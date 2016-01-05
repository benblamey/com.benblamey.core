package com.benblamey.core.classifier.svm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents cases files for the
 * <a href="http://www.csie.ntu.edu.tw/~cjlin/libsvm">LibSVM</a> classifier.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class SvmFile {

    private BufferedWriter _bw;
    private int _numFeatures;
    private String _svmFilePath;

    public SvmFile(String filePath, int numFeatures) throws IOException {
        this(filePath, numFeatures, false);// Don't append by default.
    }

    public SvmFile(String svmFilePath, int length, boolean append) throws IOException {
        _svmFilePath = svmFilePath;
        _numFeatures = length;
        _bw = new BufferedWriter(new FileWriter(svmFilePath, append));
    }

    /**
     * Add a case to the file.
     *
     * @param clazzLabel - "For classification, <label> is an integer indicating
     * the class label (multi-class is supported). For regression, <label> is
     * the target value which can be any real number. For one-class SVM, it's
     * not used so can be any number. "
     * @param featureWeights - Should be normalized, between 0..1, or -1..+1.
     * @throws IOException
     */
    public void addCase(int clazzLabel, double[] featureWeights) throws IOException {

        if (featureWeights.length != _numFeatures) {
            throw new RuntimeException("Every case must have exactly " + _numFeatures + " features.");
        }

        // The format of training and testing data file is:
        //	 <label> <index1>:<value1> <index2>:<value2> ...
        _bw.append(Integer.toString(clazzLabel));
        for (int i = 0; i < featureWeights.length; i++) {

            // "<index> is an integer starting from 1 and <value> is a real number. 
            // The only exception is the precomputed kernel, where
            // <index> starts from 0."
            if (Double.isNaN(featureWeights[i]) || Double.isInfinite(featureWeights[i])) {
                throw new IllegalArgumentException("Index " + i + " has NaN value.");
            }

            _bw.append(" " + (i + 1) + ":" + featureWeights[i]);
        }
        _bw.append("\n");
    }

    public void close() throws IOException {
        _bw.close();
    }

    public String getPath() {
        return _svmFilePath;
    }

}
