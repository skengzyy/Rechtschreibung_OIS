package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 * QuizPanel für Quizmodus
 * @author Isaac Jerryson
 */

public class QuizPanel extends JPanel {
    private JButton nextButton,soundButton,infoButton;
    private JButton exitButton;
    private JTextField answerField;
    private JLabel questionLabel, imageLabel;
    private JLabel counterLabel;
    private JPanel fragen;
    private JWindow statistik;

    private Clip musicClip;
    private boolean isMusicPlaying = false;
    private int currentQuestion = 1;
    private int totalQuestions = 10;

    public QuizPanel(ActionListener listener) throws IOException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBackground(new Color(40, 44, 52));

// Sound Button oben links
        soundButton = new JButton(resizeIcon("/pngs/sound_on.png"));
        soundButton.setPreferredSize(new Dimension(50, 50));
        soundButton.addActionListener(e -> toggleMusic());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);  // Abstand zum Rand
        gbc.anchor = GridBagConstraints.NORTHWEST;  // Oben links ausrichten
        add(soundButton, gbc);

// Info Button oben rechts
        infoButton = new JButton(resizeIcon("/pngs/info.png"));
        infoButton.setActionCommand("info_quizmode");
        infoButton.addActionListener(e -> showInfoDialog());
        infoButton.setPreferredSize(new Dimension(50, 50));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);  // Abstand zum Rand
        gbc.anchor = GridBagConstraints.NORTHEAST;  // Oben rechts ausrichten
        add(infoButton, gbc);

// Counter Label
        counterLabel = new JLabel(currentQuestion + "/" + totalQuestions);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        counterLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);  // Abstand
        gbc.anchor = GridBagConstraints.CENTER;  // Zentriert
        add(counterLabel, gbc);

// Frage Label
        fragen = new JPanel();
        fragen.setLayout(new BorderLayout());
        fragen.setBackground(new Color(40, 44, 52));
        questionLabel = new JLabel("Elon + Trump");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setForeground(Color.WHITE);
        fragen.add(questionLabel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);  // Abstand
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Horizontal füllen
        add(fragen, gbc);

// Textfeld für Antwort
        answerField = new JTextField(30);
        answerField.setFont(new Font("Arial", Font.PLAIN, 20));
        answerField.setPreferredSize(new Dimension(400, 40));
        answerField.setActionCommand("quiz_antwort");
        answerField.addActionListener(listener);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);  // Abstand
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Horizontal füllen
        add(answerField, gbc);

// Button Panel für "Next" und "Abbrechen"
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
// Abbrechen Button
        exitButton = new JButton("Abbrechen");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setPreferredSize(new Dimension(180, 50));
        exitButton.setBackground(new Color(40, 44, 52));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(178, 34, 34), 2));
        exitButton.setActionCommand("quiz_abbrechen");
        exitButton.addActionListener(listener);
        buttonGbc.gridx = 1;
        buttonGbc.weightx = 0.5;
        buttonGbc.insets = new Insets(0, 15, 0, 0);  // Abstand
        buttonPanel.add(exitButton, buttonGbc);
        buttonPanel.setBackground(new Color(40, 44, 52));

// Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setPreferredSize(new Dimension(180, 50));
        nextButton.setBackground(new Color(40, 44, 52));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        nextButton.setActionCommand("next_quizmode");
        nextButton.setEnabled(false);
        nextButton.addActionListener(listener);
        nextButton.addActionListener(e -> incrementCounter());
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.weightx = 0.5;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;  // Horizontal füllen
        buttonPanel.add(nextButton, buttonGbc);



// Buttons unter der Frage und dem Textfeld
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);
    }
    public void updatePanel(String frage) {
        fragen.removeAll();
        fragen.revalidate();
        fragen.repaint();
        questionLabel.setText(frage);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        questionLabel.setForeground(Color.WHITE);
        fragen.add(questionLabel, BorderLayout.CENTER);

    }
    public void updatePanel(URL bild) throws IOException {
        // Lade das Bild
        Image image = ImageIO.read(bild);

        // Erstelle ein JLabel mit dem Bild
        imageLabel = new JLabel(new ImageIcon(image));

        // Überschreibe die paintComponent-Methode, um das Bild zu skalieren
        fragen.removeAll();
        fragen.revalidate();
        fragen.repaint();

        // Setze das Layout des Panels
        fragen.setLayout(new BorderLayout());
        fragen.add(new JLabel(new ImageIcon(image.getScaledInstance(600, 300, Image.SCALE_SMOOTH))), BorderLayout.CENTER);
    }

    public void enableNext(boolean enable) {
        nextButton.setEnabled(enable);
    }
    public void enableAntwort(boolean enable) {
        answerField.setEnabled(enable);
    }
    public void emptyAnswerField() {
        answerField.setText("");
    }
    public String getAntwortText() {
        return answerField.getText();
    }
    public void setCounterText(String text) {
        counterLabel.setText(text);
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void incrementCounter() {
        if (currentQuestion < totalQuestions) {
            currentQuestion++;
            counterLabel.setText(currentQuestion + "/" + totalQuestions);
        }
    }
    public void resetCounter() {
        counterLabel.setText(currentQuestion + "/" + totalQuestions);
    }

    private void initializeMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Fehler beim Laden der Musik: " + e.getMessage());
        }
    }

    public void startMusic() {
        initializeMusic("src/audio/kahoot.wav");
        if (musicClip != null && !musicClip.isRunning()) {
            musicClip.setFramePosition(0);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }
    public void toggleMusic() {
        if (isMusicPlaying) {
            stopMusic();
            soundButton.setIcon(resizeIcon("/pngs/sound_off.png")); // Bild ändern
        } else {
            startMusic();
            soundButton.setIcon(resizeIcon("/pngs/sound_on.png")); // Bild ändern
        }
        isMusicPlaying = !isMusicPlaying;
    }
    // Hilfsmethode zum Skalieren der Icons
    private ImageIcon resizeIcon(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void showInfoDialog() {
        JOptionPane.showMessageDialog(this,
                "Im Quizmodus müssen Sie die Fragen nacheinander beantworten \n" +
                        "(String, Boolean, Bild oder Integer).\n\n" +
                        "Es werden aus Ihrem Fragenpool die ersten 10 Fragen gestellt. \n" +
                        "Wenn Sie alle richtig beantwortet haben, können Sie mit den \n" +
                        "nächsten 10 weitermachen (20 insgesamt) usw., \n" +
                        "bis alle 30 Fragen richtig beantwortet wurden.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void showAntwortDialog(JFrame parent, boolean richtig) {
        JWindow antwort = new JWindow(parent);
        antwort.setSize(300, 200);
        antwort.setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon(richtig ? "src/pngs/richtig.png" : "src/pngs/falsch.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);

        JLabel textLabel = new JLabel(richtig ? "RICHTIG" : "FALSCH", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> antwort.dispose());

        antwort.add(iconLabel, BorderLayout.NORTH);
        antwort.add(textLabel, BorderLayout.CENTER);
        antwort.add(okButton, BorderLayout.SOUTH);

        antwort.setLocationRelativeTo(parent);
        antwort.setVisible(true);
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
        wiederholenButton.setActionCommand("retry_Statistik");
        wiederholenButton.addActionListener(controller);

        JButton weitermachenButton = new JButton("Weitermachen");
        weitermachenButton.setActionCommand("continue_Statistik");
        weitermachenButton.addActionListener(controller);

        JButton speichernButton = new JButton("Zu Menü");
        speichernButton.setActionCommand("menü_Statistik");
        speichernButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(wiederholenButton);
        buttonPanel.add(weitermachenButton);
        buttonPanel.add(speichernButton);

        statistik.add(chartPanel, BorderLayout.CENTER);
        statistik.add(infoLabel, BorderLayout.NORTH);
        statistik.add(buttonPanel, BorderLayout.SOUTH);

        statistik.setLocationRelativeTo(parent);
        statistik.setVisible(true);
    }
    public void showKroneDialog(JFrame parent, ActionListener controller) {
        JDialog kroneDialog = new JDialog(parent, "Rechtschreib-König", true);
        kroneDialog.setSize(400, 500);
        kroneDialog.setUndecorated(true);
        kroneDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel() {
            private final Image krone = new ImageIcon("out/production/Rechtschreibung_OIS/pngs/krone.png").getImage();
            private int yPosition = 50;
            private boolean nachOben = true;

            {
                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (nachOben) {
                            yPosition -= 2;
                            if (yPosition < 40) {
                                nachOben = false;
                            }
                        } else {
                            yPosition += 2;
                            if (yPosition > 50) {
                                nachOben = true;
                            }
                        }
                        repaint();
                    }
                });
                timer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int bildBreite = (int) (getWidth() * 0.7);
                int bildHöhe = (int) (getHeight() * 0.7);
                int xBild = (getWidth() - bildBreite) / 2;
                int yBild = yPosition;

                g.drawImage(krone, xBild, yBild, bildBreite, bildHöhe, this);

                // Text
                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.setColor(Color.BLACK);
                FontMetrics metrics = g.getFontMetrics();

                String zeile1 = "DU BIST OFFIZIELL EIN";
                String zeile2 = "RECHTSCHREIBKÖNIG";

                int textX1 = (getWidth() - metrics.stringWidth(zeile1)) / 2;
                int textX2 = (getWidth() - metrics.stringWidth(zeile2) - 90) / 2;

                int textY1 = yBild + bildHöhe + 40;
                int textY2 = yBild + bildHöhe + 80;

                g.drawString(zeile1, textX1, textY1);

                g.setFont(new Font("Arial", Font.BOLD, 28));
                g.drawString(zeile2, textX2, textY2);
            }
        };

        panel.setPreferredSize(new Dimension(400, 500));

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        okButton.setActionCommand("ok_könig_dialog");
        okButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        kroneDialog.add(panel);
        kroneDialog.setVisible(true);
    }

    public int updateFrageIndex(int index, int maxQuestions) {
        if(index + 1 < maxQuestions) {
            return index + 1;
        }
        return -1;
    }
    public void showErrorStatisticsMessage(String type) {
        switch (type) {
            case "continue" -> JOptionPane.showMessageDialog(null, "Sie haben den ersten Durchgang leider nicht bestanden. Bitte drúcke auf Wiederholen", "Weitermachen", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void showSuccessStatisticsMessage(String type) {
        switch (type) {
            case "continue" -> JOptionPane.showMessageDialog(null, "Sie haben den  Durchgang bestanden! Jetzt beginnt der nächste Durchgang. Viel Glück!", "Weitermachen", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JWindow getStatistik() {
        return statistik;
    }
}
