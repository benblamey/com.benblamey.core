package com.benblamey.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    /**
     * @param args
     */
    public static boolean exists(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static void writeFile(String stringToWrite, String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(stringToWrite);
            bw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    public static BufferedWriter getWriter(String filename) {
        FileWriter fw;
        try {
            fw = new FileWriter(filename);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        BufferedWriter bw = new BufferedWriter(fw);
        return bw;    
    }

    public static void appendCSVLine(BufferedWriter bw, Object[] args) {
        
        for (Object arg : args) {
            String value;
            if (arg == null) {
                value = "NULL";
            }
            else {
                value = arg.toString();
                
                value = value.replaceAll(",", "_");
                value = value.replaceAll("\"", "@");
                
                value = value.replaceAll("[\\r\\n]+", " ");
            }
            
            try {
                bw.append(value + ",");
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
        }
        
        try {
                bw.append("\n");
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
        
    }

}
