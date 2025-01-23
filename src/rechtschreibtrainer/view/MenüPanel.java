package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;

public class MenüPanel extends JPanel {
    private JButton verwalten, quizmode, spielmode, hilfe, exit;
    private JLabel titel;

    public MenüPanel(TrainerController controller) {

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 44, 52));

        // fonts
        Font titelFont = new Font("Arial", Font.BOLD, 32);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        // titel
        titel = new JLabel("SpellWeaver - Rechtschreibtrainer", JLabel.CENTER);
        titel.setFont(titelFont);
        titel.setForeground(new Color(230, 230, 230));
        titel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(titel, BorderLayout.NORTH);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(40, 44, 52));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // button erstellen
        verwalten = createStyledButton("Fragenpool laden//verwalten", buttonFont, new Color(59, 89, 152), Color.WHITE, "verwalten", controller);
        quizmode = createStyledButton("Quizmodus starten", buttonFont, new Color(46, 204, 113), Color.WHITE, "quizmode", controller);
        quizmode.setEnabled(false);
        spielmode = createStyledButton("Spielmodus starten", buttonFont, new Color(241, 196, 15), Color.WHITE, "spielmode", controller);
        spielmode.setEnabled(false);
        hilfe = createStyledButton("Hilfe", buttonFont, new Color(52, 152, 219), Color.WHITE, "hilfe", controller);
        exit = createStyledButton("Beenden", buttonFont, new Color(231, 76, 60), Color.WHITE, "exit", controller);

        buttonPanel.add(verwalten);
        buttonPanel.add(quizmode);
        buttonPanel.add(spielmode);
        buttonPanel.add(hilfe);
        buttonPanel.add(exit);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Methode die die Buttons erstellts(um Code verdopplung zu vermeiden
     * @param text die text des Buttons
     * @param font font des texts
     * @param bgColor farbe der button
     * @param fgColor farbe des text
     * @param actionCommand actioncommand fur controller
     * @param controller controller verbindeung
     * @return die erstetlle button als JButton
     */
    private JButton createStyledButton(String text, Font font, Color bgColor, Color fgColor, String actionCommand, TrainerController controller) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2));
        button.setActionCommand(actionCommand);
        button.addActionListener(controller);

        return button;
    }

    public void enabledModes(boolean yes) {
        if(yes) {
            quizmode.setEnabled(true);
            spielmode.setEnabled(true);
        } else {
            quizmode.setEnabled(false);
            spielmode.setEnabled(false);
        }
    }
}
