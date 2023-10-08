package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ShapesList implements Serializable {
    ArrayList<Shape> shapes;

    public ShapesList() {
        shapes = new ArrayList<>();
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }
}
