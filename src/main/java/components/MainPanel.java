package components;

import model.ShapesList;
import state.CanvasState;
import state.SelectState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

    private CanvasState state;

    public MainPanel(Frame frame) {
        super();
        this.state = new SelectState(this, frame);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public void changeState(CanvasState state) {
        this.state = state;
    }

    public CanvasState getState() {
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setColor(Color.blue);
        for (Shape s : state.getCanvas().getControlPoints()) {
            if (s == null) continue;
            g2d.fill(s);
        }
        g2d.setColor(Color.BLACK);
        for (Shape s : state.getCanvas().getShapesList()) {
            if(s == null) continue;
            g2d.draw(s);
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
