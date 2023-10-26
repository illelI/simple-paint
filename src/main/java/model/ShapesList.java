package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShapesList implements Serializable {
    List<Shape> shapes;
    List<Shape> controlPoints;

    public ShapesList() {
        shapes = new ArrayList<>();
        controlPoints = new ArrayList<>();
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
    public void flushShapes() {
        shapes = new ArrayList<>();
    }
    public void flushControlPoints() {
        controlPoints = new ArrayList<>();
    }

    public boolean addShape(Shape s) {
        return shapes.add(s);
    }

    public boolean removeShape(Shape s) {
        return shapes.remove(s);
    }
    public boolean removeControlPoint(Shape s) {
        return controlPoints.remove(s);
    }

    public List<Shape> getShapes() {
        return shapes;
    }
    public List<Shape> getControlPoints() {
        return controlPoints;
    }
    public boolean addControlPoint(Shape s) {
        return controlPoints.add(s);
    }
}
