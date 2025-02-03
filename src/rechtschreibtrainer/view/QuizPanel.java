package rechtschreibtrainer.view;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class QuizPanel extends JPanel {
    private JButton nextButton,soundButton,infoButton;
    private JButton exitButton;
    private JTextField answerField;
    private JLabel questionLabel, imageLabel;
    private JLabel counterLabel;
    private JPanel fragen;

    private Clip musicClip;
    private boolean isMusicPlaying = false;
    private int currentQuestion = 1;
    private int totalQuestions = 20;

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
        questionLabel = new JLabel("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum");
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
        gbc.insets = new Insets(20, 10, 20, 10);  // Abstand
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Horizontal füllen
        add(buttonPanel, gbc);
    }
    public void updatePanel(String frage) {
        fragen.removeAll();
        fragen.revalidate();
        fragen.repaint();
        questionLabel.setText(frage);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
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
        fragen.add(new JLabel(new ImageIcon(image.getScaledInstance(300, 150, Image.SCALE_SMOOTH))), BorderLayout.CENTER);
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


}
