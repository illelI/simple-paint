package model;

import state.CanvasState;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShapesList implements Serializable {
    List<CanvasState.Point> shapes;

    public ShapesList() {
        shapes = new ArrayList<>();
    }

    public void setShapes(List<CanvasState.Point> shapes) {
        this.shapes = shapes;
    }
    public void flushShapes() {
        shapes = new ArrayList<>();
    }

    public boolean addShape(CanvasState.Point s) {
        return shapes.add(s);
    }

    public boolean removeShape(CanvasState.Point s) {
        return shapes.remove(s);
    }

    public List<CanvasState.Point> getShapes() {
        return shapes;
    }
}
