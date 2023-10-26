package model;

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
    public List<Shape> getShapesList() {
        return shapes.getShapes();
    }
    public boolean addShape(Shape s) {
        return shapes.addShape(s);
    }
    public List<Shape> getControlPoints() {
        return shapes.getControlPoints();
    }
    public boolean addControlPoint(Shape s) {
        return shapes.addControlPoint(s);
    }
    public boolean removeShape(Shape s) {
        return shapes.removeShape(s);
    }
    public boolean removeControlPoint(Shape s) {
        return shapes.removeControlPoint(s);
    }
    public void flushShapes() {
        shapes.flushShapes();
    }
    public void flushControlPoints() {
        shapes.flushControlPoints();
    }
    public void setShapes(ShapesList shapes) {
        this.shapes = shapes;
    }
}
