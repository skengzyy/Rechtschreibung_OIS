package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;
public class HilfePanel extends JPanel {
    private TrainerController tc;
    public HilfePanel(TrainerController tc) {
        this.tc = tc;
        JTextArea text = new JTextArea("1.Fragenpool verwaltung: \n" + "\n" + "- Frage bearbeiten" + "\n" + "- Frage hinzufügen" + "\n" + "- Frage löschen" +  "\n" + "- Fragen laden" + "\n\n2.Quizmodus starten:\n " + "\nQuizmodus in dem Fragen in \nForm von Text und Bild gestellt werden" + "\n\n 3.Spielmodus:\n\n Fragen werden in Form \n eines Spiels gestellt \n Es wird ein Wort gemischt\n und es muss aus den Buchstaben \n das richtige Wort ausgewählt werden\n" );
        this.add(text);
    }
}
