package rechtschreibtrainer.controller;

import rechtschreibtrainer.model.*;
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
    private MenüPanel menüPanel;
    private MenüFrame menüFrame;
    private FragenpoolPanel fragenPanel;
    private FragenpoolFrame fragenFrame;
    private QuizPanel quizPanel;
    private QuizFrame quizFrame;
    private SpielPanel spielPanel;
    private SpielFrame spielFrame;
    private int index = 0;
    private int indexEdit;
    private boolean fragenpoolGeladen = false;
    private boolean fragenpoolChanged = false;
    private boolean fragenpoolSaved = false;

    private MyFragenpool fragenpool = new MyFragenpool(0);
    private Rechtschreibtrainer trainer;

    public static void main(String[] args) throws IOException {
        new TrainerController();
    }

    public TrainerController() throws IOException {
        // Menü-Panel erstellen und Controller übergeben
        menüPanel = new MenüPanel(this);
        menüFrame = new MenüFrame(menüPanel);
        fragenPanel = new FragenpoolPanel(this);
        fragenFrame = new FragenpoolFrame(fragenPanel);
        quizPanel = new QuizPanel(this);
        quizFrame = new QuizFrame(quizPanel);
        //char[] bs = {'A', 'N', 'D','L','I','E'};
        char[] bs = {'A', 'N', 'D','L','I','E','E', 'N', 'D','L','I','A'};
        spielPanel = new SpielPanel(this, new Frage("nichts", "nichts"));
        spielFrame = new SpielFrame(spielPanel);

        // Fenster-Events hinzufügen
        fragenFrame.addWindowListener(createWindowListener(menüFrame, fragenFrame));
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
            menüFrame.setVisible(false);
            fragenFrame.setVisible(true);
        }
        if (ac.equals("quizmode")) {
            if(fragenpoolGeladen) {
                quizPanel.toggleMusic();
                menüFrame.setVisible(false);
                quizFrame.setVisible(true);

            } else {
                menüPanel.showPoolNotLoaded();
            }
        }
        if (ac.equals("spielmode")) {
            if(fragenpoolGeladen) {
                spielPanel.startMusic();
                spielPanel = new SpielPanel(this, trainer.getFragenpool()[index]);
                spielFrame = new SpielFrame(spielPanel);
                menüFrame.setVisible(false);
                spielFrame.setVisible(true);

            } else {
                menüPanel.showPoolNotLoaded();
            }
        }
        if (ac.equals("hilfe")) {
            JOptionPane.showMessageDialog(null,  "1.Fragenpool verwaltung: \n" + "\n" + "- Frage bearbeiten" + "\n" + "- Frage hinzufügen" + "\n" + "- Frage löschen" +  "\n" + "- Fragen laden" + "\n\n2.Quizmodus starten:\n " + "\nQuizmodus in dem Fragen in Form von Text \n(Text, Integer oder Boolean Eingaben) \n und Bild gestellt." + "\n\n3.Spielmodus:\n\n Fragen werden in Form eines Spiels gestellt. \n Es wird ein Wort gemischt und es muss aus \nden Buchstaben das richtige Wort gewählt werden." , "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        // wenn exit gedruckt wird
        if (ac.equals("exit")) {
            if(fragenpoolChanged && !fragenpoolSaved) {
                menüPanel.isPoolSavedDialog();

            } else {
                System.exit(0);
            }

        }
        if(ac.equals("exit_dialog")) {
            System.exit(0);
        }
        if(ac.equals("save_dialog")) {
            fragenpool.speichern(fragenPanel.saveQuestions());
            fragenpoolSaved = true;
            menüPanel.hideExitDialog();
        }
        //quizmodus
        if(ac.equals("quiz_abbrechen")) {
            quizPanel.stopMusic();
            quizFrame.setVisible(false);
            menüFrame.setVisible(true);
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

        //spielmodus
        if (ac.equals("spiel_abbrechen")) {
            spielPanel.stopMusic();
            spielFrame.setVisible(false);
            menüFrame.setVisible(true);
        }
        if (ac.startsWith("buchstabe_")) {
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
        }
        if(ac.equals("resetSpielPanel")){
            index = 0;
            spielPanel.resetSpielPanel(trainer.getFragenpool()[index], this, false);
        }
        if(ac.equals("spiel_next")){
            index++;
            spielPanel.resetSpielPanel(trainer.getFragenpool()[index], this, true);
        }

        //fragenpool verwaltung
        if(ac.equals("load_fragenpool")) {
            if(!fragenpoolGeladen) {
                File f = fragenPanel.loadQuestions();
                trainer = fragenpool.laden(f);
                if(trainer.getFragenpool() != null) {
                    int i = 0;
                    for(i = 0; i < trainer.getFragenpool().length;i++ ) {
                        if(trainer.getFragenpool()[i] != null) {
                            if(this.fragenpool != null) {
                                fragenpool.addFrage(trainer.getFragenpool()[i]);
                            }
                        }

                    }
                }
                this.fragenpoolGeladen = true;
                reloadQuestionDisplay();
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

            } else {
                fragenPanel.showErrorMessage("null", "Ein Fragenpool ist schon geladen");
            }

        }
        if(ac.equals("save_fragenpool")) {
            fragenpool.speichern(fragenPanel.saveQuestions());
            fragenpoolSaved = true;
        }
        if(ac.equals("info_fragenpool")) {
            fragenPanel.showInfoDialog();
        }

        //fragen hinzufügen, ändern oder löschen
        if(ac.equals("add_Frage_Dialog")) {
            if(fragenpool.getSize() == Speicherbar.MAXFRAGEN) {
                fragenPanel.showErrorMessage("null", "Der Fragenpool hat schon die max. Anzahl an erlaubten Fragen");
            } else if(fragenpool.isEmpty()) {
                fragenPanel.showErrorMessage("null", "Der Fragenpool ist noch nicht geladen");
            } else {
                fragenPanel.addQuestion();
            }
        }
        if(ac.equals("edit_Frage_Dialog")) {
            if(fragenpool.isEmpty()) {
                fragenPanel.showErrorMessage("null", "Der Fragenpool ist noch nicht geladen");
            } else {
                fragenPanel.editQuestion();
            }
        }
        if(ac.equals("delete_Frage_Dialog")) {
            if(fragenpool.isEmpty()) {
                fragenPanel.showErrorMessage("null", "Der Fragenpool ist noch nicht geladen");
            } else {
                fragenPanel.deleteQuestion();
            }
        }
        if(ac.equals("delete_Frage_Ok")) {
            int index = fragenPanel.getDeleteIndex();
            if(index != -1) {
                fragenpool.deleteFrage(index);
                trainer.setFragenpool(fragenpool.getFragenpool());
                fragenPanel.showSuccessMessage("delete", "delete");
                reloadQuestionDisplay();
                fragenpoolChanged = true;
            }
        }
        if(ac.equals("add_Frage_Ok")){
            if(trainer.getFragenpool().length != Speicherbar.MAXFRAGEN) {
                String text = fragenPanel.getAddQuestionText();
                String typ = fragenPanel.getAddQuestionTyp();
                String antwort = fragenPanel.getAddAntwort();
                Frage neueFrage = null;
                if(text != null && antwort != null) {
                    switch(typ) {
                        case "String":
                            neueFrage = new Frage(antwort,text);
                            break;
                        case "Bild":
                            neueFrage = new BildFrage(text, antwort);
                            break;
                        case "Integer":
                            neueFrage = new IntegerFrage(text, Integer.parseInt(antwort));
                            break;
                        case "Boolean":
                            neueFrage = new BooleanFrage(text, Boolean.parseBoolean(antwort));
                            break;
                    }
                    System.out.println(neueFrage.toString());
                }
                boolean b = fragenpool.addFrage(neueFrage);
                trainer.setFragenpool(fragenpool.getFragenpool());
                if(b) {
                    fragenPanel.showSuccessMessage("add", "Frage erfolgreich hinzugefúgt");
                    fragenpoolChanged = true;
                }
                reloadQuestionDisplay();
            }
            else {
                fragenPanel.showErrorMessage("add", "Die Maximale Anzahl an erlaubten Fragen ist erreicht");
            }
        }
        if(ac.equals("edit_Frage_Ok")) {
            indexEdit = fragenPanel.getEditIndex() - 1;
            if(trainer.getFragenpool()[indexEdit] != null && indexEdit < fragenpool.getSize() && indexEdit >=0) {
                String text = fragenPanel.getEditFrage();
                String typ = trainer.getFragenpool()[indexEdit].getTyp();
                String antwort = fragenPanel.getEditAntwort();
                Frage neueFrage = null;
                switch(typ) {
                    case "String":
                        neueFrage = new Frage(antwort, text);
                        break;
                    case "Bild":
                        neueFrage = new BildFrage(text, antwort);
                        break;
                    case "Integer":
                        neueFrage = new IntegerFrage(text, Integer.parseInt(antwort));
                        break;
                    case "Boolean":
                        neueFrage = new BooleanFrage(text, Boolean.parseBoolean(antwort));
                        break;
                }

                fragenpool.getFragenpool()[indexEdit] = neueFrage;
                trainer.setFragenpool(fragenpool.getFragenpool());
                if( antwort.equalsIgnoreCase("true") ||antwort.equalsIgnoreCase("false") ) {
                    fragenPanel.showSuccessMessage("edit", "Frage" + " (" + indexEdit + ") " + "erfolgreich geändert.");
                }
                reloadQuestionDisplay();
                fragenpoolChanged = true;
            }
        }
        if(ac.equals("edit_Frage_index")) {
            indexEdit = fragenPanel.getEditIndex() - 1;
            if(index != -1 && trainer.getFragenpool()[indexEdit] != null && indexEdit < fragenpool.getSize() && indexEdit >=0) {
                fragenPanel.enableEdit(true);
                if(trainer.getFragenpool()[indexEdit].getClass() == BildFrage.class) {
                    fragenPanel.setEditFrageText("Neue URL: ");
                } else {
                    fragenPanel.setEditFrageText("Neue Frage:");
                }
                fragenPanel.setEditText(trainer.getFragenpool()[indexEdit].getFrageText());
                fragenPanel.setEditTyp(trainer.getFragenpool()[indexEdit].getTyp());
                fragenPanel.setEditAntwort(trainer.getFragenpool()[indexEdit].getAntwort());
            } else {
                fragenPanel.showErrorMessage("edit", "Index ungúltig. Gebe einen gúltigen Index ein");
            }

        }

    }

    public void reloadQuestionDisplay() {
        fragenPanel.clearQuestionDisplay();
        fragenPanel.addTextToQuestionDisplay(fragenpool.poolToString());
    }

}