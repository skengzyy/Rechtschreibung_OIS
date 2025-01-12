package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import rechtschreibtrainer.model.Frage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SpielPanel extends JPanel {

    private JLabel counterLabel;
    private int currentCount = 1;
    private int totalQuestions = 20;
    private JLabel frageLabel;
    private JPanel buchstabenPanel;
    private JButton abbrechenButton, nextButton, resetButton;
    private JLabel statusLabel;
    private char[] buchstabenSalat;
    private String richtigeAntwort;
    private String aktuelleAntwort;
}



