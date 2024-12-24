package rechtschreibtrainer.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuizPanel extends JPanel {
    private JButton nextButton;
    private JButton exitButton;
    private JTextField answerField;
    private JLabel questionLabel;
    private JLabel counterLabel;
    private int currentQuestion = 1;
    private final int totalQuestions = 10;

    public QuizPanel(ActionListener listener) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        counterLabel = new JLabel(currentQuestion + "/" + totalQuestions);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(counterLabel, gbc);


        questionLabel = new JLabel("Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(questionLabel, gbc);


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
        nextButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
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


        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setPreferredSize(new Dimension(180, 50));
        exitButton.setBackground(new Color(220, 20, 60)); // Crimson
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(178, 34, 34), 2)); // Fire Brick border
        exitButton.setActionCommand("exitQuizPage");
        exitButton.addActionListener(listener);
        exitButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(exitButton);
            topFrame.setVisible(false);
        });
        buttonGbc.gridx = 1;
        buttonGbc.weightx = 0.5;
        buttonGbc.insets = new Insets(0, 15, 0, 0);  // Space between buttons
        buttonPanel.add(exitButton, buttonGbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(buttonPanel, gbc);
    }

    public void setCounterText(String text) {
        counterLabel.setText(text);
    }

    public void incrementCounter() {
        if (currentQuestion < totalQuestions) {
            currentQuestion++;
            counterLabel.setText(currentQuestion + "/" + totalQuestions);
        }
    }
}
