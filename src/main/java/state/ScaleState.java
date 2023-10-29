package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ScaleState extends CanvasState{
    private double prevDist = 0.0;
    private double scaleFactor = 1.0;
    private Point referencePoint;
    public ScaleState(MainPanel panel, Frame frame) {
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
        double x = e.getX();
        double y = e.getY();
        double distX = x - referencePoint.getX();
        double distY = y - referencePoint.getY();
        double distance = Math.sqrt(distX * distX + distY * distY);
        double deltaDist = distance - prevDist;

        if (prevDist == 0) {
            deltaDist = 1;
        }

        scaleFactor = 1 + deltaDist / 200;
        scaleFactor = Math.max(0.5, Math.min(1.5, scaleFactor));
        scale(scaleFactor);
        draw();
        prevDist = distance;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void scale(double scaleFactor) {
        Point reference = referencePoint; // (x0, y0)

        for (Point point : getCanvas().getShapesList()) {
            double x = point.getX();
            double y = point.getY();

            double translatedX = x - reference.getX(); // x - x0
            double translatedY = y - reference.getY(); // y - y0

            double scaledX = translatedX * scaleFactor; // (x - x0) * k
            double scaledY = translatedY * scaleFactor; // (y - y0) * k

            point.setX((int) (reference.getX() + scaledX));  // x' = x0 + (x - x0) * k
            point.setY((int) (reference.getY() + scaledY));  // y' = y0 + (x - y0) * k
        }
    }
}
