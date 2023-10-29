package components;

import model.ShapesList;
import state.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.*;


public class Frame extends JFrame {

    private MainPanel mainPanel;
    private JLabel colorInfo;
    private JPanel statusPanel;
    public Frame(String title, int xSize, int ySize) {
        super(title);
        mainPanel = new MainPanel(this);
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.white));


        Container contentPane = getContentPane();
        contentPane.add(mainPanel);

        setSize(xSize, ySize);

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

    private void createToolbar() {
        JToolBar toolBar = new JToolBar();
        JToggleButton selectBtn = new JToggleButton("Move");
        JToggleButton paintBtn = new JToggleButton("Paint");

        selectBtn.addActionListener( l -> {
            selectBtn.setEnabled(false);
            paintBtn.setSelected(false);
            paintBtn.setEnabled(true);
            mainPanel.changeState(new MoveState(mainPanel, this));
        });

        paintBtn.addActionListener(l -> {
            selectBtn.setEnabled(true);
            selectBtn.setSelected(false);
            paintBtn.setEnabled(false);
            mainPanel.changeState(new DrawState(mainPanel, this));
        });

        toolBar.add(selectBtn);
        toolBar.add(paintBtn);
        JButton add = new JButton("Add");
        add.addActionListener(l -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField xTxt = new JTextField(3);
            JTextField yTxt = new JTextField(3);
            panel.add(new JLabel("x:"));
            panel.add(xTxt);
            panel.add(new JLabel("y:"));
            panel.add(yTxt);
            int result = JOptionPane.showConfirmDialog(this, panel, "Add", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                mainPanel.getState().addPoint(Integer.parseInt(xTxt.getText()), Integer.parseInt(yTxt.getText()));
            }
        });
        toolBar.add(add);
        JButton move = new JButton("move from txt");
        move.addActionListener(l -> {
            JPanel panel = new JPanel();
            panel.repaint();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField xTxt = new JTextField(3);
            JTextField yTxt = new JTextField(3);
            panel.add(new JLabel("x:"));
            panel.add(xTxt);
            panel.add(new JLabel("y:"));
            panel.add(yTxt);
            int result = JOptionPane.showConfirmDialog(this, panel, "Add", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                mainPanel.getState().move(Integer.parseInt(xTxt.getText()), Integer.parseInt(yTxt.getText()));
            }
        });
        toolBar.add(move);
        JButton rotate = new JButton("rotate");
        rotate.addActionListener(l -> {
            mainPanel.changeState(new RotateState(mainPanel, this));
        });
        toolBar.add(rotate);
        JButton rotateTxt = new JButton("rotate txt");
        rotateTxt.addActionListener(l -> {
            JPanel panel = new JPanel();
            panel.repaint();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField xTxt = new JTextField(3);
            JTextField yTxt = new JTextField(3);
            panel.add(new JLabel("x:"));
            panel.add(xTxt);
            panel.add(new JLabel("y:"));
            panel.add(yTxt);
            JTextField angle = new JTextField(3);
            panel.add(new JLabel("angle:"));
            panel.add(angle);
            int result = JOptionPane.showConfirmDialog(this, panel, "Add", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                mainPanel.getState().rotateFromTxt(Integer.parseInt(xTxt.getText()), Integer.parseInt(yTxt.getText()), Integer.parseInt(angle.getText()));
            }
        });
        toolBar.add(rotateTxt);
        JButton scale = new JButton("scale");
        scale.addActionListener(l -> {
            mainPanel.changeState(new ScaleState(mainPanel, this));
        });
        toolBar.add(scale);

        JButton saveMenuItem = new JButton("save");

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
        JButton openMenuItem = new JButton("load");
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
        toolBar.add(saveMenuItem);
        toolBar.add(openMenuItem);
        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);
    }

}
