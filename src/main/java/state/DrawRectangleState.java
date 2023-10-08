package state;

import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DrawRectangleState extends CanvasState{

    public DrawRectangleState(MainPanel panel) {
        super(panel);
    }

    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        int x1 = mousePressedPoint.getX();
        int x2 = mouseReleasedPoint.getX();
        int y1 = mousePressedPoint.getY();
        int y2 = mouseReleasedPoint.getY();

        Rectangle2D rectangle;

        if (x1 < x2 && y1 > y2) {
            rectangle = new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2);
        } else if (x1 < x2 && y1 < y2) {
            rectangle = new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
        } else if (x1 > x2 && y1 > y2) {
            rectangle = new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2);
        } else {
            rectangle = new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1);
        }

        g2d.setColor(Color.BLACK);
        g2d.draw(rectangle);

        getCanvas().getShapes().getShapes().add(rectangle);
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
