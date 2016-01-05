package com.benblamey.core;

/**
 * Utility methods for OS.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class SystemArchitecture {

    public static boolean IsLinuxSystem() {
        return System.getProperty("os.name").startsWith("Linux");
    }
}
