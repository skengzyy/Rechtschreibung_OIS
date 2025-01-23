package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class QuizFrame extends JFrame {
    public QuizFrame(JPanel panel) {
        super("Quizmodus");
        this.add(panel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(800, 500);

        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }
}
