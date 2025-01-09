package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;

public class QuizPanel extends JPanel {
    private JButton nextButton;
    private JButton exitButton;
    private JTextField answerField;
    private JLabel questionLabel;
    private JLabel counterLabel;
    private int currentQuestion = 1;
    private int totalQuestions = 20;

    public QuizPanel(ActionListener listener) throws IOException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBackground(new Color(40, 44, 52));


        counterLabel = new JLabel(currentQuestion + "/" + totalQuestions);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(counterLabel, gbc);
        counterLabel.setForeground(Color.WHITE);

        Image image = null;
        URL url = new URL("https://www.oefb.at/bewerbe/oefb2/person/images/1278650591628556536_147e770e156255184682-1,0-600x315-600x315.png");
        image = ImageIO.read(url);
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        questionLabel = new JLabel("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(imageLabel, gbc);


        answerField = new JTextField(30);
        answerField.setFont(new Font("Arial", Font.PLAIN, 20));
        answerField.setPreferredSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 30, 10);
        add(answerField, gbc);


        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();


        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setPreferredSize(new Dimension(180, 50));
        nextButton.setBackground(new Color(40, 44, 52)); // Cornflower Blue
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Steel Blue border
        nextButton.setActionCommand("next");
        nextButton.addActionListener(listener);
        nextButton.addActionListener(e -> incrementCounter());
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.weightx = 0.5;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(nextButton, buttonGbc);



        exitButton = new JButton("Abbrechen");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setPreferredSize(new Dimension(180, 50));
        exitButton.setBackground(new Color(40, 44, 52)); // Crimson
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(178, 34, 34), 2)); // Fire Brick border
        exitButton.setActionCommand("quiz_abbrechen");
        exitButton.addActionListener(listener);
        buttonGbc.gridx = 1;
        buttonGbc.weightx = 0.5;
        buttonGbc.insets = new Insets(0, 15, 0, 0);  // Space between buttons
        buttonPanel.add(exitButton, buttonGbc);


        buttonPanel.setBackground(new Color(40, 44, 52));


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(buttonPanel, gbc);
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
}
