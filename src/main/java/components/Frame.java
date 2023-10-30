package components;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Frame extends JFrame {

    private MainPanel mainPanel;
    public Frame(String title, int xSize, int ySize) {
        super(title);
        mainPanel = new MainPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.white));


        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        setSize(xSize, ySize);

        createMenuBar();
        createToolbar();

    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem importItem = new JMenuItem("Import");
        importItem.setActionCommand("Import");

        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.setActionCommand("Close");

        importItem.addActionListener(l -> chooseImage());


        fileMenu.add(importItem);
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar();
        JButton setStructuring = new JButton("set structuring element");
        JButton dilatation = new JButton("dilatation");
        JButton erosion = new JButton("erosion");
        JButton opening = new JButton("opening");
        JButton closing = new JButton("closing");
        JButton hitOrMiss = new JButton("hit or miss");

        setStructuring.addActionListener(l -> Dialogs.setStructuringElement(this));
        dilatation.addActionListener(l -> mainPanel.dilate());
        erosion.addActionListener(l -> mainPanel.erode());
        opening.addActionListener(l -> mainPanel.opening());
        closing.addActionListener(l -> mainPanel.closing());
        hitOrMiss.addActionListener(l -> mainPanel.hitOrMiss());

        toolBar.add(setStructuring);
        toolBar.add(dilatation);
        toolBar.add(erosion);
        toolBar.add(opening);
        toolBar.add(closing);
        toolBar.add(hitOrMiss);
        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);
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
            BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.drawImage(image,0, 0, null);
            mainPanel.setImage(bi);
        } else if (extension.equals("ppm")) {
            PPMFileReader reader = new PPMFileReader(filePath, mainPanel, this);
            reader.draw();

        } else {
        }
    }

    public MainPanel getMainPanel() {
        return this.mainPanel;
    }
}
