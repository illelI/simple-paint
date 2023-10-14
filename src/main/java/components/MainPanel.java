package components;

import model.ShapesList;
import state.CanvasState;
import state.SelectState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

    private CanvasState state;
    private Frame holder;

    private BufferedImage image;

    public MainPanel(Frame frame) {
        super();
        this.holder = frame;
        this.state = new SelectState(this, holder);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }
    }

    public void paintImage(Graphics g, Image img) {
        this.setSize(img.getWidth(null), img.getHeight(null));
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponents(g2d);
        SwingUtilities.invokeLater(() -> g2d.drawImage(img, 0, 0, null));
    }

    public void prepareForPPM(Graphics g, int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        super.paintComponents(g);
        revalidate();
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
