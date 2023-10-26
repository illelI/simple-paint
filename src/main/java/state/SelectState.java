package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class SelectState extends CanvasState{

    Point mousePressedPoint;

    private Shape currentShape;

    public Shape getCurrentShape() {
        return currentShape;
    }

    public SelectState(MainPanel panel, Frame frame) {
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
            makeCurve(points.get(i - 1), points.get(i));
        }
        mainPanel.repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedPoint = new Point(e.getX(), e.getY());
        for (Shape s : getCanvas().getControlPoints()) {
            if(s == null) continue;
            if (s.contains(e.getPoint())) {
                currentShape = s;
                return;
            }
        }
        currentShape = null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        getCanvas().addControlPoint(new Rectangle2D.Double(e.getX() - 2, e.getY() - 2, 4, 4));
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

        if(currentShape != null) {
            try {
                points = new ArrayList<>();
                Rectangle2D rect = new Rectangle2D.Double(e.getX() - 2, e.getY() - 2, 4, 4);
                bezierPoints(200);
                getCanvas().flushShapes();
                getCanvas().flushControlPoints();
                for (BezierPoint bp : controlPoints) {
                    if (rect.contains(bp.getX(), bp.getY(), 1, 1)) {
                        getCanvas().addControlPoint(rect);
                        continue;
                    }
                    getCanvas().addControlPoint(new Rectangle2D.Double(bp.getX() - 2, bp.getY() - 2, 4, 4));
                }
                controlPoints = convertShapeToBezierPoint(getCanvas().getControlPoints());
                for (int i = 1; i < points.size(); i++) {
                    makeCurve(points.get(i-1), points.get(i));
                }
                mainPanel.repaint();
            } catch (Exception ex) {
                //
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
