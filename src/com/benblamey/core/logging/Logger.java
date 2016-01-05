package com.benblamey.core.logging;

/**
 * Basic Logger.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public abstract class Logger {

    public boolean debug;

    public abstract void log(LoggerLevel level, String message);

    public void debug(String message) {
        log(LoggerLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LoggerLevel.DEBUG, message);
    }

}
