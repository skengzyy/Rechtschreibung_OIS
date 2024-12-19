package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import javax.swing.*;
import java.awt.*;

public class FragenpoolPanel extends JPanel {
    private TrainerController tc;
    private JTextField index, text, filename;
    private JComboBox verwalten;
    private int indexFrage;
    private JLabel labelVerwaltung, frageAction, labelIndex,labelAktion, labelAuswahl;
    public FragenpoolPanel(TrainerController tc) {
        this.tc = tc;
        GridBagLayout areas = new GridBagLayout();
        this.setLayout(areas);

        JPanel selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(5,1));

        // top area selection
        String verwaltungOptions[] = {"Fragen bearbeiten","Frage hinzufügen", "Frage löschen", "Fragen laden"};
        verwalten = new JComboBox(verwaltungOptions);

        Font f = new Font("Arial", Font.ITALIC, 20);
        labelVerwaltung = new JLabel("Auswahl:", JLabel.CENTER);
        labelVerwaltung.setFont(f);

        selectionArea.add(labelVerwaltung);
        selectionArea.add(verwalten);

        //Eingabefelder
        Font i = new Font("Arial", Font.BOLD, 14);
        frageAction = new JLabel("bearbeiten");
        frageAction.setFont(i);

        labelIndex = new JLabel("Gebe den Index der Frage an die " + frageAction + " werden soll."); // bearbeiten oder löschen
        index = new JTextField();

        labelAktion = new JLabel("Gebe die Frage die neue Frage ein (bearbeiten). Index: " + indexFrage );
        text = new JTextField();


        this.add(selectionArea);
        this.add(text);

    }
}
