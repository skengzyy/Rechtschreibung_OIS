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
        verwalten.setBackground(Color.WHITE);
        verwalten.setOpaque(true);
        verwalten.setFont(f2);
        verwalten.addActionListener(this.tc);
        verwalten.setActionCommand("verwalten");

        quizmode = new JButton("Quizmodus starten");
        quizmode.setBackground(Color.WHITE);
        quizmode.setOpaque(true);
        quizmode.setFont(f2);
        quizmode.addActionListener(this.tc);
        quizmode.setActionCommand("quizmode");
        String test = "test";


        spielmode = new JButton("Spielmodus starten");
        spielmode.setBackground(Color.WHITE);
        spielmode.setOpaque(true);
        spielmode.setFont(f2);
        spielmode.addActionListener(this.tc);
        spielmode.setActionCommand("spielmode");

        hilfe = new JButton("Hilfe");
        hilfe.setBackground(Color.GRAY);
        hilfe.setOpaque(true);
        hilfe.setBorderPainted(false);
        hilfe.addActionListener(this.tc);
        hilfe.setActionCommand("hilfe");
        hilfe.addActionListener( e -> showHelp());

        exit = new JButton("Exit");
        exit.setBackground(Color.RED);
        exit.setOpaque(true);
        exit.setBorderPainted(false);
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
    public void showHelp() {
        JOptionPane.showMessageDialog(this,
                "1.Fragenpool verwaltung: \n" + "\n" + "- Frage bearbeiten" + "\n" + "- Frage hinzufügen" + "\n" + "- Frage löschen" +  "\n" + "- Fragen laden" + "\n\n2.Quizmodus starten:\n " + "\nQuizmodus in dem Fragen in Form von Text \n(Text, Integer oder Boolean Eingaben) \n und Bild gestellt." + "\n\n3.Spielmodus:\n\n Fragen werden in Form eines Spiels gestellt. \n Es wird ein Wort gemischt und es muss aus \nden Buchstaben das richtige Wort gewählt werden." ,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
