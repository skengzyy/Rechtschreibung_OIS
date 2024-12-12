package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import javax.swing.*;
import java.awt.*;

public class FragenpoolPanel extends JPanel {
    private TrainerController tc;
    private JComboBox verwalten;
    private JLabel labelVerwaltung;
    public FragenpoolPanel(TrainerController tc) {
        this.tc = tc;
        GridBagLayout areas = new GridBagLayout();
        this.setLayout(areas);

        JPanel selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(1,2));
        String verwaltungOptions[] = {"Fragen bearbeiten","Frage hinzufügen", "Frage löschen", "Fragen laden"};
        verwalten = new JComboBox(verwaltungOptions);
        selectionArea.setPreferredSize(new Dimension(getWidth(),getHeight()/2-100));

        JPanel options = new JPanel();
        JLabel text = new JLabel("SDHJGJHFSGJKLFDHJGKHLFDJHGSLHFKJDGHSFDHGSKFJGHSLK");
        options.setBackground(Color.WHITE);
        options.add(text);
        Font f = new Font("Arial", Font.ITALIC, 20);
        labelVerwaltung = new JLabel("Auswahl:", JLabel.CENTER);
        labelVerwaltung.setFont(f);



        selectionArea.add(labelVerwaltung);
        selectionArea.add(verwalten);

        this.add(selectionArea);
        this.add(options);




    }
}
