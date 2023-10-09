package components;

import model.ShapesList;
import state.*;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.io.*;

public class Frame extends JFrame {

    MainPanel mainPanel;
    public Frame(String title, int xSize, int ySize) {
        super(title);

        mainPanel = new MainPanel(this);
        mainPanel.setBackground(Color.white);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        setSize(xSize, ySize);

        createMenuBar();
        createToolbar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.setActionCommand("Close");

        newMenuItem.addActionListener(l -> System.out.println("New Clicked"));
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

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void createToolbar() {
        JToolBar toolBar = new JToolBar();
        JToggleButton selectBtn = new JToggleButton("Select");
        JToggleButton paintBtn = new JToggleButton("Paint");
        JComboBox shapesBox = new JComboBox(new String[] {"Line", "Rectangle", "Circle", "Free"});
        JButton drawFromTextField = new JButton("Draw from text field");


        selectBtn.addActionListener( l -> {
            selectBtn.setEnabled(false);
            paintBtn.setSelected(false);
            paintBtn.setEnabled(true);
        });

        paintBtn.addActionListener(l -> {
            selectBtn.setEnabled(true);
            selectBtn.setSelected(false);
            paintBtn.setEnabled(false);
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

        selectBtn.setSelected(true);

        toolBar.add(selectBtn);
        toolBar.add(paintBtn);
        toolBar.add(shapesBox);
        toolBar.add(drawFromTextField);

        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);
    }

    private void shapesBoxState(JComboBox shapesBox) {
        switch (shapesBox.getSelectedIndex()) {
            case 0 -> mainPanel.changeState(new DrawLineState(mainPanel, this));
            case 1 -> mainPanel.changeState(new DrawRectangleState(mainPanel, this));
            case 2 -> mainPanel.changeState(new DrawCircleState(mainPanel, this));
            case 3 -> mainPanel.changeState(new DrawFreeState(mainPanel, this));
            default -> {
            }
        }
    }

}
