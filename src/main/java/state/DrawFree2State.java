package state;

import components.Frame;
import components.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

public class DrawFree2State extends CanvasState{

    private GeneralPath path;
    private boolean isStarted;

    public DrawFree2State(MainPanel panel, Frame frame) {
        super(panel, frame);
        path = new GeneralPath();
        isStarted = false;
    }
    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();

        if (!isStarted) {
            path.moveTo(mousePressedPoint.getX(), mousePressedPoint.getY());
            isStarted = true;
        }

        path.lineTo(mouseReleasedPoint.getX(), mouseReleasedPoint.getY());

        g2d.setColor(Color.BLACK);
        g2d.draw(path);
        getCanvas().getShapes().addShape(path);
    }

    @Override
    public void showDrawingDialog() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousePressedPoint = new Point(e.getX(), e.getY());
        mouseReleasedPoint = new Point(e.getX(), e.getY());
        draw();
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
