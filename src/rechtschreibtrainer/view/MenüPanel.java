package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;

public class MenüPanel extends JPanel {
    private JButton verwalten, quizmode, spielmode, hilfe, exit;
    private JLabel titel;

    public MenüPanel(TrainerController controller) {
        // Setze GridLayout (5x1 für Buttons + 1 Zeile für Titel)
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 44, 52)); // Dunkles Thema

        // Fonts
        Font titelFont = new Font("Arial", Font.BOLD, 32);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        // Titel
        titel = new JLabel("SpellWeaver - Rechtschreibtrainer", JLabel.CENTER);
        titel.setFont(titelFont);
        titel.setForeground(new Color(230, 230, 230)); // Helle Schrift
        titel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Abstände
        this.add(titel, BorderLayout.NORTH);

        // Panel für die Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 Reihen, gleichmäßige Abstände
        buttonPanel.setBackground(new Color(40, 44, 52)); // Gleicher Hintergrund wie Hauptpanel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Außenabstand

        // Buttons erstellen
        verwalten = createStyledButton("Fragenpool verwalten", buttonFont, new Color(59, 89, 152), Color.WHITE, "verwalten", controller);
        quizmode = createStyledButton("Quizmodus starten", buttonFont, new Color(46, 204, 113), Color.WHITE, "quizmode", controller);
        spielmode = createStyledButton("Spielmodus starten", buttonFont, new Color(241, 196, 15), Color.WHITE, "spielmode", controller);
        hilfe = createStyledButton("Hilfe", buttonFont, new Color(52, 152, 219), Color.WHITE, "hilfe", controller);
        exit = createStyledButton("Beenden", buttonFont, new Color(231, 76, 60), Color.WHITE, "exit", controller);

        // Buttons hinzufügen
        buttonPanel.add(verwalten);
        buttonPanel.add(quizmode);
        buttonPanel.add(spielmode);
        buttonPanel.add(hilfe);
        buttonPanel.add(exit);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    // Hilfsmethode zum Erstellen von Buttons
    private JButton createStyledButton(String text, Font font, Color bgColor, Color fgColor, String actionCommand, TrainerController controller) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2));
        button.setActionCommand(actionCommand);
        button.addActionListener(controller);

        return button;
    }
}
