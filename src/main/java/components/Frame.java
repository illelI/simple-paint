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

    public MainPanel getMainPanel() {
        return this.mainPanel;
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
        JButton calculate = new JButton("calculate");

        calculate.addActionListener(l -> Dialogs.resultDialog(this));

        toolBar.add(calculate);
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
                return fileName.endsWith(".jpg") || fileName.endsWith("*.jpeg") || fileName.endsWith(".ppm") || fileName.endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "*.ppm, *.jpg, *.jpeg, *.png";
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
}
