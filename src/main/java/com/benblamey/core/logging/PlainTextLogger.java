package com.benblamey.core.logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlainTextLogger extends Logger {

    private PrintStream _stream;

    public PlainTextLogger(PrintStream s) {
        _stream = s;
    }

    private static SimpleDateFormat logTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    @Override
    public void log(LoggerLevel level, String message) {

        if (debug || level != LoggerLevel.DEBUG) {
            _stream.println(logTimestamp.format(new Date()) + " " + level.toString() + " " + message);
        }
    }

}
