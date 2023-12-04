package components;

import javax.swing.*;

public class Dialogs {

    private Dialogs() {}

    public static void resultDialog(Frame frame) {
        JPanel dialog = new JPanel();
        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));

        String greenPercent = String.format("%.2f", frame.getMainPanel().getGreen()) + "% of all pixels are green pixels";

        JLabel greenLabel = new JLabel(greenPercent);
        dialog.add(greenLabel);

        JOptionPane.showMessageDialog(frame, dialog);
    }
}
