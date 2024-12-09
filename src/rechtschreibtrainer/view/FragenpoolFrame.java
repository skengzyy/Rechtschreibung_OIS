package rechtschreibtrainer.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FragenpoolFrame extends JFrame implements WindowListener {
    public FragenpoolFrame(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(800, 500);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(false);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
