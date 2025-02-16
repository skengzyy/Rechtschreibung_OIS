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
    private int indexFrageQuiz = 0;
    private int maxFragenQuiz = 0;
    private int indexFrageSpiel = 0;
    private int maxFragenSpiel = 0;
    private int indexEdit;
    private boolean fragenpoolGeladen = false;
    private boolean fragenpoolChanged = false;
    private boolean fragenpoolSaved = false;
    private boolean quizmodeStarted = false;
    private boolean spielModeStarted = false;

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
                setMaxFragenQuiz();
                quizPanel.setTotalQuestions(maxFragenQuiz);
                quizPanel.resetCounter();
                quizFrame.setVisible(true);
                showQuestionsQuizMode();

            } else {
                menüPanel.showPoolNotLoaded();
            }
        }
        if (ac.equals("spielmode")) {
            if(fragenpoolGeladen) {
                spielPanel = new SpielPanel(this, trainer.getFragenpool()[indexFrageSpiel]);
                spielFrame = new SpielFrame(spielPanel);
                setMaxFragenSpiel();
                spielPanel.setTotalQuestions(maxFragenSpiel);
                trainer.addCountAbgefragtSpiel();
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
            fragenpool.speichern(fragenPanel.saveQuestions(), trainer.getAnzahlVersuche());
            fragenpoolSaved = true;
            menüPanel.hideExitDialog();
            System.exit(0);
        }

        //quizmodus
        if(ac.equals("quiz_antwort")) {
            String antwort = quizPanel.getAntwortText();
            if(antwort != null && !(antwort.equals(""))) {
                if(trainer.getFragenpool()[indexFrageQuiz].check(antwort)) {
                    trainer.addCountRichtige();
                    if(trainer.getCountAbgefragt() != maxFragenQuiz) {
                        quizPanel.showAntwortDialog(quizFrame,true);
                    }
                } else if(!(trainer.getFragenpool()[indexFrageQuiz].check(antwort) && trainer.getCountAbgefragt() != maxFragenQuiz) ) {
                    quizPanel.showAntwortDialog(quizFrame,false);
                }
                trainer.addCountAbgefragt();
                quizPanel.enableAntwort(false);
                quizPanel.enableNext(true);

                if(indexFrageQuiz == maxFragenQuiz-1 && trainer.getAnzahlVersuche() != 3) {
                    quizPanel.showStatistikDialog(quizFrame, trainer.getCountAbgefragt(), trainer.getCountRichtige(), trainer.getCountFalsche(), this);
                    quizPanel.enableAntwort(false);
                    quizPanel.enableNext(false);
                } else if(trainer.getAnzahlVersuche() == 3 && trainer.getCountRichtige() >= Math.round(trainer.getCountAbgefragt()*0.7) && trainer.getCountAbgefragt() == maxFragenQuiz){
                    quizPanel.showKroneDialog(quizFrame, this);
                    quizPanel.enableAntwort(false);
                    quizPanel.enableNext(false);
                }

            } else {
                fragenPanel.showErrorMessage("null", "Es wurde keine Antwort eingegeben");
            }
        }
        if(ac.equals("quiz_abbrechen")) {
            quizPanel.stopMusic();
            quizFrame.setVisible(false);
            menüFrame.setVisible(true);
        }
        if(ac.equals("next_quizmode")) {
            indexFrageQuiz = quizPanel.updateFrageIndex(indexFrageQuiz, maxFragenQuiz);
            if(indexFrageQuiz < trainer.getFragenpool().length && indexFrageQuiz != -1) {
                quizPanel.emptyAnswerField();
                showQuestionsQuizMode();
                quizPanel.enableAntwort(true);
                quizPanel.enableNext(false);
            } else if(trainer.getAnzahlVersuche() == 3 && trainer.getCountRichtige() >= Math.round(trainer.getCountAbgefragt()*0.7)) {
               // quizPanel.showKroneDialog(quizFrame);
            } else {
                quizPanel.showStatistikDialog(quizFrame, trainer.getCountAbgefragt(), trainer.getCountRichtige(), trainer.getCountFalsche(), this);
            }
        }
        if(ac.equals("retry_Statistik")) {
            trainer.setCountAbgefragt(0);
            trainer.setCountRichtige(0);
            trainer.setCountFalsche(0);
            loadQuestionsAgain();
        }
        if(ac.equals("continue_Statistik")) {
            if(trainer.getCountRichtige() >= Math.round(trainer.getCountAbgefragt()*0.7) && trainer.getAnzahlVersuche() <= 3) {
                quizPanel.showSuccessStatisticsMessage("continue");
                trainer.setCountAbgefragt(0);
                trainer.setCountRichtige(0);
                trainer.setCountFalsche(0);
                trainer.addAnzahlVersuche();
                loadQuestionsAgain();
            }
            else {
                quizPanel.showErrorStatisticsMessage("continue");
            }

        }
        if(ac.equals("menü_Statistik") || ac.equals("ok_könig_dialog") || ac.equals("menü_statistik_spiel")) {
            spielFrame.setVisible(false);
            quizFrame.setVisible(false);
            menüFrame.setVisible(true);
        }


        //spielmodus
        if (ac.equals("spiel_abbrechen")) {
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
                    trainer.addCountRichtigeSpiel();
                    if (indexFrageSpiel >= getLastIndexOfWordAnswer()) {
                        spielPanel.enableNext(false);
                        spielPanel.showStatistikDialog(spielFrame, trainer.getCountAbgefragtSpiel(), trainer.getCountRichtigeSpiel(), trainer.getCountFalscheSpiel(), this);
                    }
                }
            } else {
                spielPanel.highlightButton((JButton) e.getSource(), false);
                spielPanel.updateStatus(false, aktuelleEingabe);
                if (indexFrageSpiel >= getLastIndexOfWordAnswer()) {
                    spielPanel.enableNext(false);
                    spielPanel.showStatistikDialog(spielFrame, trainer.getCountAbgefragtSpiel(), trainer.getCountRichtigeSpiel(), trainer.getCountFalscheSpiel(), this);
                }
            }

        }
        if(ac.equals("resetSpielPanel")){
            spielPanel.resetSpielPanel(trainer.getFragenpool()[indexFrageSpiel], this);
        }
        if(ac.equals("spiel_next")){
            boolean istFrageAntwortEinWort = true;
            while (istFrageAntwortEinWort) {
                indexFrageSpiel = spielPanel.updateFrageIndex(indexFrageSpiel, trainer.getFragenpool().length);
                if(indexFrageSpiel != -1) {
                    if (trainer.getFragenpool()[indexFrageSpiel].getTyp().equals("String") ||
                            trainer.getFragenpool()[indexFrageSpiel] instanceof BildFrage) {

                        trainer.addCountAbgefragtSpiel();
                        spielPanel.updateCounter();
                        spielPanel.resetSpielPanel(trainer.getFragenpool()[indexFrageSpiel], this);
                        break;
                    }

                    if (indexFrageSpiel >= maxFragenSpiel) {
                        istFrageAntwortEinWort = false;
                    }
                }
            }



        }
        if(ac.equals("retry_statistik_spiel")) {
            trainer.setCountAbgefragtSpiel(0);
            trainer.setCountRichtigeSpiel(0);
            trainer.setCountFalscheSpiel(0);
            spielPanel.resetSpielPanel(trainer.getFragenpool()[indexFrageSpiel], this);
            spielPanel.enableNext(true);
            spielPanel.getStatistik().dispose();
            spielPanel.setCurrentQuestion(1);
            boolean istFrageAntwortEinWort = true;
            while (istFrageAntwortEinWort) {
                indexFrageSpiel = spielPanel.updateFrageIndex(indexFrageSpiel, trainer.getFragenpool().length);
                if(indexFrageSpiel != -1) {
                    if (trainer.getFragenpool()[indexFrageSpiel].getTyp().equals("String") ||
                            trainer.getFragenpool()[indexFrageSpiel] instanceof BildFrage) {

                        trainer.addCountAbgefragtSpiel();
                        spielPanel.updateCounter();
                        spielPanel.resetSpielPanel(trainer.getFragenpool()[indexFrageSpiel], this);
                        break;
                    }

                    if (indexFrageSpiel >= maxFragenSpiel) {
                        istFrageAntwortEinWort = false;
                    }
                }
            }

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
                setMaxFragenQuiz();
            } else {
                fragenPanel.showErrorMessage("null", "Ein Fragenpool ist schon geladen");
            }

        }
        if(ac.equals("save_fragenpool")) {
            if(fragenpoolGeladen) {
                fragenpool.speichern(fragenPanel.saveQuestions(), trainer.getAnzahlVersuche());
                fragenpoolSaved = true;
            } else {
                fragenPanel.showErrorMessage("null", "Es ist kein Fragenpool vorhanden.");
            }

        }
        if(ac.equals("info_fragenpool")) {
            fragenPanel.showInfoDialog();
        }

        //fragen hinzufügen, ändern oder löschen
        if(ac.equals("add_Frage_Dialog")) {
            if(fragenpool.getSize() == Speicherbar.MAXFRAGEN) {
                fragenPanel.showErrorMessage("null", "Der Fragenpool hat schon die max. Anzahl("+ Speicherbar.MAXFRAGEN +") an erlaubten Fragen");
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

                }
                fragenPanel.showSuccessMessage("edit", "Frage" + " (" + indexEdit+1 + ") " + "erfolgreich geändert.");
                reloadQuestionDisplay();
                fragenpoolChanged = true;
            }
        }
        if(ac.equals("edit_Frage_index")) {
            indexEdit = fragenPanel.getEditIndex() - 1;
            if(indexEdit != -1 && trainer.getFragenpool()[indexEdit] != null && indexEdit < fragenpool.getSize() && indexEdit >=0) {
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
        setMaxFragenQuiz();
        fragenPanel.addTextToQuestionDisplay(fragenpool.poolToString());
    }
    public void showQuestionsQuizMode() {
        if(indexFrageQuiz != -1){
            reloadQuestionDisplay();
            if(trainer.getFragenpool()[indexFrageQuiz] != null && !(trainer.getFragenpool()[indexFrageQuiz] instanceof BildFrage)) {
                quizPanel.updatePanel(trainer.getFragenpool()[indexFrageQuiz].getFrageText());
            }
            else if(trainer.getFragenpool()[indexFrageQuiz] instanceof BildFrage) {
                Image image = null;
                URL url = null;
                try {
                    url = new URL(trainer.getFragenpool()[indexFrageQuiz].getFrageText());
                } catch (MalformedURLException ex) {
                    System.err.println(ex.getMessage());
                }
                try {
                    image = ImageIO.read(url);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                try {
                    quizPanel.updatePanel(url);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
    public void showQuestionsGameMode() {

    }
    public void setMaxFragenQuiz() {
        int versuche = trainer.getAnzahlVersuche();
        int fragenAnzahl = trainer.getFragenpool().length;

        switch (versuche) {
            case 1:
                versuche = (int) Math.ceil(fragenAnzahl * (1.0 / 3.0));
                break;
            case 2:
                versuche = (int) Math.ceil(fragenAnzahl * (2.0 / 3.0));
                break;
            case 3:
                versuche = fragenAnzahl;
                break;
        }
        this.maxFragenQuiz = versuche;
    }
    public void setMaxFragenSpiel() {
        int fragenAnzahl = 0;
        for(int i = 0; i < trainer.getFragenpool().length;i++) {
            if(trainer.getFragenpool()[i] != null) {
                if((trainer.getFragenpool()[i].getTyp()).equals("String") || trainer.getFragenpool()[i] instanceof BildFrage) {
                    fragenAnzahl += 1;
                }
            }
        }
        this.maxFragenSpiel = fragenAnzahl;
    }
    public int getLastIndexOfWordAnswer() {
        int index = 0;
        for(int i = 0; i < trainer.getFragenpool().length;i++) {
            if(trainer.getFragenpool()[i].getTyp().equals("String") || trainer.getFragenpool()[i] instanceof BildFrage) {
                index = i;
            }
        }
        return index;
    }
    public void loadQuestionsAgain() {
        quizPanel.getStatistik().setVisible(false);
        indexFrageQuiz = 0;
        showQuestionsQuizMode();
        quizPanel.setCurrentQuestion(1);
        setMaxFragenQuiz();
        quizPanel.setTotalQuestions(maxFragenQuiz);
        quizPanel.resetCounter();
        quizPanel.emptyAnswerField();
        quizPanel.enableAntwort(true);
        quizPanel.enableAntwort(true);
        quizPanel.enableNext(false);
    }
    public void loadQuestionsAgainSpiel() {
        spielPanel.getStatistik().setVisible(false);
        indexFrageSpiel = 0;
        spielPanel.resetSpielPanel(trainer.getFragenpool()[indexFrageSpiel], this);
        spielPanel.setCurrentQuestion(0);
        setMaxFragenQuiz();
        spielPanel.setTotalQuestions(maxFragenSpiel);

    }



}