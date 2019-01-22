import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {

    public static void main(String argv[]) {
        SwingUtilities.invokeLater(() -> {
            ConvertingApp frame = new ConvertingApp();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setTitle("App");
            frame.setSize(600,900);
            frame.setLocation(150, 150);
            frame.setVisible(true);
        });

    }

}