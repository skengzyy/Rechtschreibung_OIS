package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import rechtschreibtrainer.model.Frage;
import rechtschreibtrainer.model.WortsalatFrage;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.*;

/**
 * SpielPanel für Spielmodus
 * @author Samo Kitzer
 */
public class SpielPanel extends JPanel {

    private JLabel counterLabel;
    private int currentCount = 1;
    private int totalQuestions = 20;
    private JLabel frageLabel;
    private JPanel buchstabenPanel;
    private JButton abbrechenButton, nextButton, resetButton, startMusicButton, stopMusicButton;
    private JLabel statusLabel;
    private char[] buchstabenSalat;
    private String richtigeAntwort;
    private String aktuelleAntwort;
    private JWindow statistik;



    public SpielPanel(TrainerController controller, Frage ersteFrage) {
        this.buchstabenSalat = ersteFrage.getAntwort().toCharArray();
        this.buchstabenSalat = mischeArray(this.buchstabenSalat);
        this.richtigeAntwort = ersteFrage.getAntwort();
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

    private char[] mischeArray(char[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
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
    public void updateCounter() {
        if (currentCount < totalQuestions) {
            currentCount++;
            counterLabel.setText(currentCount + "/" + totalQuestions);
        }
    }
    public void resetSpielPanel(Frage neuerBuchstabenSalat, TrainerController tc){
        counterLabel.setText(currentCount + "/" + totalQuestions);

        buchstabenSalat = neuerBuchstabenSalat.getAntwort().toCharArray();
        buchstabenSalat = mischeArray(this.buchstabenSalat);
        richtigeAntwort = neuerBuchstabenSalat.getAntwort();
        aktuelleAntwort = "";



        buchstabenPanel.removeAll();
        for (char buchstabe : buchstabenSalat) {
            JButton button = createBuchstabenButton(buchstabe, tc);
            buchstabenPanel.add(button);
        }
        buchstabenPanel.revalidate();
        buchstabenPanel.repaint();

        statusLabel.setText("");
    }

    public void addBuchstabeToAntwort(char buchstabe) {
        aktuelleAntwort += buchstabe; // Buchstabe zur aktuellen Antwort hinzufügen
    }

    public String getAktuelleAntwort() {
        return aktuelleAntwort;
    }
    public String getRichtigeAntwort(){
        return richtigeAntwort;
    }

    public int updateFrageIndex(int index, int maxQuestions) {
        if(index + 1 <= maxQuestions -1) {
            return index + 1;
        }
        return -1;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        this.counterLabel.setText(currentCount + "/" + totalQuestions);
    }
    public void setCurrentQuestion(int currentQuestion) {
        this.currentCount = currentQuestion;
        this.counterLabel.setText(currentCount + "/" + totalQuestions);
    }
    public void enableNext(boolean enable){
        this.nextButton.setEnabled(enable);
    }

    public JWindow getStatistik() {
        return statistik;
    }

    public void showStatistikDialog(JFrame parent, int gesamt, int richtig, int falsch, ActionListener controller) {
        statistik = new JWindow(parent);
        statistik.setSize(400, 400);
        statistik.setLayout(new BorderLayout());


        double richtigProzent = (gesamt > 0) ? ((double) richtig / gesamt) * 100 : 0;
        double falschProzent = 100 - richtigProzent;

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int size = Math.min(getWidth(), getHeight()) - 20;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                int winkelRichtig = (int) (richtigProzent * 3.6);

                // Zeichne den grünen Bereich (Richtig)
                g2d.setColor(Color.GREEN);
                g2d.fillArc(x, y, size, size, 0, winkelRichtig);

                // Zeichne den roten Bereich (Falsch)
                g2d.setColor(Color.RED);
                g2d.fillArc(x, y, size, size, winkelRichtig, 360 - winkelRichtig);

                // Berechnung der Mittelpunkte für Text
                int xRichtigText = x + size / 2 + (int) (Math.cos(Math.toRadians(winkelRichtig / 2)) * (size / 4));
                int yRichtigText = y + size / 2 - (int) (Math.sin(Math.toRadians(winkelRichtig / 2)) * (size / 4));

                int xFalschText = x + size / 2 + (int) (Math.cos(Math.toRadians(winkelRichtig + (360 - winkelRichtig) / 2)) * (size / 4));
                int yFalschText = y + size / 2 - (int) (Math.sin(Math.toRadians(winkelRichtig + (360 - winkelRichtig) / 2)) * (size / 4));

                // Text für Richtig und Falsch
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.format("%.1f%% Richtig", richtigProzent), xRichtigText, yRichtigText);
                g2d.drawString(String.format("%.1f%% Falsch", falschProzent), xFalschText, yFalschText);
            }
        };
        chartPanel.setPreferredSize(new Dimension(200, 200));

        JLabel infoLabel = new JLabel("Fragen: " + gesamt + " | Richtig: " + richtig + " | Falsch: " + falsch);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton wiederholenButton = new JButton("Wiederholen");
        wiederholenButton.setActionCommand("retry_statistik_spiel");
        wiederholenButton.addActionListener(controller);


        JButton speichernButton = new JButton("Zu Menü");
        speichernButton.setActionCommand("menü_statistik_spiel");
        speichernButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(wiederholenButton);
        buttonPanel.add(speichernButton);

        statistik.add(chartPanel, BorderLayout.CENTER);
        statistik.add(infoLabel, BorderLayout.NORTH);
        statistik.add(buttonPanel, BorderLayout.SOUTH);

        statistik.setLocationRelativeTo(parent);
        statistik.setVisible(true);
    }



}
