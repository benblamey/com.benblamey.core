package com.benblamey.core.pajek;

/**
 * Represents a Vertex when creating a graph for
 * <a href="http://vlado.fmf.uni-lj.si/pub/networks/pajek/">PAJEK<a>
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Vertex {

    private final String _label;
    private final Object _data;
    private int _index;

    public Vertex(String label) {
        this(label, null);
    }

    public Vertex(String label, Object data) {
        _label = label;
        _data = data;
    }

    /**
     * Index is set internally.
     *
     * @param value
     */
    void setIndex(int value) {
        _index = value;
    }

    Integer getIndex() {
        return _index;
    }

    String getLabel() {
        return _label;
    }

    public Object getData() {
        return _data;
    }
}
