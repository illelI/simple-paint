import components.Frame;

import javax.swing.*;

public class JPaint {
    public static void main(String[] args)
    {
        String title = "JPaint";
        int xSize = 600;
        int ySize = 600;
        Frame frame = new Frame(title, xSize, ySize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
