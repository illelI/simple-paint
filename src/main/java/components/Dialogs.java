package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Dialogs {

    private Dialogs() {}

    public static void circleDialog(MainPanel mainPanel, Frame frame, Ellipse2D circle) {

        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField centerX = new JTextField(5);
        JTextField centerY = new JTextField(5);
        JTextField radiusInput = new JTextField(5);

        Shape currentShape;

        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapesList()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        dialog.setLayout(new BoxLayout(dialog, 3));

        dialog.add(new JLabel("X coordinate of circle center:"));
        dialog.add(centerX);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Y coordinate of circle center:"));
        dialog.add(centerY);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Circle radius:"));
        dialog.add(radiusInput);

        centerX.setText(String.valueOf(circle.getCenterX()));
        centerY.setText(String.valueOf(circle.getCenterY()));
        radiusInput.setText(String.valueOf(circle.getHeight()/2));

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            mainPanel.getState().getCanvas().removeShape(circle);
            currentShape = new Ellipse2D.Double(Double.parseDouble(centerX.getText()) - Double.parseDouble(radiusInput.getText()),
                    Double.parseDouble(centerY.getText()) - Double.parseDouble(radiusInput.getText()),
                    Double.parseDouble(radiusInput.getText()) * 2, Double.parseDouble(radiusInput.getText()) * 2);

            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            mainPanel.getState().getCanvas().addShape(currentShape);
        }

    }

    public static void lineDialog(MainPanel mainPanel, Frame frame, Line2D line) {
        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);

        Shape currentShape;

        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapesList()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        dialog.setLayout(new BoxLayout(dialog, 1));

        dialog.add(new JLabel("x1:"));
        dialog.add(x1Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("y1:"));
        dialog.add(y1Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("x2:"));
        dialog.add(x2Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("y2:"));
        dialog.add(y2Field);

        x1Field.setText(String.valueOf(line.getX1()));
        y1Field.setText(String.valueOf(line.getY1()));
        x2Field.setText(String.valueOf(line.getX2()));
        y2Field.setText(String.valueOf(line.getX2()));

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Change values:", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            mainPanel.getState().getCanvas().removeShape(line);
            currentShape = new Line2D.Double(Double.parseDouble(x1Field.getText()), Double.parseDouble(y1Field.getText()),
                    Double.parseDouble(x2Field.getText()), Double.parseDouble(y2Field.getText()));
            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            mainPanel.getState().getCanvas().addShape(currentShape);
        }

    }

    public static void rectangleDialog(MainPanel mainPanel, Frame frame, Rectangle2D rect) {
        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField upperLeftX = new JTextField(5);
        JTextField upperLeftY = new JTextField(5);
        JTextField width = new JTextField(5);
        JTextField height = new JTextField(5);

        Shape currentShape;

        dialog.setLayout(new BoxLayout(dialog, 3));

        dialog.add(new JLabel("X coordinate of upper left corner:"));
        dialog.add(upperLeftX);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Y coordinate of upper left corner:"));
        dialog.add(upperLeftY);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Width:"));
        dialog.add(width);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Height:"));
        dialog.add(height);

        upperLeftX.setText(String.valueOf(rect.getX()));
        upperLeftY.setText(String.valueOf(rect.getY()));
        width.setText(String.valueOf(rect.getWidth()));
        height.setText(String.valueOf(rect.getHeight()));


        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapesList()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            mainPanel.getState().getCanvas().removeShape(rect);

            currentShape = new Rectangle2D.Double(Double.parseDouble(upperLeftX.getText()), Double.parseDouble(upperLeftY.getText()),
                    Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));

            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            mainPanel.getState().getCanvas().addShape(currentShape);
        }

    }

    public static void fileErrorDialog(Frame frame, String message) {
        JPanel dialog = new JPanel();
        dialog.add(new JLabel(message));
        JOptionPane.showMessageDialog(frame, dialog);
    }

    public static float compressionDialog(Frame frame) {
        JPanel dialog = new JPanel();
        JTextField ratioField = new JTextField(5);

        dialog.setLayout(new BoxLayout(dialog, 2));

        dialog.add(new JLabel("Compression ratio"));
        ratioField.setText("1.0");
        dialog.add(ratioField);
        int result = JOptionPane.showConfirmDialog(frame, dialog, "Enter value: ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return Float.parseFloat(ratioField.getText());
        }
        return 0;
    }

    public static void operationsDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add");
        JButton subtractButton = new JButton("Subtract");
        JButton multiplyButton = new JButton("Multiply");
        JButton divideButton = new JButton("Divide");
        JButton changeBrightnessButton = new JButton("Change brightness");
        JButton greyScaleButton = new JButton("Greyscale");

        addButton.addActionListener(l -> Dialogs.addOperationDialog(frame));
        subtractButton.addActionListener(l -> Dialogs.subtractOperationDialog(frame));
        multiplyButton.addActionListener(l -> Dialogs.multiplyOperationDialog(frame));
        divideButton.addActionListener(l -> Dialogs.divideOperationDialog(frame));
        changeBrightnessButton.addActionListener(l -> brightnessOperationDialog(frame));
        greyScaleButton.addActionListener(l -> frame.getMainPanel().greyscaleOperation());

        dialog.add(addButton);
        dialog.add(subtractButton);
        dialog.add(multiplyButton);
        dialog.add(divideButton);
        dialog.add(changeBrightnessButton);
        dialog.add(greyScaleButton);

        JOptionPane.showMessageDialog(frame, dialog, "", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void filtersDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JButton averagingFilterButton = new JButton("Averaging");
        JButton medianFilterButton = new JButton("Median");
        JButton sobelFilterButton = new JButton("Sobel");
        JButton highPassFilterButton = new JButton("High-pass");
        JButton gaussFilterButton = new JButton("Gauss");

        averagingFilterButton.addActionListener(l -> frame.getMainPanel().applyAveragingFilter(3));
        medianFilterButton.addActionListener(l -> frame.getMainPanel().applyMedianFilter(3));
        sobelFilterButton.addActionListener(l -> frame.getMainPanel().applySobelFilter());
        highPassFilterButton.addActionListener(l -> frame.getMainPanel().applyHighPassSharpenFilter());
        gaussFilterButton.addActionListener(l -> frame.getMainPanel().applyGaussianBlur(5));

        dialog.add(averagingFilterButton);
        dialog.add(medianFilterButton);
        dialog.add(sobelFilterButton);
        dialog.add(highPassFilterButton);
        dialog.add(gaussFilterButton);

        JOptionPane.showMessageDialog(frame, dialog, "", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addOperationDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();

        dialog.add(new JLabel("Red: "));
        dialog.add(redTextField);
        dialog.add(new JLabel("Green: "));
        dialog.add(greenTextField);
        dialog.add(new JLabel("Blue: "));
        dialog.add(blueTextField);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Add", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            frame.getMainPanel().addOperation(new Color(Integer.parseInt(redTextField.getText()), Integer.parseInt(greenTextField.getText()), Integer.parseInt(blueTextField.getText())));
        }
    }

    private static void subtractOperationDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();

        dialog.add(new JLabel("Red: "));
        dialog.add(redTextField);
        dialog.add(new JLabel("Green: "));
        dialog.add(greenTextField);
        dialog.add(new JLabel("Blue: "));
        dialog.add(blueTextField);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Subtract", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            frame.getMainPanel().subtractOperation(new Color(Integer.parseInt(redTextField.getText()), Integer.parseInt(greenTextField.getText()), Integer.parseInt(blueTextField.getText())));
        }
    }
    private static void multiplyOperationDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();

        dialog.add(new JLabel("Red: "));
        dialog.add(redTextField);
        dialog.add(new JLabel("Green: "));
        dialog.add(greenTextField);
        dialog.add(new JLabel("Blue: "));
        dialog.add(blueTextField);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Subtract", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            frame.getMainPanel().multiplyOperation(new Color(Integer.parseInt(redTextField.getText()), Integer.parseInt(greenTextField.getText()), Integer.parseInt(blueTextField.getText())));
        }
    }
    private static void divideOperationDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();

        dialog.add(new JLabel("Red: "));
        dialog.add(redTextField);
        dialog.add(new JLabel("Green: "));
        dialog.add(greenTextField);
        dialog.add(new JLabel("Blue: "));
        dialog.add(blueTextField);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Subtract", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            frame.getMainPanel().divideOperation(new Color(Integer.parseInt(redTextField.getText()), Integer.parseInt(greenTextField.getText()), Integer.parseInt(blueTextField.getText())));
        }
    }
    private static void brightnessOperationDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JTextField brightnessTextField = new JTextField();

        dialog.add(new JLabel("Brightness: "));
        dialog.add(brightnessTextField);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "Subtract", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            frame.getMainPanel().brightnessOperation(Float.parseFloat(brightnessTextField.getText()));
        }
    }


}
