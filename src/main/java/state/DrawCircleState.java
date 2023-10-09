package state;

import components.Frame;
import components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class DrawCircleState extends CanvasState{

    public DrawCircleState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        int x1 = mousePressedPoint.getX();
        int x2 = mouseReleasedPoint.getX();
        int y1 = mousePressedPoint.getY();
        int y2 = mouseReleasedPoint.getY();

        double diameter = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        Ellipse2D circle;

        if (x1 < x2 && y1 > y2) {
            circle = new Ellipse2D.Double(x1, y2, diameter, diameter);
        } else if (x1 < x2 && y1 < y2) {
            circle = new Ellipse2D.Double(x1, y1, diameter, diameter);
        } else if (x1 > x2 && y1 > y2) {
            circle = new Ellipse2D.Double(x2, y2, diameter, diameter);
        } else {
            circle = new Ellipse2D.Double(x2, y1, diameter, diameter);
        }


        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
        getCanvas().getShapes().getShapes().add(circle);
    }

    @Override
    public void showDrawingDialog() {
        JPanel drawingDialog = new JPanel();
        JTextField centerX = new JTextField(5);
        JTextField centerY = new JTextField(5);
        JTextField radiusInput = new JTextField(5);

        drawingDialog.setLayout(new BoxLayout(drawingDialog, 3));

        drawingDialog.add(new JLabel("X coordinate of circle center:"));
        drawingDialog.add(centerX);
        drawingDialog.add(Box.createVerticalStrut(15));
        drawingDialog.add(new JLabel("Y coordinate of circle center:"));
        drawingDialog.add(centerY);
        drawingDialog.add(Box.createVerticalStrut(15));
        drawingDialog.add(new JLabel("Circle radius:"));
        drawingDialog.add(radiusInput);

        int result = JOptionPane.showConfirmDialog(frame, drawingDialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            int x = Integer.parseInt(centerX.getText());
            int y = Integer.parseInt(centerY.getText());
            int radius = Integer.parseInt(radiusInput.getText());
            Ellipse2D circle = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
            g2d.draw(circle);
            getCanvas().getShapes().getShapes().add(circle);
        }

    }

    @Override
    public void showChangePropertiesDialog() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedPoint = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleasedPoint = new Point(e.getX(), e.getY());
        draw();
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
