package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorFrame extends JFrame {

    private JPanel colorsPanel;
    private Color choosenColor;
    private JPanel labelsPanel;

    private JLabel colorLabel;

    private JTextField redField;
    private JTextField greenField;
    private JTextField blueField;
    private JTextField cyanField;
    private JTextField magentaField;
    private JTextField yellowField;
    private JTextField blackField;
    private boolean isDuringChange = false;


    public ColorFrame() {
        super("Change color");
        colorsPanel = new JPanel();
        labelsPanel = new JPanel();
        choosenColor = new Color(0,0,0);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        colorLabel = new JLabel(getIconFromColor());


        setSize(new Dimension(380, 400));

        DocumentListener changeRGBListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!isDuringChange)
                    convertRGBtoCMYK();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isDuringChange)
                    convertRGBtoCMYK();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!isDuringChange)
                    convertRGBtoCMYK();
            }
        };
        DocumentListener changeCMYKListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isDuringChange)
                    convertCMYKtoRGB();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isDuringChange)
                    convertCMYKtoRGB();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!isDuringChange)
                    convertCMYKtoRGB();
            }
        };

        JLabel redLabel = new JLabel("Red:");
        redField = new JTextField(3);
        redField.setText(String.valueOf(choosenColor.getRed()));

        JLabel greenLabel = new JLabel("Green:");
        greenField = new JTextField(3);
        greenField.setText(String.valueOf(choosenColor.getGreen()));

        JLabel blueLabel = new JLabel("Blue:");
        blueField = new JTextField(3);
        blueField.setText(String.valueOf(choosenColor.getBlue()));

        JLabel cyanLabel = new JLabel("Cyan:");
        cyanField = new JTextField(3);

        JLabel magentaLabel = new JLabel("Magenta:");
        magentaField = new JTextField(3);

        JLabel yellowLabel = new JLabel("Yellow:");
        yellowField = new JTextField(3);

        JLabel blackLabel = new JLabel("Black:");
        blackField = new JTextField(3);

        convertRGBtoCMYK();

        redField.getDocument().addDocumentListener(changeRGBListener);
        greenField.getDocument().addDocumentListener(changeRGBListener);
        blueField.getDocument().addDocumentListener(changeRGBListener);
        cyanField.getDocument().addDocumentListener(changeCMYKListener);
        magentaField.getDocument().addDocumentListener(changeCMYKListener);
        yellowField.getDocument().addDocumentListener(changeCMYKListener);
        blackField.getDocument().addDocumentListener(changeCMYKListener);

        labelsPanel.add(redLabel);
        labelsPanel.add(redField);
        labelsPanel.add(greenLabel);
        labelsPanel.add(greenField);
        labelsPanel.add(blueLabel);
        labelsPanel.add(blueField);
        labelsPanel.add(Box.createHorizontalStrut(380));
        labelsPanel.add(cyanLabel);
        labelsPanel.add(cyanField);
        labelsPanel.add(magentaLabel);
        labelsPanel.add(magentaField);
        labelsPanel.add(yellowLabel);
        labelsPanel.add(yellowField);
        labelsPanel.add(blackLabel);
        labelsPanel.add(blackField);
        labelsPanel.add(Box.createHorizontalStrut(380));
        labelsPanel.add(colorLabel);
        labelsPanel.add(Box.createHorizontalStrut(380));
        labelsPanel.add(okButton);
        labelsPanel.add(cancelButton);
        add(colorsPanel);
        add(labelsPanel);
    }

    private void convertRGBtoCMYK() {
        try {
            isDuringChange = true;
            double red = Double.parseDouble(redField.getText()) / 255;
            double green = Double.parseDouble(greenField.getText()) / 255;
            double blue = Double.parseDouble(blueField.getText()) / 255;
            double black = 1 - Math.max(Math.max(red, green), blue);

            blackField.setText(String.valueOf((int) (black * 100)));
            cyanField.setText(String.valueOf((int) (((1 - red - black) / (1 - black)) * 100)));
            magentaField.setText(String.valueOf((int) (((1 - green - black) / (1 - black)) * 100)));
            yellowField.setText(String.valueOf((int) (((1 - blue - black) / (1 - black)) * 100)));


            choosenColor = new Color((int) (red * 255) , (int) (green * 255), (int) (blue * 255));
            colorLabel.setIcon(getIconFromColor());

        } catch (Exception e) {
        } finally {
            isDuringChange = false;
        }
    }

    private void convertCMYKtoRGB() {
        try {
            isDuringChange = true;
            double black = Double.parseDouble(blackField.getText()) / 100;
            double cyan = Double.parseDouble(cyanField.getText()) / 100;
            double magenta = Double.parseDouble(magentaField.getText()) / 100;
            double yellow = Double.parseDouble(yellowField.getText()) / 100;

            int red = (int) Math.round((1 - Math.min(1, cyan  * (1 - black) + black)) * 255);
            int green = (int) Math.round((1 - Math.min(1, magenta * (1 - black) + black)) * 255);
            int blue = (int) Math.round((1 - Math.min(1, yellow * (1 - black) + black)) * 255);

            redField.setText(String.valueOf(red));
            greenField.setText(String.valueOf(green));
            blueField.setText(String.valueOf(blue));

            choosenColor = new Color(red, green, blue);
            colorLabel.setIcon(getIconFromColor());

        } catch (Exception e) {
        } finally {
            isDuringChange = false;
        }
    }

    public Icon getIconFromColor() {
        try {
            BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setPaint(choosenColor);
            g2d.fillRect(0, 0, 50, 50);
            return new ImageIcon(image);
        } catch (Exception e) {
            return null;
        }
    }

}
