package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {

    private BufferedImage image;
    private int[][] structuringElement;

    public MainPanel() {
        super();
        structuringElement = new int[3][3];
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setStructuringElement(int[][] structuringElement) {
        this.structuringElement = structuringElement;
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

    public void dilate() {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (image.getRGB(x, y) == Color.WHITE.getRGB()) {
                    for (int i = 0; i < structuringElement.length; i++) {
                        for (int j = 0; j < structuringElement[i].length; j++) {
                            int newX = x + i - structuringElement.length / 2;
                            int newY = y + j - structuringElement[i].length / 2;

                            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                                outputImage.setRGB(newX, newY, Color.WHITE.getRGB());
                            }
                        }
                    }
                }
            }
        }
        this.image = outputImage;
        repaint();
    }

    public void erode() {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean performErosion = true;

                for (int i = 0; i < structuringElement.length; i++) {
                    for (int j = 0; j < structuringElement[i].length; j++) {
                        int newX = x + i - structuringElement.length / 2;
                        int newY = y + j - structuringElement[i].length / 2;

                        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                            if (image.getRGB(newX, newY) != Color.WHITE.getRGB()) {
                                performErosion = false;
                                break;
                            }
                        } else {
                            performErosion = false;
                            break;
                        }
                    }

                    if (!performErosion) {
                        break;
                    }
                }

                if (performErosion) {
                    outputImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        this.image = outputImage;
        repaint();
    }

    public void opening() {
        erode();
        dilate();
    }

    public void closing() {
        dilate();
        erode();
    }

    public void hitOrMiss() {
        int size = structuringElement[0].length;
        int[][] missed = new int[size][size];
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((structuringElement[i][j] == 1)) {
                    missed[i][j] = 0;
                } else {
                    missed[i][j] = 1;
                }
            }
        }

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                boolean isHit = true;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = image.getRGB(x + i, y + j);
                        int pixelValue = (rgb >> 16) & 0xFF;

                        if (structuringElement[j + 1][i + 1] == 1 && pixelValue != 255) {
                            isHit = false;
                            break;
                        }
                        if (missed[j + 1][i + 1] == 1 && pixelValue != 0) {
                            isHit = false;
                            break;
                        }
                    }

                    if (!isHit) {
                        break;
                    }
                }

                int resultRGB = isHit ? 0xFFFFFF : 0x000000;
                newImage.setRGB(x, y, resultRGB);
            }
        }
        this.image = newImage;
        repaint();
    }

}
