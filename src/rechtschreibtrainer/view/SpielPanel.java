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

    public SpielPanel(TrainerController controller, char[] buchstabenSalat, String richtigeAntwort) {
        this.buchstabenSalat = buchstabenSalat;
        this.richtigeAntwort = richtigeAntwort;
        this.aktuelleAntwort = "";

        setLayout(new GridBagLayout());
        setBackground(new Color(40, 44, 52));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 1.0;


        counterLabel = new JLabel(currentCount + "/" + totalQuestions);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(counterLabel, gbc);
        counterLabel.setForeground(Color.WHITE);


        frageLabel = new JLabel("Wähle die Buchstaben in der richtigen Reihenfolge aus:", JLabel.CENTER);
        frageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frageLabel.setForeground(Color.WHITE);
        frageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        add(frageLabel, gbc);


        buchstabenPanel = new JPanel();
        buchstabenPanel.setLayout(new GridLayout(2, buchstabenSalat.length / 2 + 1, 10, 10));
        buchstabenPanel.setBackground(new Color(230, 230, 230));
        buchstabenPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        add(buchstabenPanel, gbc);

        for (char buchstabe : buchstabenSalat) {
            JButton button = createBuchstabenButton(buchstabe, controller);
            buchstabenPanel.add(button);
        }




