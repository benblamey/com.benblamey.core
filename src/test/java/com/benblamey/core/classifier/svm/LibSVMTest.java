package com.benblamey.core.classifier.svm;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LibSVMTest {

    @Test
    @Ignore // This needs libSVM installed locally, ignore for now.
    public void test() {

        List<SvmClassificationProbResult> output = LibSVM._extractProbResultsFromOutput("C:/work/code/Ben/ben_phd_java/benblamey/src/benblamey/saesneg/phaseB/clustering/output.txt");

        // labels 2 1
        // 1 0.119056 0.880944
        // 2 0.88152 0.11848
        // 1 0.415069 0.584931
        SvmClassificationProbResult resultA = output.get(0);
        SvmClassificationProbResult resultB = output.get(1);
        SvmClassificationProbResult resultC = output.get(2);

        assertEquals((Integer) 1, resultA.mostLikelyClass);
        assertEquals((Integer) 2, resultB.mostLikelyClass);
        assertEquals((Integer) 1, resultC.mostLikelyClass);

        assertEquals((Double) 0.880944, resultA.probOfMostLikelyClass);
        assertEquals((Double) 0.88152, resultB.probOfMostLikelyClass);
        assertEquals((Double) 0.584931, resultC.probOfMostLikelyClass);

    }

}
