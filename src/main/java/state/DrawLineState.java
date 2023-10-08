package state;

import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawLineState extends CanvasState{

    public DrawLineState(MainPanel panel) {
        super(panel);
    }

    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        Point2D startingPoint = new Point2D.Float(mousePressedPoint.getX(), mousePressedPoint.getY());
        Point2D endingPoint = new Point2D.Float(mouseReleasedPoint.getX(), mouseReleasedPoint.getY());
        Line2D line = new Line2D.Float(startingPoint, endingPoint);

        g2d.setColor(Color.BLACK);
        g2d.draw(line);

        getCanvas().getShapes().getShapes().add(line);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleasedPoint = new Point(e.getX(), e.getY());
        draw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
