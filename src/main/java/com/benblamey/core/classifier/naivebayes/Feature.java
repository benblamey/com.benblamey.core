package com.benblamey.core.classifier.naivebayes;

import java.util.HashMap;

/**
 * A feature used by internally the @see Classifier
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
class Feature {

    final String _featureKey;
    final HashMap<String, Integer> _occurencesByLabel = new HashMap<>();

    public Feature(String feature) {
        _featureKey = feature;
    }

    public void trainingCaseHasFeature(String category) {
        Integer caseCount = _occurencesByLabel.get(category);
        if (caseCount == null) {
            caseCount = 0;
        }
        caseCount++;
        _occurencesByLabel.put(category, caseCount);
    }

    public int occurencesForCategory(String category) {
        Integer result = _occurencesByLabel.get(category);
        if (result != null) {
            return result.intValue();
        } else {
            return 0;
        }
    }
}
