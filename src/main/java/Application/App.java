package Application;

import Frames.*;
import javax.swing.*;

public class App extends JFrame {
    public static MainFrame frame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new MainFrame();
            }
        });
    }
}
