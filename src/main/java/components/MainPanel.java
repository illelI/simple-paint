package components;

import model.ShapesList;
import state.CanvasState;
import state.MoveState;

import state.CanvasState.Point;

import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

    private CanvasState state;

    public MainPanel(Frame frame) {
        super();
        this.state = new MoveState(this, frame);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public void changeState(CanvasState state) {
        this.state = state;
    }

    public CanvasState getState() {
        return state;
    }
    public void load(ShapesList shapesList) {
        super.paint(getGraphics());
        state.getCanvas().setShapes(shapesList);
        state.setPointList(shapesList);
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setColor(Color.blue);
        List<Point> points = getState().getPointList();
        if (points.size() > 0) {
            g2d.fill(new Rectangle2D.Double(points.get(0).getX() - 1, points.get(0).getY() - 1, 3, 3));
            for (int i = 1; i < points.size(); i++) {
                g2d.fill(new Rectangle2D.Double(points.get(i).getX() - 1, points.get(i).getY() - 1, 3, 3));
                g2d.setColor(Color.BLACK);
                g2d.drawLine(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(), points.get(i).getY());
                g2d.setColor(Color.blue);
            }
            g2d.setColor(Color.BLACK);
            g2d.drawLine(points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), points.get(0).getX(), points.get(0).getY());
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        state.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        state.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        state.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        state.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
