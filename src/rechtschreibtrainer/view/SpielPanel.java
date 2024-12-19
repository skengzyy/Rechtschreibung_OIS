package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import java.awt.*;
import javax.swing.*;
public class SpielPanel extends JPanel {
    private TrainerController tc;
    public SpielPanel(TrainerController tc) {
        this.tc = tc;
    }
}
