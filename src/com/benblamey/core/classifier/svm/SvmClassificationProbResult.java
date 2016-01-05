package com.benblamey.core.classifier.svm;

import java.util.HashMap;
import java.util.Map;

public class SvmClassificationProbResult {

    public Integer mostLikelyClass;
    public Double probOfMostLikelyClass;

    public Map<Integer, Double> Probabilities = new HashMap<Integer, Double>();
}
