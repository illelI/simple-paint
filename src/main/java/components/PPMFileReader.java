package components;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PPMFileReader {
    private BufferedReader bufferedReader;
    private MainPanel panel;
    private Frame frame;

    private int width = 0;
    private int height = 0;
    private int maxColor = 0;
    private int currentPositionX;
    private int currentPositionY;

    private String path;
    private int lineNumber = 0;

    private BufferedImage bufferedImage;

    PPMFileReader(String path, MainPanel panel, Frame frame) {
        try {
            this.panel = panel;
            this.frame = frame;
            this.path = path;
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader, 1024 * 1024 * 2);
        } catch (Exception e) {
            Dialogs.fileErrorDialog(frame, "Error during file opening");
        }
    }

    public void draw() {
        String line;
        Thread newThread;
        try {
            line = bufferedReader.readLine();
            if (line.equals("P3")) {
                newThread = new Thread(this::drawP3);
                newThread.start();
            } else if (line.equals("P6")) {
                newThread = new Thread(this::drawP6);
                newThread.start();
            } else {
                Dialogs.fileErrorDialog(frame, "File error");
            }
        } catch (Exception e) {
            Dialogs.fileErrorDialog(frame, "Error during file reading");
        }
    }

    private void drawP3() {
        String line;
        int r = -1;
        int g = -1;
        int b = -1;
        double tmpDouble;
        List<String> otherValues = getImageParams();
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

        for (String s : otherValues) {
            if(s.startsWith("#")) {
                break;
            }
            if(s.matches("")) continue;
            if (r == -1) {
                tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                r = (int) tmpDouble;
            } else if (g == -1) {
                tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                g = (int) tmpDouble;
            } else {
                tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                b = (int) tmpDouble;
                g2d.setColor(new Color(r, g, b));
                g2d.fill(new Rectangle2D.Double(currentPositionX++, currentPositionY, 1, 1));
                r = -1;
                g = -1;
                if (currentPositionX == width) {
                    currentPositionX = 0;
                    currentPositionY++;
                }
            }
        }
        try {
            line = bufferedReader.readLine().trim();
            while (line != null) {
                line = line.trim();
                for (String s : line.split("\\s")) {
                    if(s.startsWith("#")) {
                        break;
                    }
                    if(s.equals("")) continue;
                    if (r == -1) {
                        tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                        r = (int) tmpDouble;
                    } else if (g == -1) {
                        tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                        g = (int) tmpDouble;
                    } else {
                        tmpDouble = Math.floor(Double.parseDouble(s) / maxColor * 255);
                        b = (int) tmpDouble;
                        g2d.setColor(new Color(r, g, b));
                        g2d.fill(new Rectangle2D.Double(currentPositionX++, currentPositionY, 1, 1));
                        r = -1;
                        g = -1;
                        if (currentPositionX == width) {
                            currentPositionX = 0;
                            currentPositionY++;
                        }
                    }
                }
                line = bufferedReader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.fileErrorDialog(frame, "File error");
        }
        panel.setImage(bufferedImage);
        panel.repaint();
    }

    private void drawP6() {
        double tmpDouble;
        int r = -1;
        int g = -1;
        int b = -1;
        getImageParams();
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        try {
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path), 1024 * 1024));
            for(int i = 0; i < lineNumber + 1; i++) inputStream.readLine();
            int byteFromFile = inputStream.read();
            while (byteFromFile != -1) {
                if(r == -1) {
                    tmpDouble = Math.floor((double) byteFromFile / maxColor * 255);
                    r = (int) tmpDouble;
                } else if (g == -1) {
                    tmpDouble = Math.floor((double) byteFromFile / maxColor * 255);
                    g = (int) tmpDouble;
                } else {
                    tmpDouble = Math.floor((double) byteFromFile / maxColor * 255);
                    b = (int) tmpDouble;
                    g2d.setColor(new Color(r, g, b));
                    g2d.fill(new Rectangle2D.Double(currentPositionX++, currentPositionY, 1, 1));
                    r = -1;
                    g = -1;
                    if (currentPositionX == width) {
                        currentPositionX = 0;
                        currentPositionY++;
                    }
                }
                byteFromFile = inputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.fileErrorDialog(frame, "File error");
        }
        panel.setImage(bufferedImage);
        panel.repaint();
    }

    private List<String> getImageParams() {
        String line;
        List<String> otherValues = new ArrayList<>();
        try {
            while (maxColor == 0) {
                line = bufferedReader.readLine().trim();
                lineNumber++;
                for (String s : line.split("\\s")) {
                    if(s.startsWith("#")) {
                        break;
                    }
                    if(s.equals("")) continue;
                    if(width == 0) {
                        width = Integer.parseInt(s);
                    } else if (height == 0) {
                        height = Integer.parseInt(s);
                    }
                    else if (maxColor == 0){
                        maxColor = Integer.parseInt(s);
                    } else {
                        otherValues.add(s);
                    }
                }
            }
        } catch (Exception e) {
            Dialogs.fileErrorDialog(frame, "File error");
        }
        panel.prepareForPPM(panel.getGraphics(), width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        currentPositionX = 0;
        currentPositionY = 0;
        return otherValues;
    }
}
