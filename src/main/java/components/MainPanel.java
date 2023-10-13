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
    private Frame holder;
    public MainPanel(Frame frame) {
        super();
        this.holder = frame;
        this.state = new SelectState(this, holder);
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
        state.load(shapesList);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        for (Shape s : state.getCanvas().getShapesList()) {
            if(s == null) continue;
            g2d.draw(s);
        }
    }

    public void paintImage(Graphics g, Image img) {
        this.setSize(img.getWidth(null), img.getHeight(null));
        super.paintComponents(g);
        SwingUtilities.invokeLater(() -> g.drawImage(img, 0, 0, null));
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
