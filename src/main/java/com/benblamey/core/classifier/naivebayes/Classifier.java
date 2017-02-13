package com.benblamey.core.classifier.naivebayes;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Bayesian Classifier with Laplace smoothing. Strings are used as features
 * without any further processing, such as case normalization.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Classifier {

    /**
     *
     * @param smoothingParam Smoothing parameter for Laplace smoothing. Zero for
     * no smoothing.
     * @see <a href="http://en.wikipedia.org/wiki/Additive_smoothing">Laplace
     * Smoothing</a>
     */
    public Classifier(Double smoothingParam) {
        _smoothingParameter = smoothingParam;
    }

    private final HashMap<String, Feature> _features = new HashMap<>();
    private final HashMap<String, Integer> _docCountsByCategory = new HashMap<>();
    private final double _smoothingParameter;

    /**
     * Add a training case.
     *
     * @param category
     * @param features
     */
    public void addTrainingCase(String category, Collection<String> features) {
        for (String docFeature : features) {

            // Case is not normalized here. When using text tokens as features, this is done beforehand. 
            Feature feature = _features.get(docFeature);
            if (feature == null) {
                feature = new Feature(docFeature);
                _features.put(docFeature, feature);
            }
            feature.trainingCaseHasFeature(category);
        }

        if (_docCountsByCategory.containsKey(category)) {
            _docCountsByCategory.put(category, _docCountsByCategory.get(category) + 1);
        } else {
            _docCountsByCategory.put(category, 1);
        }
    }

    /**
     * Print a summary of training cases to STDOUT.
     */
    public void printTrainingCaseSummaryToSTDOUT() {

        PrintStream out = System.out;

        out.println("CASES");
        int totalCases = 0;
        for (String categ : _docCountsByCategory.keySet()) {
            Integer casesForCateg = _docCountsByCategory.get(categ);
            out.println("\tCases for categ: " + categ + " = " + casesForCateg);
            totalCases += casesForCateg;
        }

        out.println("Total cases: = " + totalCases);

        for (String feature : _features.keySet()) {
            Feature feature2 = _features.get(feature);
            out.println("Feature: " + feature2._featureKey);
            for (String label : feature2._occurencesByLabel.keySet()) {
                out.println("\t" + label + " = " + feature2._occurencesByLabel.get(label));
            }
        }

        out.println();
    }

    /**
     * Classifiers the given feature set.
     *
     * @param docFeatures
     * @return The name of the category.
     */
    public String getProbabilities(HashSet<String> docFeatures) {

        HashMap<String, Double> results = new HashMap<>();

        for (String category : _docCountsByCategory.keySet()) {
            // Add extra cases for Laplace smoothing.
            Double docCountForCategory = _docCountsByCategory.get(category) + _smoothingParameter * _docCountsByCategory.size();

            double logOfProbability = 0; // Prior doesn't matter - we're only interested in which one larger.

            for (String docFeature : docFeatures) {
                double occurencesForCategory;

                if (this._features.containsKey(docFeature)) {
                    Feature get = this._features.get(docFeature);
                    occurencesForCategory = get.occurencesForCategory(category);
                } else {
                    occurencesForCategory = 0;
                }

                // Add the smoothing parameter for Laplace smoothing. 
                occurencesForCategory += _smoothingParameter;

                logOfProbability += Math.log(occurencesForCategory);
                logOfProbability -= Math.log(docCountForCategory);
            }

            results.put(category, logOfProbability);
        }

        double prob = Double.NEGATIVE_INFINITY;
        String categ = null;
        for (java.util.Map.Entry<String, Double> entry : results.entrySet()) {
            if (entry.getValue() > prob) {
                prob = entry.getValue();
                categ = entry.getKey();
            }
        }

        return categ;
    }
}
