package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShapesList implements Serializable {
    List<Shape> shapes;

    public ShapesList() {
        shapes = new ArrayList<>();
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}
