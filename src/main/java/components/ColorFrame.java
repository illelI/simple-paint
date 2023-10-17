package components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ColorFrame extends JFrame {

    private final ColorsPanel colorsPanel;
    private Color chosenColor;
    private final JPanel labelsPanel;

    private final JLabel colorLabel;

    private final JTextField redField;
    private final JTextField greenField;
    private final JTextField blueField;
    private final JTextField cyanField;
    private final JTextField magentaField;
    private final JTextField yellowField;
    private final JTextField blackField;
    private boolean isDuringChange = false;
    private ColorsPanel colorSlider;


    public ColorFrame() {
        super("Change color");
        colorsPanel = new ColorsPanel(300, 300);
        labelsPanel = new JPanel();
        chosenColor = new Color(0,0,0);
        colorSlider = new ColorsPanel(10, 200);
        JPanel colorChoosePanel = new JPanel();
        colorChoosePanel.setSize(new Dimension(350, 350));
        colorChoosePanel.setPreferredSize(new Dimension(350, 300));

        colorChoosePanel.setLayout(new BoxLayout(colorChoosePanel, BoxLayout.X_AXIS));

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        colorLabel = new JLabel(getIconFromColor());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


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
        redField.setText(String.valueOf(chosenColor.getRed()));

        JLabel greenLabel = new JLabel("Green:");
        greenField = new JTextField(3);
        greenField.setText(String.valueOf(chosenColor.getGreen()));

        JLabel blueLabel = new JLabel("Blue:");
        blueField = new JTextField(3);
        blueField.setText(String.valueOf(chosenColor.getBlue()));

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
        setColorsPanel();
        colorChoosePanel.add(colorsPanel);
        colorChoosePanel.add(colorSlider);
        add(colorChoosePanel);
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


            chosenColor = new Color((int) (red * 255) , (int) (green * 255), (int) (blue * 255));
            colorLabel.setIcon(getIconFromColor());

        } catch (Exception e) { //
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

            chosenColor = new Color(red, green, blue);
            colorLabel.setIcon(getIconFromColor());

        } catch (Exception e) { //
        } finally {
            isDuringChange = false;
        }
    }

    public Icon getIconFromColor() {
        try {
            BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setPaint(chosenColor);
            g2d.fillRect(0, 0, 50, 50);
            return new ImageIcon(image);
        } catch (Exception e) {
            return null;
        }
    }

    private void setColorSlider() {
        BufferedImage colorsImage = new BufferedImage(20, 127, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = colorsImage.createGraphics();

        for (int i = 0; i < 127; i++) {
            for (int j = 0; j < 20; j++) {
                //zrobie później
            }
        }

    }


    private void setColorsPanel() {
        BufferedImage colorsImage = new BufferedImage(255 * 6, 127, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = colorsImage.createGraphics();

        float r = 255;
        float b = 0;
        float g = 0;

        for(int i = 0; i < 128; i++) {
            for (int j = 255; j > 0; j--) {
                r = 255f - i;
                g = 255f - j;
                b = i;
                g = scaleColor(g , i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 - j, i, 1, 1);
            }
            for (int j = 0; j < 256; j++) {
                g = 255f - i;
                r = 255f - j;
                b = i;
                r = scaleColor(r, i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 + j, i, 1, 1);
            }
            for (int j = 255; j > -1; j--) {
                r = i;
                g = 255f - i;
                b = 255f - j;
                b = scaleColor(b, i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 * 3 - j, i, 1, 1);
            }
            for (int j = 0; j < 256; j++) {
                r = i;
                b = 255f - i;
                g = 255f - j;
                g = scaleColor(g, i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 * 3 + j, i, 1, 1);
            }
            for (int j = 255; j > 0; j--) {
                g = i;
                b = 255f - i;
                r = 255f - j;
                r = scaleColor(r, i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 * 5 - j, i, 1, 1);
            }
            for (int j = 0; j < 256; j++) {
                g = i;
                r = 255f - i;
                b = 255f - j;
                b = scaleColor(b, i);
                g2d.setColor(new Color((int)r, (int)g, (int)b));
                g2d.drawRect(255 * 5 + j, i, 1, 1);
            }
        }
        colorsPanel.setImage(colorsImage);
    }

    private class ColorsPanel extends JPanel {
        private BufferedImage image;

        ColorsPanel(int width, int height) {
            super();
            setSize(new Dimension(width, height));
            setPreferredSize(new Dimension(width, height));
        }
        void setImage(BufferedImage img) {
            image = img;
            repaint();
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform at = new AffineTransform();
            at.scale(0.5, 1);
            if(image != null) {
                at.scale((double) getWidth() / image.getWidth(), 1.5);//(double)getHeight() / image.getHeight());
                g2d.drawImage(image, at, null);
            }
        }
    }

    private float scaleColor(float color, int i) {
        if (i == 0) return color;
        if(color > 127 ) {
            color = color - i*(color - 127)/127;
        } else if (color < 127 ) {
            color = color + i*(127 - color)/127;
        }
        return color;
    }

}
