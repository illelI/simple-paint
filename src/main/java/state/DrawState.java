package state;

import components.Frame;
import components.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawState extends CanvasState{

    public DrawState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        pointList.add(new Point(e.getX(), e.getY()));
        getCanvas().addShape(new Point(e.getX(), e.getY()));
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
