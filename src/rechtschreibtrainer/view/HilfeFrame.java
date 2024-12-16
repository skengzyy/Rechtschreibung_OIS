package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;

public class HilfeFrame extends JFrame {
    public HilfeFrame(JPanel panel)  {
        super("Hilfe");
        this.add(panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

}
