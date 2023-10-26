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
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
        JToggleButton selectBtn = new JToggleButton("Select");
        JToggleButton paintBtn = new JToggleButton("Paint");

        selectBtn.addActionListener( l -> {
            selectBtn.setEnabled(false);
            paintBtn.setSelected(false);
            paintBtn.setEnabled(true);
            mainPanel.changeState(new SelectState(mainPanel, this));
        });

        paintBtn.addActionListener(l -> {
            selectBtn.setEnabled(true);
            selectBtn.setSelected(false);
            paintBtn.setEnabled(false);
            mainPanel.changeState(new DrawBezierState(mainPanel, this));
        });

        toolBar.add(selectBtn);
        toolBar.add(paintBtn);
        JTextField pointCount = new JTextField();
        toolBar.add(new JLabel("How many points: "));
        toolBar.add(pointCount);
        JButton ok = new JButton("OK");
        ok.addActionListener(l -> (mainPanel.getState()).setHowMany(Integer.parseInt(pointCount.getText())));
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
        toolBar.add(ok);
        toolBar.add(add);
        JButton change = new JButton("change");
        change.addActionListener(l -> {
            JPanel panel = new JPanel();
            panel.repaint();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            List<JTextField> textFieldList = new ArrayList<>();
            List<CanvasState.BezierPoint> bps = mainPanel.getState().getControlPoints();
            for (int i = 0; i < mainPanel.getState().getHowMany(); i++) {
                textFieldList.add(new JTextField(3));
                textFieldList.add(new JTextField(3));
                textFieldList.get(i * 2).setText(String.valueOf((int)bps.get(i).getX()));
                textFieldList.get(i * 2 + 1).setText(String.valueOf((int)bps.get(i).getY()));
                panel.add(new JLabel("x" + i + ":"));
                panel.add(textFieldList.get(i * 2));
                panel.add(new JLabel("y" + i + ":"));
                panel.add(textFieldList.get(i * 2 + 1));

            }
            int result = JOptionPane.showConfirmDialog(this, panel, "Add", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                List<Integer> values = new ArrayList<>();
                for (JTextField txt : textFieldList) {
                    values.add(Integer.parseInt(txt.getText()));
                }
                mainPanel.getState().changePoints(values);
            }
        });
        toolBar.add(change);
        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);
    }

}
