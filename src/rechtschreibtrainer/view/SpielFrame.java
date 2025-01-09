package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class SpielFrame extends JFrame {
    public SpielFrame(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }
}