package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class SpielFrame extends JFrame {
    public SpielFrame(JPanel panel) {
        super("Spielmodus");
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }
}
