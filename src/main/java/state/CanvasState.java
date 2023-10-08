package state;

import components.MainPanel;
import model.Canvas;
import model.ShapesList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class CanvasState extends JPanel implements MouseListener, MouseMotionListener {
    protected MainPanel mainPanel;
    protected Point mousePressedPoint;
    protected Point mouseReleasedPoint;
    private Canvas canvas;

    CanvasState(MainPanel panel) {
        this.mainPanel = panel;
        canvas = Canvas.getInstance();
    }

    public abstract void draw();

    public Canvas getCanvas() {
        return canvas;
    }

    public void load(ShapesList shapesList) {
        canvas.setShapes(shapesList);
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        for (Shape s : shapesList.getShapes()) {
            g2d.draw(s);
        }
    }

    protected class Point {
        private int x;
        private int y;
        Point() {
        }
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        void setX(int x) {
            this.x = x;
        }
        void setY(int y) {
            this.y = y;
        }
    }

}
