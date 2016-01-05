package com.benblamey.core.onmi;

import com.benblamey.core.StreamUtils;
import java.io.IOException;
import java.io.File;

/**
 * Utility method for calling the
 * <a href="https://github.com/aaronmcdaid/Overlapping-NMI">ONMI</a> library to
 * compute Overlapping Normalized Mutual Information.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class onmi {

    public static OnmiResult run(String fileA, String fileB) throws InterruptedException, IOException {
        return run(new File(fileA), new File(fileB));
    }
    
    public static OnmiResult run(File fileA, File fileB) throws InterruptedException, IOException {

        ProcessBuilder p = new ProcessBuilder("C:/work/code/3rd_Ben/Overlapping-NMI/onmi.exe", fileA.getAbsolutePath(), fileB.getAbsolutePath());

        p.environment().put("Path", p.environment().get("Path") + ";C:/cygwin64/bin");

        Process onmi = p.start();

        onmi.waitFor();

        // Convert the output of the process into a string.
        OnmiResult onmiResult = new OnmiResult(StreamUtils.convertStreamToString(onmi.getInputStream()));

        return onmiResult;
    }
}
