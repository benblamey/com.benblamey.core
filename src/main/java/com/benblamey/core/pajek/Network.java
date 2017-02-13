package com.benblamey.core.pajek;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Network when creating a graph for
 * <a href="http://vlado.fmf.uni-lj.si/pub/networks/pajek/">PAJEK<a>
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Network {

    final List<Vertex> _vertices = new ArrayList<Vertex>();
    private List<Edge> _edges = new ArrayList<Edge>();

    public List<Vertex> getVertices() {
        return _vertices;
    }

    public void WriteToNetFile(Writer f) throws IOException {
        f.append("*Vertices " + _vertices.size() + "\r\n");

        int vertexCounter = 1;
        for (Vertex vertex : _vertices) {
            vertex.setIndex(vertexCounter);
            f.append(Integer.toString(vertexCounter) + " \"" + vertex.getLabel() + "\"\r\n");
            vertexCounter++;
        }

        f.append("*Edges\n");
        for (Edge e : _edges) {
            f.append(e.getLeft().getIndex() + " "
                    + e.getRight().getIndex() + " "
                    + Double.toString(e.Weight)
                    + "\r\n");
        }

        
    }

    public List<Edge> getEdges() {
        return _edges;
    }
}
