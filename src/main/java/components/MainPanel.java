package components;

import model.ShapesList;
import state.CanvasState;
import state.SelectState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

    private CanvasState state;
    private Frame holder;

    private BufferedImage image;
    private double zoom = 1.0;
    private int width;
    private int height;

    public MainPanel(Frame frame) {
        super();
        this.holder = frame;
        this.state = new SelectState(this, holder);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        width = getWidth();
        height = getHeight();

    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
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

    public void zoomIn() {
        zoom += 0.25;
        width *= zoom;
        height *= zoom;
        this.setSize(new Dimension(width, height));
        repaint();
    }

    public void zoomOut() {
        if(zoom > 0.25) {
            width /= zoom;
            height /= zoom;
            this.setSize(new Dimension(width, height));
            zoom -= 0.25;
            repaint();
        }
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
            AffineTransform at = new AffineTransform();
            at.scale(zoom, zoom);
            g2d.drawImage(image, at, this);
            revalidate();
            setSize(width, height);
        }
    }

    public void paintImage(Graphics g, Image img) {
        zoom = 1.0;
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage tmpImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D imgGraphics = tmpImg.createGraphics();
        imgGraphics.drawImage(img,0,0,null);
        imgGraphics.dispose();
        this.image = tmpImg;
        super.paintComponents(g2d);
        revalidate();
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        //SwingUtilities.invokeLater(() -> g2d.drawImage(img, 0, 0, null));
    }

    public void prepareForPPM(Graphics g, int width, int height) {
        zoom = 1.0;
        super.paintComponents(g);
        revalidate();
        this.setPreferredSize(new Dimension(width, height));
        this.height = height;
        this.width = width;
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
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;

        try {
            Robot robot = new Robot();
            holder.updateColor(robot.getPixelColor(x, y));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
