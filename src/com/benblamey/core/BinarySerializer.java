package com.benblamey.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class for serializing objects in binary format.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class BinarySerializer {

    public static void writeToFile(String fileName, Object obj) throws IOException {

        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }

        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(fileName);

        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(obj);

        obj_out.close();
    }

    public static Object readObjectFromFile(String filename) throws ClassNotFoundException, IOException {
        // Read from disk using FileInputStream
        FileInputStream f_in = new FileInputStream(filename);

        // Read object using ObjectInputStream
        ObjectInputStream obj_in
                = new ObjectInputStream(f_in);

        // Read an object
        Object obj = obj_in.readObject();

        obj_in.close();

        return obj;
    }

    private BinarySerializer() {
    }
}
