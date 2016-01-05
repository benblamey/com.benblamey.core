package com.benblamey.core;

import com.benblamey.core.StreamUtils;
import java.io.IOException;

/**
 * Utility methods for processes.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class ProcessUtilities {

    // DOES NOT WORK WITH ARGUMENTS -- YOU NEED TO USE THE ARRAY VERSION!
    /**
     * Run a process and return its (standard) output as a @see String.
     *
     * @param cmd
     * @return
     */
    public static String runAndReturnOutput(String cmd) {
        ProcessBuilder pb = new ProcessBuilder(cmd);

        Process proc;
        try {
            proc = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Convert the output of the process into a string.
        String stdOut = StreamUtils.convertStreamToString(proc.getInputStream());

        return stdOut;
    }

    /**
     * Run a process and return its (standard) output as a @see String.
     *
     * @param cmd
     * @return
     */
    public static String runAndReturnOutput(String[] cmd) {
        ProcessBuilder pb = new ProcessBuilder(cmd);

        Process proc;
        try {
            proc = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Convert the output of the process into a string.
        String stdOut = StreamUtils.convertStreamToString(proc.getInputStream());

        stdOut += StreamUtils.convertStreamToString(proc.getErrorStream());

        return stdOut;
    }

}
