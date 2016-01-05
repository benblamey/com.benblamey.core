package com.benblamey.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/**
 * Utility methods for Streams.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class StreamUtils {

    public static String convertStreamToString(java.io.InputStream is) {

        // "I learned this trick from "Stupid Scanner tricks" article. The reason it works is because Scanner iterates over tokens in the stream, and in this case we separate tokens using "beginning of the input boundary" (\A) thus giving us only one token for the entire contents of the stream."
        // http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void writeStringToFile(String s, String filePath) {
        PrintWriter out;
        try {
            out = new PrintWriter(filePath, "UTF-8");
            out.println(s);
            out.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
