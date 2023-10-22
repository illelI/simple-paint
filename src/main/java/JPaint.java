import components.Frame;

import javax.swing.*;

public class JPaint {
    public static void main(String[] args)
    {
        String title = "JPaint";
        int xSize = 800;
        int ySize = 600;
        Frame frame = new Frame(title, xSize, ySize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
