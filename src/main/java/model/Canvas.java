package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

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

    public void setShapes(ShapesList shapes) {
        this.shapes = shapes;
    }
}
