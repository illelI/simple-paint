package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawBezierState extends CanvasState{

    public DrawBezierState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void draw() {
        points = new ArrayList<>();
        bezierPoints(200);
        getCanvas().flushShapes();
        getCanvas().flushControlPoints();
        for (BezierPoint bp : controlPoints) {
            getCanvas().addControlPoint(new Rectangle2D.Double(bp.getX() - 2, bp.getY() - 2, 4, 4));
        }
        for (int i = 1; i < points.size(); i++) {
            makeCurve(points.get(i-1), points.get(i));
        }
        mainPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        addPoint(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
