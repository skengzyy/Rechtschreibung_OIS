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
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        bottomPanel.setBackground(new Color(40, 44, 52));

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        bottomPanel.add(statusLabel);

        abbrechenButton = new JButton("Abbrechen");
        abbrechenButton.setFont(new Font("Arial", Font.BOLD, 16));
        abbrechenButton.setBackground(Color.RED);
        abbrechenButton.setForeground(Color.WHITE);
        abbrechenButton.setOpaque(true);
        abbrechenButton.setBorderPainted(false);
        abbrechenButton.setFocusPainted(false);
        abbrechenButton.setActionCommand("spiel_abbrechen");
        abbrechenButton.addActionListener(controller);
        bottomPanel.add(abbrechenButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(Color.GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setActionCommand("resetSpielPanel");
        resetButton.addActionListener(controller);
        bottomPanel.add(resetButton);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(new Color(40, 44, 52));
        nextButton.setOpaque(true);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.setActionCommand("spiel_next");
        nextButton.addActionListener(controller);
        bottomPanel.add(nextButton);

        gbc.gridy = 2;
        gbc.weighty = 0.2;
        add(bottomPanel, gbc);
    }

    private JButton createBuchstabenButton(char buchstabe, TrainerController controller) {
        JButton button = new JButton(String.valueOf(buchstabe));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(59, 89, 152));
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setActionCommand("buchstabe_" + buchstabe);
        button.addActionListener(controller);
        return button;
    }

    public void updateStatus(boolean korrekt, String aktuelleEingabe) {
        if (korrekt) {
            statusLabel.setText("Richtig! Das richtige Wort: " + aktuelleEingabe);
            statusLabel.setForeground(new Color(46, 204, 113));
        } else {
            statusLabel.setText("Falsch! Versuche es erneut.");
            statusLabel.setForeground(new Color(231, 76, 60));
        }
    }

    public void highlightButton(JButton button, boolean korrekt) {
        if (korrekt) {
            button.setBackground(new Color(46, 204, 113));
            button.setOpaque(true);
        } else {
            button.setBackground(new Color(231, 76, 60));
            button.setOpaque(true);
        }
    }


