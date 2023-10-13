package state;

import components.Frame;
import components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawLineState extends CanvasState{

    public DrawLineState(MainPanel panel, Frame frame) {
        super(panel, frame);
    }

    @Override
    public void draw() {
        Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
        Point2D startingPoint = new Point2D.Float(mousePressedPoint.getX(), mousePressedPoint.getY());
        Point2D endingPoint = new Point2D.Float(mouseReleasedPoint.getX(), mouseReleasedPoint.getY());
        Line2D line = new Line2D.Float(startingPoint, endingPoint);

        g2d.setColor(Color.BLACK);
        g2d.draw(line);

        getCanvas().addShape(line);
    }

    @Override
    public void showDrawingDialog() {
        JPanel drawingDialog = new JPanel();
        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);

        drawingDialog.setLayout(new BoxLayout(drawingDialog, 1));

        drawingDialog.add(new JLabel("x1:"));
        drawingDialog.add(x1Field);
        drawingDialog.add(Box.createVerticalStrut(15));
        drawingDialog.add(new JLabel("y1:"));
        drawingDialog.add(y1Field);
        drawingDialog.add(Box.createVerticalStrut(15));
        drawingDialog.add(new JLabel("x2:"));
        drawingDialog.add(x2Field);
        drawingDialog.add(Box.createVerticalStrut(15));
        drawingDialog.add(new JLabel("y2:"));
        drawingDialog.add(y2Field);

        int result = JOptionPane.showConfirmDialog(frame, drawingDialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());
            int y2 = Integer.parseInt(y2Field.getText());

            Line2D line = new Line2D.Float(x1, y1, x2, y2);
            g2d.draw(line);
            getCanvas().addShape(line);
        }
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
