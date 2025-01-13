package rechtschreibtrainer.controller;

import rechtschreibtrainer.model.MyFragenpool;
import rechtschreibtrainer.model.Rechtschreibtrainer;
import rechtschreibtrainer.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class TrainerController implements ActionListener {
    private MenüPanel mp;
    private MenüFrame mf;
    private FragenpoolPanel fragenPanel;
    private FragenpoolFrame fragenFrame;
    private QuizPanel quizPanel;
    private QuizFrame quizFrame;
    private SpielPanel spielPanel;
    private SpielFrame spielFrame;

    private MyFragenpool fragenpool = new MyFragenpool(20);
    private Rechtschreibtrainer trainer;

    public static void main(String[] args) throws IOException {
        new TrainerController();
    }

    public TrainerController() throws IOException {
        // Menü-Panel erstellen und Controller übergeben
        mp = new MenüPanel(this);
        mf = new MenüFrame(mp);
        fragenPanel = new FragenpoolPanel(this);
        fragenFrame = new FragenpoolFrame(fragenPanel);
        quizPanel = new QuizPanel(this);
        quizFrame = new QuizFrame(quizPanel);
        //char[] bs = {'A', 'N', 'D','L','I','E'};
        char[] bs = {'A', 'N', 'D','L','I','E','E', 'N', 'D','L','I','A'};
        spielPanel = new SpielPanel(this, bs, "DANIELDANIEL");
        spielFrame = new SpielFrame(spielPanel);

        // Fenster-Events hinzufügen
        fragenFrame.addWindowListener(createWindowListener(mf, fragenFrame));
    }

    private WindowListener createWindowListener(JFrame mainFrame, JFrame childFrame) {
        return new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                childFrame.setVisible(false);
                mainFrame.setVisible(true);
            }

            // Leere Methoden, um alle WindowListener-Methoden zu implementieren
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        if (ac.equals("verwalten")) {
            mf.setVisible(false);
            fragenFrame.setVisible(true);
        } else if (ac.equals("quizmode")) {
            mf.setVisible(false);
            quizFrame.setVisible(true);
        } else if (ac.equals("spielmode")) {
            mf.setVisible(false);
            spielFrame.setVisible(true);
        } else if (ac.equals("hilfe")) {
            JOptionPane.showMessageDialog(null,  "1.Fragenpool verwaltung: \n" + "\n" + "- Frage bearbeiten" + "\n" + "- Frage hinzufügen" + "\n" + "- Frage löschen" +  "\n" + "- Fragen laden" + "\n\n2.Quizmodus starten:\n " + "\nQuizmodus in dem Fragen in Form von Text \n(Text, Integer oder Boolean Eingaben) \n und Bild gestellt." + "\n\n3.Spielmodus:\n\n Fragen werden in Form eines Spiels gestellt. \n Es wird ein Wort gemischt und es muss aus \nden Buchstaben das richtige Wort gewählt werden." , "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (ac.equals("exit")) {
            System.exit(0);
        }
        if (ac.equals("spiel_abbrechen")) {
            spielFrame.setVisible(false);
            mf.setVisible(true);
        }
        if(ac.equals("quiz_abbrechen")) {
            quizFrame.setVisible(false);
            mf.setVisible(true);
        }
        else if (ac.startsWith("buchstabe_")) {
            char ausgewählterBuchstabe = ac.charAt(ac.length() - 1);
            spielPanel.addBuchstabeToAntwort(ausgewählterBuchstabe);

            String aktuelleEingabe = spielPanel.getAktuelleAntwort();
            if (spielPanel.getRichtigeAntwort().startsWith(aktuelleEingabe)) {

                spielPanel.highlightButton((JButton) e.getSource(), true);

                if (aktuelleEingabe.equals(spielPanel.getRichtigeAntwort())) {
                    spielPanel.updateStatus(true, aktuelleEingabe);
                }
            } else {
                spielPanel.highlightButton((JButton) e.getSource(), false);
                spielPanel.updateStatus(false, aktuelleEingabe);
            }
        }else if(ac.equals("resetSpielPanel")){
            spielPanel.resetSpielPanel(new char[] {'N', 'E', 'U'}, "NEU", this, false);
        }else if(ac.equals("spiel_next")){
            spielPanel.resetSpielPanel(new char[] {'N', 'E', 'U'}, "NEU", this, true);
        }

        //fragenpool verwaltung
        if(ac.equals("load_fragenpool")) {
            File f = fragenPanel.loadQuestions();
            trainer = fragenpool.laden(f);
            mp.enabledModes(true);

        } else if(ac.equals("save_fragenpool")) {
            fragenPanel.saveQuestions();
            fragenpool.speichern(fragenPanel.saveQuestions());
        } else if(ac.equals("info_fragenpool")) {
            fragenPanel.showInfoDialog();
        }



    }

}