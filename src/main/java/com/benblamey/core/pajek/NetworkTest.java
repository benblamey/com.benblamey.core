package com.benblamey.core.pajek;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author Ben Blamey ben@benblamey.com
 *
 */
class NetworkTest {

    public static void main(String[] args) throws IOException {
        Network n = new Network();
        n.getVertices().add(new Vertex("a"));
        n.getVertices().add(new Vertex("b"));
        n.getVertices().add(new Vertex("c"));

        n.getEdges();

        //FileWriter fw = new FileWriter();
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:\\work\\boo.net"), "ASCII"));

        n.WriteToNetFile(out);

        out.close();

    }
}
