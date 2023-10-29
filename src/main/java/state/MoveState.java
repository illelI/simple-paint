package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MoveState extends CanvasState{

    Point oldPoint;

    public MoveState(MainPanel panel, Frame frame) {
        super(panel, frame);
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
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        oldPoint = mousePressedPoint;
        mousePressedPoint = new Point(e.getX(), e.getY());
        Point vector = new Point(mousePressedPoint.getX() - oldPoint.getX(), mousePressedPoint.getY() - oldPoint.getY());
        for (Point p : getPointList()) {
            p.setX(p.getX() + vector.getX());
            p.setY(p.getY() + vector.getY());
        }
        draw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
