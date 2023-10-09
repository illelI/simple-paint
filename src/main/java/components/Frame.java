package components;

import model.ShapesList;
import state.*;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
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
        JComboBox shapesBox = new JComboBox(new String[] {"Line", "Rectangle", "Circle", "Free", "Free (points)"});
        JButton drawFromTextField = new JButton("Draw from text field");
        JButton changeShape = new JButton("Change");


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
                    case Ellipse2D circle -> circleDialog(circle);
                    case Rectangle2D rect -> rectangleDialog(rect);
                    case Line2D line -> lineDialog(line);
                    case null -> JOptionPane.showMessageDialog(this, new JLabel("Select shape"));
                    default -> JOptionPane.showMessageDialog(this, new JLabel("unsupported shape"));
                }
        });

        selectBtn.setSelected(true);

        toolBar.add(selectBtn);
        toolBar.add(paintBtn);
        toolBar.add(shapesBox);
        toolBar.add(drawFromTextField);
        toolBar.add(changeShape);

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

    private void circleDialog(Ellipse2D circle) {

        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField centerX = new JTextField(5);
        JTextField centerY = new JTextField(5);
        JTextField radiusInput = new JTextField(5);

        Shape currentShape;

        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapes().getShapes()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        dialog.setLayout(new BoxLayout(dialog, 3));

        dialog.add(new JLabel("X coordinate of circle center:"));
        dialog.add(centerX);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Y coordinate of circle center:"));
        dialog.add(centerY);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Circle radius:"));
        dialog.add(radiusInput);

        centerX.setText(String.valueOf(circle.getCenterX()));
        centerY.setText(String.valueOf(circle.getCenterY()));
        radiusInput.setText(String.valueOf(circle.getHeight()/2));

        int result = JOptionPane.showConfirmDialog(this, dialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            this.mainPanel.getState().getCanvas().getShapes().getShapes().remove(circle);
            currentShape = new Ellipse2D.Double(Double.parseDouble(centerX.getText()) - Double.parseDouble(radiusInput.getText()),
                                                Double.parseDouble(centerY.getText()) - Double.parseDouble(radiusInput.getText()),
                                                Double.parseDouble(radiusInput.getText()) * 2, Double.parseDouble(radiusInput.getText()) * 2);

            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            this.mainPanel.getState().getCanvas().getShapes().getShapes().add(currentShape);
        }


    }

    private void lineDialog(Line2D line) {
        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);

        Shape currentShape;

        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapes().getShapes()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        dialog.setLayout(new BoxLayout(dialog, 1));

        dialog.add(new JLabel("x1:"));
        dialog.add(x1Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("y1:"));
        dialog.add(y1Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("x2:"));
        dialog.add(x2Field);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("y2:"));
        dialog.add(y2Field);

        x1Field.setText(String.valueOf(line.getX1()));
        y1Field.setText(String.valueOf(line.getY1()));
        x2Field.setText(String.valueOf(line.getX2()));
        y2Field.setText(String.valueOf(line.getX2()));

        int result = JOptionPane.showConfirmDialog(this, dialog, "Change values:", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION) {
            this.mainPanel.getState().getCanvas().getShapes().getShapes().remove(line);
            currentShape = new Line2D.Double(Double.parseDouble(x1Field.getText()), Double.parseDouble(y1Field.getText()),
                    Double.parseDouble(x2Field.getText()), Double.parseDouble(y2Field.getText()));
            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            this.mainPanel.getState().getCanvas().getShapes().getShapes().add(currentShape);
        }

    }

    private void rectangleDialog(Rectangle2D rect) {
        JPanel dialog = new JPanel();
        dialog.repaint();
        JTextField upperLeftX = new JTextField(5);
        JTextField upperLeftY = new JTextField(5);
        JTextField width = new JTextField(5);
        JTextField height = new JTextField(5);

        Shape currentShape;

        dialog.setLayout(new BoxLayout(dialog, 3));

        dialog.add(new JLabel("X coordinate of upper left corner:"));
        dialog.add(upperLeftX);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Y coordinate of upper left corner:"));
        dialog.add(upperLeftY);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Width:"));
        dialog.add(width);
        dialog.add(Box.createVerticalStrut(15));
        dialog.add(new JLabel("Height:"));
        dialog.add(height);

        upperLeftX.setText(String.valueOf(rect.getX()));
        upperLeftY.setText(String.valueOf(rect.getY()));
        width.setText(String.valueOf(rect.getWidth()));
        height.setText(String.valueOf(rect.getHeight()));


        Runnable draw = () -> {
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            for (Shape shape : mainPanel.getState().getCanvas().getShapes().getShapes()) {
                if(shape == null) continue;
                g2d.draw(shape);
            }
        };

        for (Shape shape : mainPanel.getState().getCanvas().getShapes().getShapes()) {
            System.out.println(shape);
        }

        int result = JOptionPane.showConfirmDialog(this, dialog, "Enter values:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            this.mainPanel.getState().getCanvas().getShapes().getShapes().remove(rect);

            currentShape = new Rectangle2D.Double(Double.parseDouble(upperLeftX.getText()), Double.parseDouble(upperLeftY.getText()),
                    Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));

            mainPanel.repaint();
            SwingUtilities.invokeLater(draw);
            Graphics2D g2d = (Graphics2D) mainPanel.getGraphics();
            g2d.draw(currentShape);
            this.mainPanel.getState().getCanvas().getShapes().getShapes().add(currentShape);
        }

    }

}
