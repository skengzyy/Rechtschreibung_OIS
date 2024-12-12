package rechtschreibtrainer.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FragenpoolFrame extends JFrame {
    public FragenpoolFrame(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }

}
