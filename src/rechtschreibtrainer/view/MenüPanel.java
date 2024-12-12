package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;

public class MenüPanel extends JPanel  {
    private TrainerController tc;
    private JButton verwalten, quizmode, spielmode,hilfe,exit;
    private JLabel titel;
    public MenüPanel(TrainerController tc) {
        this.tc = tc;
        GridLayout gl = new GridLayout(5,1,3,3);
        this.setLayout(gl);
        this.setBackground(Color.LIGHT_GRAY);

        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new GridLayout(0,2));
        bottomArea.setBackground(Color.GRAY);

        Font f = new Font("Arial", Font.BOLD, 28);
        Font f2 = new Font("Arial", Font.PLAIN,18);

        titel = new JLabel("SpellWeaver - Rechtschreibtrainer", JLabel.CENTER);
        titel.setFont(f);

        verwalten = new JButton("Fragenpool verwalten");
        verwalten.setFont(f2);
        verwalten.addActionListener(this.tc);
        verwalten.setActionCommand("verwalten");

        quizmode = new JButton("Quizmodus starten");
        quizmode.setFont(f2);
        quizmode.addActionListener(this.tc);
        quizmode.setActionCommand("quizmode");
        String test = "test";


        spielmode = new JButton("Spielmodus starten");
        spielmode.setFont(f2);
        spielmode.addActionListener(this.tc);
        spielmode.setActionCommand("spielmode");

        hilfe = new JButton("Hilfe");
        hilfe.setForeground(Color.LIGHT_GRAY);
        hilfe.addActionListener(this.tc);
        hilfe.setActionCommand("hilfe");

        exit = new JButton("Exit");
        exit.setForeground(Color.RED);
        exit.addActionListener(this.tc);
        exit.setActionCommand("exit");

        this.add(titel);
        this.add(verwalten);
        this.add(quizmode);
        this.add(spielmode);

        bottomArea.add(hilfe);
        bottomArea.add(exit);

        this.add(bottomArea);

    }
}
