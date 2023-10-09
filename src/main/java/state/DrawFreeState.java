package state;

import components.Frame;
import components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawFreeState extends CanvasState{

    Point point = new Point();

    public DrawFreeState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();

        int x = point.getX();
        int y = point.getY();

        Ellipse2D circle = new Ellipse2D.Double(x - 5, y - 5, 10, 10);


        g2d.setColor(Color.BLACK);
        g2d.fill(circle);

        getCanvas().getShapes().getShapes().add(circle);
    }

    @Override
    public void showDrawingDialog() {

    }

    @Override
    public void showChangePropertiesDialog() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        point.setX(e.getX());
        point.setY(e.getY());
        draw();
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
        point.setX(e.getX());
        point.setY(e.getY());
        draw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
