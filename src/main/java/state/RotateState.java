package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RotateState extends CanvasState{
    private Point referencePoint;
    private double startAngle = 0;
    public RotateState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            referencePoint = new Point(e.getX(), e.getY());
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.setColor(Color.RED);
            g2d.drawRect(referencePoint.getX() - 1, referencePoint.getY() - 1, 3, 3);
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
        int x = e.getX();
        int y = e.getY();
        double currentAngle = Math.toDegrees(Math.atan2(y - referencePoint.getY(), x - referencePoint.getX()));
        double angleDifference = currentAngle - startAngle;

        if (startAngle == 0) {
            rotate(startAngle);
        } else {
            rotate(angleDifference);
        }

        startAngle = currentAngle;
    }

    private void rotate(double angle) {
        double radians = Math.toRadians(angle);
        double cosA = Math.cos(radians);
        double sinA = Math.sin(radians);
        Point reference = referencePoint;

        for (Point point : getCanvas().getShapesList()) {
            double x = point.getX();
            double y = point.getY();

            double translatedX = x - reference.getX();
            double translatedY = y - reference.getY();

            double rotatedX = translatedX * cosA - translatedY * sinA;
            double rotatedY = translatedX * sinA + translatedY * cosA;

            point.setX((int) (reference.getX() + rotatedX));
            point.setY((int) (reference.getY() + rotatedY));
        }
        draw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
