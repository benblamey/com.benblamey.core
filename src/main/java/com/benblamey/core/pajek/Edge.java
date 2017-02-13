package com.benblamey.core.pajek;

/**
 * Represents an Edge when creating a graph for
 * <a href="http://vlado.fmf.uni-lj.si/pub/networks/pajek/">PAJEK<a>
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Edge {

    private final Vertex _right;
    private final Vertex _left;

    public Edge(Vertex left, Vertex right, Double weight) {
        _left = left;
        _right = right;
        Weight = weight;
    }

    public Vertex getLeft() {
        return _left;
    }

    public Vertex getRight() {
        return _right;
    }
    public Double Weight = 1.0;
}
