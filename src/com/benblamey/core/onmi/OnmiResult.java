package com.benblamey.core.onmi;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the result from calling @see onmi
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class OnmiResult {

    public OnmiResult(String dump) {
        _dump = dump;

        // "You can't use \s in Java to match white space on its own native character set, because Java doesn�t support the Unicode white space property � even though doing so is strictly required to meet UTS#18�s RL1.2! What it does have is not standards-conforming, alas.2
        Pattern f = Pattern.compile("[\\t ]*([^ [\\t ]]+)[\\t ]+([0-9\\.]+|nan)");

        for (String line : dump.split("[\\n\\r]+")) {
            Matcher matcher = f.matcher(line);
            if (matcher.matches()) {
                String key = matcher.group(1);

                // The angle brackets confuse the JSON serialization.
                key = key.replace("<", "").replace(">", "").replace(":", "");

                String value = matcher.group(2);
                if (value.equals("nan")) {
                    // "nan" is returned in the case of a perfect, trivial, (1)->(1) mapping.
                    // Parse it, and let the caller decide how to handle it.
                    Output.put(key, Double.NaN);
                } else {
                    Output.put(key, Double.parseDouble(value));
                }
                
            }
        }

        maxNormalizedInformationEntropy = Output.get("NMISum");
    }

    public String getDump() {
        return _dump;
    }

    private String _dump;

    public Double maxNormalizedInformationEntropy;
    public Map<String, Double> Output = new HashMap<String, Double>();
}
