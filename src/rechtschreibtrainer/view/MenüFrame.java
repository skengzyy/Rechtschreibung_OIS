package rechtschreibtrainer.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class MenüFrame extends JFrame  {
    public MenüFrame(JPanel panel) {
        super("SpellWeaver");
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
