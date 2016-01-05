package com.benblamey.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * String utility methods.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class StringUtils {

    public static boolean IsNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Tokenizes a String.
     *
     * @param s
     * @return
     */
    public static List<String> Tokenize(String s) {

        //List<String> tokens = Twokenize.tokenize(s);// We don't use rawTokenize, because we don't have HTML character entities.
        List<String> tokens = Arrays.asList(s.split("[\\s\\.\\\\/,\"'!\\?\\(\\);:|-]+"));

        List<String> finalTokens = new ArrayList<>();
        for (String token : tokens) {
            String lowerCaseToken = token.toLowerCase();
            if (lowerCaseToken.endsWith("'s")) {
                lowerCaseToken = lowerCaseToken.substring(0, lowerCaseToken.length() - 2);
            }
            lowerCaseToken = StringUtils.squashRepitition(lowerCaseToken);

            finalTokens.add(lowerCaseToken);
        }

        return finalTokens;
    }

    /**
     * Squashes repetitions of more than 3 characters in a String.
     *
     * @param s
     * @return
     */
    public static String squashRepitition(String s) {
        if (s.length() <= 3) {
            return s;
        }
        String squashedChars = "";

        char prevButOneChar = 0;
        char prevChar = 0;
        for (char c : s.toCharArray()) {
            if ((prevButOneChar == prevChar) && (prevChar == c)) {
                continue;
            }
            prevButOneChar = prevChar;
            prevChar = c;
            squashedChars = squashedChars + c;
        }

        return squashedChars;
    }

    /**
     * Trims from the start and end of a string anything that is not a letter.
     * (actually this turned out to be slow-ish, so it was done with a regex in
     * the end)/
     *
     * @return
     */
    public static String trimNonLetters(String val) {
        int firstLetterIndex;
        int length = val.length();
        for (firstLetterIndex = 0; firstLetterIndex < length; firstLetterIndex++) {
            if (Character.isLetter(val.charAt(firstLetterIndex))) {
                break;
            }
        }

        // All chars are non-letters.
        if (firstLetterIndex == length) {
            return "";
        }

        int lastLetterIndex;
        for (lastLetterIndex = length - 1; lastLetterIndex >= 0; lastLetterIndex--) {
            if (Character.isLetter(val.charAt(lastLetterIndex))) {
                break;
            }
        }

        String trimmedString = val.substring(firstLetterIndex, lastLetterIndex + 1);
        return trimmedString;
    }

    static String trim(char c, String str) {
        final String cString = Character.toString(c);
        if (str.startsWith(cString)) {
            str = str.substring(1);
        }
        
        if (str.endsWith(cString)) {
            throw new RuntimeException("not implemented yet");
        }
        
        return str;
    }
}
