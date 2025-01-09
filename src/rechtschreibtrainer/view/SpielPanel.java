package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionListener;
public class SpielPanel extends JPanel {
    private TrainerController tc;
    private JButton nextButton;
    private JButton backButton;
    private JButton exitButton;
    private JLabel counterLabel;
    private JLabel questionLabel;
    private int currentQuestion = 1;
    private final int totalQuestions = 20;

    private ArrayList<JButton> button_arr = new ArrayList<JButton>();
    
    private final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public SpielPanel(ActionListener listener, char[] frage, TrainerController tc) throws IOException {
        this.tc = tc;
        char[] new_frage = new char[(frage.length * 2) - 1];

        System.arraycopy(frage, 0, new_frage, 0, frage.length);

        for(int i = 0; i < new_frage.length - frage.length; ++i){
            new_frage[i] = Character.toUpperCase(alphabet[new Random().nextInt(frage.length)]);
        }

        shuffleArr(new_frage);


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

        for(char c : new_frage){
            button_arr.add(new JButton(String.valueOf(c)));
        }

        int btnSize = getPreferredBtnWidth(button_arr.size());

        for(JButton b : button_arr){

            b.setFont(new Font("Arial", Font.BOLD, 18));
            b.setPreferredSize(new Dimension(btnSize, btnSize));
            b.setBackground(new Color(40, 44, 52)); // Cornflower Blue
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Steel Blue border
            b.setActionCommand("rightOrWrong");
            b.addActionListener(listener);
            buttonGbc.gridx = 0;
            buttonGbc.gridy = 0;
            buttonGbc.weightx = 0.5;
            buttonGbc.fill = GridBagConstraints.HORIZONTAL;
            buttonPanel.add(nextButton, buttonGbc);
        }


    }


    public char[] shuffleArr(char[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            char temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        return array;
    }

    public int getPreferredBtnWidth(int btnCount){
        return (700 - (10 * btnCount)) / btnCount;
    }

}
