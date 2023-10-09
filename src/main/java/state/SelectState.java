package state;

import components.Frame;
import components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class SelectState extends CanvasState{

    private Shape currentShape;

    public Shape getCurrentShape() {
        return currentShape;
    }

    public SelectState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void draw() {

    }

    @Override
    public void showDrawingDialog() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Shape s : getCanvas().getShapes().getShapes()) {
            if(s == null) continue;
            if (s.contains(e.getPoint())) {
                currentShape = s;
                getCanvas().getShapes().getShapes().remove(s);
                return;
            }
        }
        currentShape = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        getCanvas().getShapes().getShapes().add(currentShape);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : getCanvas().getShapes().getShapes()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
            g2d.draw(currentShape);
        };

        if(currentShape != null) {
            try {
                int x1 = mousePressedPoint.getX();
                int x2 = e.getX();
                int y1 = mousePressedPoint.getY();
                int y2 = e.getY();
                if (currentShape instanceof Rectangle2D || currentShape instanceof Ellipse2D) {
                    if (x1 < x2 && y1 > y2) {
                        ((RectangularShape) currentShape).setFrame(x1, y2, ((RectangularShape) currentShape).getWidth(), ((RectangularShape) currentShape).getHeight());
                    } else if (x1 < x2 && y1 < y2) {
                        ((RectangularShape) currentShape).setFrame(x1, y1, ((RectangularShape) currentShape).getWidth(), ((RectangularShape) currentShape).getHeight());
                    } else if (x1 > x2 && y1 > y2) {
                        ((RectangularShape) currentShape).setFrame(x2, y2, ((RectangularShape) currentShape).getWidth(), ((RectangularShape) currentShape).getHeight());
                    } else {
                        ((RectangularShape) currentShape).setFrame(x2, y1, ((RectangularShape) currentShape).getWidth(), ((RectangularShape) currentShape).getHeight());
                    }
                }
                mainPanel.repaint();
                SwingUtilities.invokeLater(draw);
            } catch (Exception ex) {
                //
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
