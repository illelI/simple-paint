package components;

import model.ShapesList;
import state.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;


public class Frame extends JFrame {

    private MainPanel mainPanel;
    private JLabel colorInfo;
    private ColorFrame colorFrame;
    private JPanel statusPanel;
    public Frame(String title, int xSize, int ySize) {
        super(title);
        mainPanel = new MainPanel(this);
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        colorFrame = new ColorFrame();
        colorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        setSize(xSize, ySize);

        createMenuBar();
        createToolbar();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane);

        statusPanel = new JPanel();
        colorInfo = new JLabel();
        statusPanel.add(colorInfo);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand("New");

        JMenuItem importItem = new JMenuItem("Import");
        importItem.setActionCommand("Import");

        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.setActionCommand("Export");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.setActionCommand("Close");

        newMenuItem.addActionListener(l -> System.out.println("New clicked"));

        importItem.addActionListener(l -> chooseImage());

        saveMenuItem.addActionListener(l -> {
            try {
                File file = new File("save.txt");
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream("save.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(mainPanel.getState().getCanvas().getShapes());
                objectOutputStream.flush();
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        openMenuItem.addActionListener(l -> {
            try {
                FileInputStream fileInputStream = new FileInputStream("save.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ShapesList shapesList = (ShapesList) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                mainPanel.load(shapesList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        closeMenuItem.addActionListener(l -> System.exit(0));

        exportItem.addActionListener(l -> exportImage());

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar();
        JToggleButton selectBtn = new JToggleButton("Select");
        JToggleButton paintBtn = new JToggleButton("Paint");
        JComboBox shapesBox = new JComboBox(new String[] {"Line", "Rectangle", "Circle", "Free", "Free (points)"});
        JButton drawFromTextField = new JButton("Draw from text field");
        JButton changeShape = new JButton("Change");
        JButton zoomInButton = new JButton("+");
        JButton zoomOutButton = new JButton("-");

        JButton colorsButton = new JButton("colors");

        selectBtn.addActionListener( l -> {
            selectBtn.setEnabled(false);
            paintBtn.setSelected(false);
            paintBtn.setEnabled(true);
            mainPanel.changeState(new SelectState(mainPanel, this));
            changeShape.setEnabled(true);
        });

        paintBtn.addActionListener(l -> {
            selectBtn.setEnabled(true);
            selectBtn.setSelected(false);
            paintBtn.setEnabled(false);
            changeShape.setEnabled(false);
            shapesBoxState(shapesBox);
        });

        shapesBox.addActionListener(l -> {
            if (paintBtn.isSelected()) {
                shapesBoxState(shapesBox);
            }
            drawFromTextField.setEnabled(shapesBox.getSelectedIndex() <= 2);

        });

        drawFromTextField.addActionListener(l -> {
            if(mainPanel.getState() instanceof SelectState) {
                shapesBoxState(shapesBox);

                mainPanel.getState().showDrawingDialog();

                mainPanel.changeState(new SelectState(mainPanel, this));
            }
            else {
                mainPanel.getState().showDrawingDialog();
            }
        });

        changeShape.addActionListener(l -> {
                if(!(mainPanel.getState() instanceof SelectState)) {
                    JOptionPane.showMessageDialog(this, new JLabel("Select shape"));
                    return;
                }

                Shape shape = ((SelectState) mainPanel.getState()).getCurrentShape();
                switch (shape) {
                    case Ellipse2D circle -> Dialogs.circleDialog(mainPanel, this, circle);
                    case Rectangle2D rect -> Dialogs.rectangleDialog(mainPanel, this, rect);
                    case Line2D line -> Dialogs.lineDialog(mainPanel, this, line);
                    case null -> JOptionPane.showMessageDialog(this, new JLabel("Select shape"));
                    default -> JOptionPane.showMessageDialog(this, new JLabel("unsupported shape"));
                }
        });

        zoomInButton.addActionListener( l -> mainPanel.zoomIn());
        zoomOutButton.addActionListener(l -> mainPanel.zoomOut());

        colorsButton.addActionListener(l -> openColorMenu());

        selectBtn.setSelected(true);

        toolBar.add(selectBtn);
        toolBar.add(paintBtn);
        toolBar.add(shapesBox);
        toolBar.add(drawFromTextField);
        toolBar.add(changeShape);
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(colorsButton);

        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);
    }

    private void shapesBoxState(JComboBox shapesBox) {
        switch (shapesBox.getSelectedIndex()) {
            case 0 -> mainPanel.changeState(new DrawLineState(mainPanel, this));
            case 1 -> mainPanel.changeState(new DrawRectangleState(mainPanel, this));
            case 2 -> mainPanel.changeState(new DrawCircleState(mainPanel, this));
            case 3 -> mainPanel.changeState(new DrawFreeState(mainPanel, this));
            case 4 -> mainPanel.changeState(new DrawFree2State(mainPanel, this));
        }
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) return true;
                String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".jpg") || fileName.endsWith("*.jpeg") || fileName.endsWith(".ppm");
            }

            @Override
            public String getDescription() {
                return "*.ppm, *.jpg, *.jpeg";
            }
        });
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadImage(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void loadImage(String filePath) {
        int pathLength = filePath.length();
        String extension = filePath.toLowerCase().substring(pathLength-3);
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            ImageIcon imageIcon = new ImageIcon(filePath);
            Image image = imageIcon.getImage();
            mainPanel.getState().getCanvas().flushShapes();
            mainPanel.paintImage(mainPanel.getGraphics(), image);
        } else if (extension.equals("ppm")) {
            PPMFileReader reader = new PPMFileReader(filePath, mainPanel, this);
            reader.draw();

        } else {
            Dialogs.fileErrorDialog(this, "Unsupported file format");
        }
    }

    private void exportImage() {
        if (mainPanel.getImage() == null) {
            Dialogs.fileErrorDialog(this, "Firstly import a file");
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                float ratio = Dialogs.compressionDialog(this);
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".jpg");
                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                    ImageWriter writer = writers.next();
                    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(file));
                    writer.setOutput(imageOutputStream);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(ratio);
                    writer.write(null, new IIOImage(mainPanel.getImage(), null, null), param);
                    imageOutputStream.close();
                    writer.dispose();
            } catch (Exception e) {
                Dialogs.fileErrorDialog(this, "Error during saving a file");
            }
        }
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void updateColor(Color c) {
        colorInfo.setText("rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
        statusPanel.repaint();
    }

    private void openColorMenu() {
        colorFrame.setVisible(true);
    }
}
