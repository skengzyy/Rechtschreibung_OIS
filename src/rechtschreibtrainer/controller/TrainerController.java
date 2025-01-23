package rechtschreibtrainer.controller;

import rechtschreibtrainer.model.BildFrage;
import rechtschreibtrainer.model.MyFragenpool;
import rechtschreibtrainer.model.Rechtschreibtrainer;
import rechtschreibtrainer.view.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class TrainerController implements ActionListener {
    private MenüPanel mp;
    private MenüFrame mf;
    private FragenpoolPanel fragenPanel;
    private FragenpoolFrame fragenFrame;
    private QuizPanel quizPanel;
    private QuizFrame quizFrame;
    private SpielPanel spielPanel;
    private SpielFrame spielFrame;
    private int index = 0;

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
        spielPanel = new SpielPanel(this, trainer.getFragenpool()[index]);
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
            quizPanel.toggleMusic();
            mf.setVisible(false);
            quizFrame.setVisible(true);
        } else if (ac.equals("spielmode")) {
            spielPanel.startMusic();
            mf.setVisible(false);
            spielFrame.setVisible(true);
        } else if (ac.equals("hilfe")) {
            JOptionPane.showMessageDialog(null,  "1.Fragenpool verwaltung: \n" + "\n" + "- Frage bearbeiten" + "\n" + "- Frage hinzufügen" + "\n" + "- Frage löschen" +  "\n" + "- Fragen laden" + "\n\n2.Quizmodus starten:\n " + "\nQuizmodus in dem Fragen in Form von Text \n(Text, Integer oder Boolean Eingaben) \n und Bild gestellt." + "\n\n3.Spielmodus:\n\n Fragen werden in Form eines Spiels gestellt. \n Es wird ein Wort gemischt und es muss aus \nden Buchstaben das richtige Wort gewählt werden." , "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (ac.equals("exit")) {
            System.exit(0);
        }
        //spielmodus
        if (ac.equals("spiel_abbrechen")) {
            spielPanel.stopMusic();
            spielFrame.setVisible(false);
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
            index = 0;
            spielPanel.resetSpielPanel(trainer.getFragenpool()[index], this, false);
        }else if(ac.equals("spiel_next")){
            index++;
            spielPanel.resetSpielPanel(trainer.getFragenpool()[index], this, true);
        }

        //fragenpool verwaltung
        if(ac.equals("load_fragenpool")) {
            File f = fragenPanel.loadQuestions();
            trainer = fragenpool.laden(f);
            if(trainer.getFragenpool() != null) {
                for(int i = 0; i < trainer.getFragenpool().length;i++ ) {
                    if(trainer.getFragenpool()[i] != null) {
                        fragenPanel.addTextToQuestionDisplay(i + ". " +trainer.getFragenpool()[i].toString());
                        //gleichzeitig die fragen zu myfragenpool hinzufugune
                        if(this.fragenpool!= null) {
                            fragenpool.addFrage(trainer.getFragenpool()[i]);
                        }
                    }

                }
            }

            if(trainer.getFragenpool()[0] != null && !(trainer.getFragenpool()[0] instanceof BildFrage)) {
                quizPanel.updatePanel(trainer.getFragenpool()[0].getFrageText());
            } else if(trainer.getFragenpool()[0] instanceof BildFrage) {
                Image image = null;
                URL url = null;
                try {
                    url = new URL(trainer.getFragenpool()[0].getFrageText());
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    image = ImageIO.read(url);
                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }
                try {
                    quizPanel.updatePanel(url);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            mp.enabledModes(true);

        } else if(ac.equals("save_fragenpool")) {
            fragenPanel.saveQuestions();
            fragenpool.speichern(fragenPanel.saveQuestions());
        } else if(ac.equals("info_fragenpool")) {
            fragenPanel.showInfoDialog();
        }
        //fragen hinzufügen, ändern oder löschen
        if(ac.equals("delete_Frage_Ok")) {
            int index = fragenPanel.getDeleteIndex();
            fragenPanel.addTextToQuestionDisplay("\"Frage gelöscht (Index \" " + index +" \")\\n\"");
            if(index != -1) fragenpool.deleteFrage(index);
        }
        if(ac.equals("add_Frage_Ok")){

        }
        if(ac.equals("edit_Frage_Ok")) {

        }

        //quizmodus
        if(ac.equals("quiz_abbrechen")) {
            quizPanel.stopMusic();
            quizFrame.setVisible(false);
            mf.setVisible(true);
        }
        if(ac.equals("next_quizmode")) {
            index += 1;
            if(trainer.getFragenpool()[index] != null && !(trainer.getFragenpool()[index] instanceof BildFrage)) {
                quizPanel.updatePanel(trainer.getFragenpool()[index].getFrageText());
            } else if(trainer.getFragenpool()[index] instanceof BildFrage) {
                Image image = null;
                URL url = null;
                try {
                    url = new URL(trainer.getFragenpool()[index].getFrageText());
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    image = ImageIO.read(url);
                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }
                try {
                    quizPanel.updatePanel(url);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


    }

}