package model;

import state.CanvasState;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Canvas implements Serializable {
    private ShapesList shapes;
    private static Canvas instance;

    private Canvas() {
        shapes = new ShapesList();
    }

    public static Canvas getInstance() {
        if(instance == null) {
            instance = new Canvas();
        }
        return instance;
    }

    public ShapesList getShapes() {
        return shapes;
    }
    public List<CanvasState.Point> getShapesList() {
        return shapes.getShapes();
    }
    public boolean addShape(CanvasState.Point s) {
        return shapes.addShape(s);
    }
    public boolean removeShape(CanvasState.Point s) {
        return shapes.removeShape(s);
    }
    public void flushShapes() {
        shapes.flushShapes();
    }
    public void setShapes(ShapesList shapes) {
        this.shapes = shapes;
    }
}
