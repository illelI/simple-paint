package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {

    private BufferedImage image;

    public MainPanel() {
        super();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(image, 0, 0, null);
    }


    public void prepareForPPM(Graphics g, int width, int height) {
        super.paintComponents(g);
        revalidate();
        this.setPreferredSize(new Dimension(width, height));
    }

    public float getGreen() {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        float totalPixels = height * width;
        int greenPixels = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = this.image.getRGB(j, i);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                float[] hsv = Color.RGBtoHSB(red, green, blue, null);
                hsv[0] *= 179;
                if (hsv[0] > 40 && hsv[0] < 108) {
                    greenPixels++;
                }
            }
        }
        return (greenPixels / totalPixels) * 100;
    }

}
