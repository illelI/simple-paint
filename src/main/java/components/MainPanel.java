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
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

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

    public void addOperation(Color values) {
        if (image != null) {
            int tmpR = 0;
            int tmpG = 0;
            int tmpB = 0;
            Color tmpColor;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    tmpColor = new Color(image.getRGB(i, j));
                    tmpR = tmpColor.getRed() + values.getRed();
                    tmpG = tmpColor.getGreen() + values.getGreen();
                    tmpB = tmpColor.getBlue() + values.getBlue();
                    if (tmpR > 255) tmpR = 255;
                    if (tmpG > 255) tmpG = 255;
                    if (tmpB > 255) tmpB = 255;
                    image.setRGB(i, j, new Color(tmpR, tmpG, tmpB).getRGB());
                }
            }
            repaint();
        }
    }

    public void subtractOperation(Color values) {
        if (image != null) {
            int tmpR = 0;
            int tmpG = 0;
            int tmpB = 0;
            Color tmpColor;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    tmpColor = new Color(image.getRGB(i, j));
                    tmpR = tmpColor.getRed() - values.getRed();
                    tmpG = tmpColor.getGreen() - values.getGreen();
                    tmpB = tmpColor.getBlue() - values.getBlue();
                    if (tmpR < 0) tmpR = 0;
                    if (tmpG < 0) tmpG = 0;
                    if (tmpB < 0) tmpB = 0;
                    image.setRGB(i, j, new Color(tmpR, tmpG, tmpB).getRGB());
                }
            }
            repaint();
        }
    }

    public void multiplyOperation(Color values) {
        if (image != null) {
            int tmpR = 0;
            int tmpG = 0;
            int tmpB = 0;
            Color tmpColor;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    tmpColor = new Color(image.getRGB(i, j));
                    tmpR = tmpColor.getRed() * values.getRed();
                    tmpG = tmpColor.getGreen() * values.getGreen();
                    tmpB = tmpColor.getBlue() * values.getBlue();
                    if (tmpR > 255) tmpR = 255;
                    if (tmpG > 255) tmpG = 255;
                    if (tmpB > 255) tmpB = 255;
                    image.setRGB(i, j, new Color(tmpR, tmpG, tmpB).getRGB());
                }
            }
            repaint();
        }
    }
    public void divideOperation(Color values) {
        if (image != null) {
            int tmpR = 0;
            int tmpG = 0;
            int tmpB = 0;
            Color tmpColor;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    tmpColor = new Color(image.getRGB(i, j));
                    tmpR = tmpColor.getRed() / values.getRed();
                    tmpG = tmpColor.getGreen() / values.getGreen();
                    tmpB = tmpColor.getBlue() / values.getBlue();

                    image.setRGB(i, j, new Color(tmpR, tmpG, tmpB).getRGB());
                }
            }
            repaint();
        }
    }

    public void brightnessOperation(float value) {
        if (image != null) {
            int tmpR = 0;
            int tmpG = 0;
            int tmpB = 0;
            int rgb;
            Color color;
            float[] hsb = new float[]{0, 0, 0, 0};
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    color = new Color(image.getRGB(i, j));
                    tmpR = color.getRed();
                    tmpG = color.getGreen();
                    tmpB = color.getBlue();
                    hsb = Color.RGBtoHSB(tmpR, tmpG, tmpB, hsb);
                    hsb[2] += value;
                    rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                    image.setRGB(i, j, rgb);
                }
            }
            repaint();
        }
    }
    public void greyscaleOperation() {
        if (image != null) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    int gray = (int) (0.2989 * ((rgb >> 16) & 0xFF) + 0.5870 * ((rgb >> 8) & 0xFF) + 0.1140 * (rgb & 0xFF));
                    int grayPixel = (gray << 16) | (gray << 8) | gray;
                    image.setRGB(i, j, grayPixel);
                }
            }
            repaint();
        }
    }

    public void applyAveragingFilter(int kernelSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                int pixelCount = 0;

                for (int dy = -kernelSize; dy <= kernelSize; dy++) {
                    for (int dx = -kernelSize; dx <= kernelSize; dx++) {
                        int neighborX = x + dx;
                        int neighborY = y + dy;

                        if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                            int rgb = image.getRGB(neighborX, neighborY);
                            redSum += (rgb >> 16) & 0xFF;
                            greenSum += (rgb >> 8) & 0xFF;
                            blueSum += rgb & 0xFF;
                            pixelCount++;
                        }
                    }
                }

                int averagedRed = redSum / pixelCount;
                int averagedGreen = greenSum / pixelCount;
                int averagedBlue = blueSum / pixelCount;

                int averagedRGB = (averagedRed << 16) | (averagedGreen << 8) | averagedBlue;
                outputImage.setRGB(x, y, averagedRGB);
            }
        }
        this.image = outputImage;
        repaint();
    }

    public void applyMedianFilter(int kernelSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);


        int kernelRadius = kernelSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] redValues = new int[kernelSize * kernelSize];
                int[] greenValues = new int[kernelSize * kernelSize];
                int[] blueValues = new int[kernelSize * kernelSize];

                int pixelCount = 0;

                for (int ky = -kernelRadius; ky <= kernelRadius; ky++) {
                    for (int kx = -kernelRadius; kx <= kernelRadius; kx++) {
                        int nx = x + kx;
                        int ny = y + ky;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int pixel = image.getRGB(nx, ny);
                            redValues[pixelCount] = (pixel >> 16) & 0xFF;
                            greenValues[pixelCount] = (pixel >> 8) & 0xFF;
                            blueValues[pixelCount] = pixel & 0xFF;

                            pixelCount++;
                        }
                    }
                }

                Arrays.sort(redValues);
                Arrays.sort(greenValues);
                Arrays.sort(blueValues);

                int medianRed = redValues[pixelCount / 2];
                int medianGreen = greenValues[pixelCount / 2];
                int medianBlue = blueValues[pixelCount / 2];

                int medianRGB = (medianRed << 16) | (medianGreen << 8) | medianBlue;
                outputImage.setRGB(x, y, medianRGB);
            }
        }

        this.image = outputImage;
        repaint();
    }

    public void applySobelFilter() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int pixelX = 0;
                int pixelY = 0;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int rgb = image.getRGB(x + dx, y + dy);
                        int gray = (rgb >> 16) & 0xFF;

                        pixelX += gray * sobelX[dy + 1][dx + 1];
                        pixelY += gray * sobelY[dy + 1][dx + 1];
                    }
                }

                int gradient = (int) Math.sqrt(pixelX * pixelX + pixelY * pixelY);
                gradient = Math.min(255, gradient);

                int newRGB = (gradient << 16) | (gradient << 8) | gradient;
                outputImage.setRGB(x, y, newRGB);
            }
        }

        this.image = outputImage;
        repaint();
    }

    public void applyHighPassSharpenFilter() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] kernel = {
                { -1, -1, -1 },
                { -1,  9, -1 },
                { -1, -1, -1 }
        };

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int rgb = image.getRGB(x + dx, y + dy);
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = rgb & 0xFF;

                        redSum += red * kernel[dy + 1][dx + 1];
                        greenSum += green * kernel[dy + 1][dx + 1];
                        blueSum += blue * kernel[dy + 1][dx + 1];
                    }
                }

                int newRed = Math.min(255, Math.max(0, redSum));
                int newGreen = Math.min(255, Math.max(0, greenSum));
                int newBlue = Math.min(255, Math.max(0, blueSum));

                int newRGB = (newRed << 16) | (newGreen << 8) | newBlue;
                outputImage.setRGB(x, y, newRGB);
            }
        }

        this.image = outputImage;
        repaint();
    }

    public void applyGaussianBlur(int radius) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = 0;
                int green = 0;
                int blue = 0;

                int count = 0;

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int rgb = image.getRGB(nx, ny);
                            red += (rgb >> 16) & 0xFF;
                            green += (rgb >> 8) & 0xFF;
                            blue += rgb & 0xFF;
                            count++;
                        }
                    }
                }

                red /= count;
                green /= count;
                blue /= count;

                int blurredRGB = (red << 16) | (green << 8) | blue;
                blurredImage.setRGB(x, y, blurredRGB);
            }
        }

        this.image = blurredImage;
        repaint();
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
