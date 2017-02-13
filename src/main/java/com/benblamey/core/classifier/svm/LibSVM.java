package com.benblamey.core.classifier.svm;

import com.benblamey.core.StringUtil;
import com.benblamey.core.FileUtil;
import com.benblamey.core.ProcessUtilities;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates (and executes) commands for executing the
 * <a href="http://www.csie.ntu.edu.tw/~cjlin/libsvm">LibSVM</a> classifier, as
 * an external EXE.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class LibSVM {

    private static String svmPath = "C:\\work\\code\\3rd_Ben\\libsvm\\windows\\";

    /**
     * Run training on a given cases file.
     *
     * @param casesFilePath
     * @throws InterruptedException
     * @throws IOException
     */
    public static void train(String casesFilePath) throws InterruptedException, IOException {
        String cmd = generateSVMCrossValidateCmd(casesFilePath);
        ProcessUtilities.runAndReturnOutput(cmd);
    }

    /**
     * Generate the command for executing cross-validation on a given cases
     * file.
     *
     * @param casesFilePath
     * @return
     */
    public static String generateSVMCrossValidateCmd(String casesFilePath) {
        /*
		Usage: svm-train [options] training_set_file [model_file]
		options:
		-s svm_type : set type of SVM (default 0)
			0 -- C-SVC		(multi-class classification)
			1 -- nu-SVC		(multi-class classification)
			2 -- one-class SVM	
			3 -- epsilon-SVR	(regression)
			4 -- nu-SVR		(regression)
		-t kernel_type : set type of kernel function (default 2)
			0 -- linear: u'*v
			1 -- polynomial: (gamma*u'*v + coef0)^degree
			2 -- radial basis function: exp(-gamma*|u-v|^2)
			3 -- sigmoid: tanh(gamma*u'*v + coef0)
			4 -- precomputed kernel (kernel values in training_set_file)
		-d degree : set degree in kernel function (default 3)
		-g gamma : set gamma in kernel function (default 1/num_features)
		-r coef0 : set coef0 in kernel function (default 0)
		-c cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)
		-n nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)
		-p epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)
		-m cachesize : set cache memory size in MB (default 100)
		-e epsilon : set tolerance of termination criterion (default 0.001)
		-h shrinking : whether to use the shrinking heuristics, 0 or 1 (default 1)
		-b probability_estimates : whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)
		-wi weight : set the parameter C of class i to weight*C, for C-SVC (default 1)
		-v n: n-fold cross validation mode
		-q : quiet mode (no outputs)
		
		The k in the -g option means the number of attributes in the input data.
		
		option -v randomly splits the data into n parts and calculates cross
		validation accuracy/mean squared error on them.
		
		See libsvm FAQ for the meaning of outputs.
         */

        String cmd = svmPath + "svm-train.exe"
                + " -b 1" // enable probability estimates
                + " -m 2000" // Cache size MB. (32bit app). 
                + " -v 3" // 3-fold x-validation.
                + " \"" + casesFilePath + "\"";
        return cmd;
    }

    /**
     * Generate the command for predicting the class.
     *
     * @param casesFilePath The input cases to be classified.
     * @param modelFile The model file.
     * @param outputFile The file to which the output cases will be written.
     * @return
     */
    public static String[] generateSvmPredictCmd(String casesFilePath, String modelFile, String outputFile, boolean prob) {

        String[] cmd = new String[]{svmPath + "svm-predict.exe",
            "-b", new Integer(prob ? 1 : 0).toString(),
            "\"" + casesFilePath + "\"",
            "\"" + modelFile + "\"",
            "\"" + outputFile + "\""};

        return cmd;
    }

    /**
     * Predict the class for a single case.
     *
     * @param casesFilePath A cases file containing a single case.
     * @param modelFile The trained model.
     * @return The index of the output class.
     */
    public static int predict(String casesFilePath, String modelFile) {

        if (!new File(modelFile).exists()) {
            throw new RuntimeException("Model file " + modelFile + " does not exist.");
        }

        String outputFile = System.getProperty("java.io.tmpdir") + "test_lib_svm_out.tmp";
        String cmd[] = generateSvmPredictCmd(casesFilePath, modelFile, outputFile, false);
        String svmTrainOutput = ProcessUtilities.runAndReturnOutput(cmd);
        //System.out.println("svm-train: " + svmTrainOutput);

        List<String> readAllLines;
        try {
            readAllLines = Files.readAllLines(Paths.get(outputFile), Charset.forName("US-ASCII"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // The output file consists of one line containing the output class.
        return Integer.parseInt(readAllLines.get(0));
    }

    /**
     * Predict the class for a single case.
     *
     * @param casesFilePath A cases file containing a single case.
     * @param modelFile The trained model.
     * @return The index of the output class.
     */
    public static List<SvmClassificationProbResult> predictWithProbs(String casesFilePath, String modelFile, String output) {

        if (!new File(modelFile).exists()) {
            throw new RuntimeException("Model file " + modelFile + " does not exist.");
        }

        //String outputFile = System.getProperty("java.io.tmpdir") + "test_lib_svm_out.tmp";
        String cmd[] = generateSvmPredictCmd(casesFilePath, modelFile, output, true);
        String svmTrainOutput = ProcessUtilities.runAndReturnOutput(cmd);
        FileUtil.writeFile(svmTrainOutput, output + ".stdout");
        
        //System.out.println("svm-predict: " + svmTrainOutput);

        return _extractProbResultsFromOutput(output);
    }

    /**
     * (Package access to allow for testing).
     *
     * @param outputFile
     * @return
     */
    static List<SvmClassificationProbResult> _extractProbResultsFromOutput(
            String outputFile) {
        List<String> readAllLines;
        try {
            readAllLines = Files.readAllLines(Paths.get(outputFile), Charset.forName("US-ASCII"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // The output file consists of something like this:
		/*
		labels 0 1                !Remember: labels may not be in right order, and may not start at 1 etc!
		1 0.119056 0.880944
		0 0.88152 0.11848
		1 0.415069 0.584931
         */

        String[] colHeaders = null; // col headers. First label is "label"
        List<SvmClassificationProbResult> results = new ArrayList<>();
        for (int i = 0; i < readAllLines.size(); i++) {
            String line = readAllLines.get(i);
            if (i == 0) {
                // Verify the header,and check that the labels are sensible classes.
                if (!line.startsWith("labels ")) {
                    System.err.println("SVM output:");
                    StringUtil.concat(readAllLines, "\n");
                    throw new RuntimeException("svm output not in right format");
                } else {
                    colHeaders = line.split(" "); // First label is "label"
                }
            } else {
                String[] split = line.split(" ");
                SvmClassificationProbResult result = new SvmClassificationProbResult();
                for (int col = 0; col < split.length; col++) {
                    String colText = split[col];
                    if (col == 0) {
                        result.mostLikelyClass = Integer.parseInt(colText); // most likely Class is the first column.
                    } else {
                       
                        int classOfThisCol = Integer.parseInt(colHeaders[col]); // Key is the class index, found from col index.
                        double probOFThisClass = Double.parseDouble(colText);// Value is the column text.
                        result.Probabilities.put(classOfThisCol, probOFThisClass);
                        if (result.mostLikelyClass == classOfThisCol) {
                            result.probOfMostLikelyClass = probOFThisClass;
                        }
                    }
                }
                results.add(result);
            }
        }

        return results;
    }

    /**
     * Generate a command to train an SVM
     *
     * @param casesFilePath The input cases.
     * @param modelFile The output model.
     * @return
     */
    public static String[] generateSVMTrainCmd(String casesFilePath, String modelFile) {
        /*
		Usage: svm-train [options] training_set_file [model_file]
		options:
		-s svm_type : set type of SVM (default 0)
			0 -- C-SVC		(multi-class classification)
			1 -- nu-SVC		(multi-class classification)
			2 -- one-class SVM	
			3 -- epsilon-SVR	(regression)
			4 -- nu-SVR		(regression)
		-t kernel_type : set type of kernel function (default 2)
			0 -- linear: u'*v
			1 -- polynomial: (gamma*u'*v + coef0)^degree
			2 -- radial basis function: exp(-gamma*|u-v|^2)
			3 -- sigmoid: tanh(gamma*u'*v + coef0)
			4 -- precomputed kernel (kernel values in training_set_file)
		-d degree : set degree in kernel function (default 3)
		-g gamma : set gamma in kernel function (default 1/num_features)
		-r coef0 : set coef0 in kernel function (default 0)
		-c cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)
		-n nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)
		-p epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)
		-m cachesize : set cache memory size in MB (default 100)
		-e epsilon : set tolerance of termination criterion (default 0.001)
		-h shrinking : whether to use the shrinking heuristics, 0 or 1 (default 1)
		-b probability_estimates : whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)
		-wi weight : set the parameter C of class i to weight*C, for C-SVC (default 1)
		-v n: n-fold cross validation mode
		-q : quiet mode (no outputs)
		
		The k in the -g option means the number of attributes in the input data.
		
		option -v randomly splits the data into n parts and calculates cross
		validation accuracy/mean squared error on them.
		
		See libsvm FAQ for the meaning of outputs.
         */

        String[] cmd
                = new String[]{
                    svmPath + "svm-train.exe",
                    "-m", "2000", // Cache size MB. (32bit app).
                    "-b", "1",
                    // I think these two might be the wrong way round.

                    "\"" + casesFilePath + "\"",
                    "\"" + modelFile + "\""

                };
        return cmd;
    }

}
