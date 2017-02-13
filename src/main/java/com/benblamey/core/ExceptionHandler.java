package com.benblamey.core;

public class ExceptionHandler {


    public static void handleException(Throwable ex, String message) {
        System.err.println(message);
        ex.printStackTrace();
        System.exit(-1);
    }

    public static void handleException(Throwable ex) {
        ExceptionHandler.handleException(ex, "Exception - Terminating...");
    }

    public static void printException(Throwable ex) {
        System.err.println("Exception - Printing...");
        ex.printStackTrace();
    }

    public static String getDetailedExceptionSummary(Throwable ex) {
        String outstr = "";
        
        do {
        	
            outstr += ex.toString();
            outstr += ex.getMessage();

            outstr += "\n";
            for (StackTraceElement element : ex.getStackTrace()) {
                outstr += element.toString();
                outstr += "\n";
            }

            outstr += "\n" + "Caused by:" + "\n";

            ex = ex.getCause();
            
        } while (ex != null);
        return outstr;
    }

    private ExceptionHandler() {
    }

}
