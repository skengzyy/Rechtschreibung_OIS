package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class SpielFrame extends JFrame {
    public SpielFrame(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 550);
        this.setResizable(false);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }
}