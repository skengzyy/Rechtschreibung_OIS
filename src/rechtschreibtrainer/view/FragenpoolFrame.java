package rechtschreibtrainer.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FragenpoolFrame extends JFrame {
    public FragenpoolFrame(JPanel panel) {
        super("Fragenpool verwalten");
        this.add(panel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(1000, 550);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }

}
