package rechtschreibtrainer.view;
import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;


public class QuizPanel extends JPanel {
    private TrainerController tc;
    private JButton nextButton, exitButton;
    private JLabel label, query;

    public QuizPanel(TrainerController tc) {
        this.tc = tc;

        // Set the layout
        setLayout(new GridLayout(5, 1, 3, 3));
        setBackground(Color.LIGHT_GRAY);

        // Create the bottom area
        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new GridLayout(2, 2));
        bottomArea.setBackground(Color.GRAY);

        // Create the labels and buttons
        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setHorizontalAlignment(JLabel.CENTER);

        query = new JLabel("Lorem ipsum lorem ipsum lorem ipsum lorem ipsum");
        query.setFont(new Font("Arial", Font.PLAIN, 18));
        query.setHorizontalAlignment(JLabel.CENTER);

        nextButton = new JButton("Next");
        exitButton = new JButton("Exit");

        // Add the components to the panel
        this.add(label);
        this.add(query);
        bottomArea.add(nextButton);
        bottomArea.add(exitButton);
        this.add(bottomArea);
    }
}