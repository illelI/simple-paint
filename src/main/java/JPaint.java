import components.Frame;

import javax.swing.*;

public class JPaint {
    public static void main(String[] args)
    {
        String title = "JPaint";
        int xSize = 400;
        int ySize = 400;
        Frame frame = new Frame(title, xSize, ySize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
