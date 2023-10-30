package components;

import javax.swing.*;
import java.util.ArrayList;

public class Dialogs {

    private Dialogs() {}

    public static void setStructuringElement(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        JLabel howBigLabel = new JLabel("How big?");
        JTextField howBig = new JTextField();

        dialog.add(howBigLabel);
        dialog.add(howBig);

        int result = JOptionPane.showConfirmDialog(frame, dialog, "", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            setElements(frame, Integer.parseInt(howBig.getText()));
        }
    }

    private static void setElements(Frame frame, int howBig) {
        int[][] elements = new int[howBig][howBig];
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JTextField> textFields = new ArrayList<>();
        for (int i = 0; i < howBig * howBig; i++) {
            if (i % howBig == 0) {
                panel.add(Box.createVerticalStrut(10));
            }
            textFields.add(new JTextField(1));
            panel.add(textFields.get(i));
        }
        int result = JOptionPane.showConfirmDialog(frame, panel, "", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int j = 0;
            for (int i = 0; i < howBig * howBig; i++) {
                if (i % howBig == 0 && i != 0) {
                    j++;
                }
                elements[i - 3 * j][j] = Integer.parseInt(textFields.get(i).getText());
            }
            frame.getMainPanel().setStructuringElement(elements);
        }
    }
}
