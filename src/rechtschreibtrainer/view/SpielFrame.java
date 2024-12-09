package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class SpielFrame extends JFrame {
    public SpielFrame(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(3);
        this.setSize(400, 250);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }
}
