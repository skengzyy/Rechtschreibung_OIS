package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;

public class MenüPanel extends JPanel {
    private JButton verwalten, quizmode, spielmode, hilfe, exit;
    JDialog dialogSavePool;
    private JLabel titel;
    private TrainerController controller;
    public MenüPanel(TrainerController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 44, 52));

        // Fonts
        Font titelFont = new Font("Arial", Font.BOLD, 32);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        // Titel
        titel = new JLabel("SpellWeaver - Rechtschreibtrainer", JLabel.CENTER);
        titel.setFont(titelFont);
        titel.setForeground(new Color(230, 230, 230));
        titel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(titel, BorderLayout.NORTH);

        // Button-Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(40, 44, 52));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Buttons erstellen
        verwalten = createStyledButton("Fragenpool laden//verwalten", buttonFont, new Color(59, 89, 152), Color.WHITE, "verwalten", controller);
        quizmode = createStyledButton("Quizmodus starten", buttonFont, new Color(46, 204, 113), Color.WHITE, "quizmode", controller);
        spielmode = createStyledButton("Spielmodus starten", buttonFont, new Color(241, 196, 15), Color.WHITE, "spielmode", controller);
        hilfe = createStyledButton("Hilfe", buttonFont, new Color(52, 152, 219), Color.WHITE, "hilfe", controller);
        exit = createStyledButton("Beenden", buttonFont, new Color(231, 76, 60), Color.WHITE, "exit", controller);

        buttonPanel.add(verwalten);
        buttonPanel.add(quizmode);
        buttonPanel.add(spielmode);
        buttonPanel.add(hilfe);
        buttonPanel.add(exit);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

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
    public void showPoolNotLoaded() {
        JOptionPane.showMessageDialog(null, "Fragenpool noch nicht geladen! \nÖffne den Fragenpool Verwaltungsfenster \num einen Fragenpool zu laden", "Hinweis", JOptionPane.WARNING_MESSAGE);
    }
    public void isPoolSavedDialog() {
        dialogSavePool = new JDialog((Frame) null, "Fragenpool nicht gespeichert", true);
        dialogSavePool.setLayout(new GridLayout(2, 1, 10, 10));


        JLabel infoText = new JLabel("Ihr Fragenpool wurde geändert aber nicht gespeichert.");
        dialogSavePool.add(infoText);
        JPanel selection = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton speichern = new JButton("Speichern");
        JButton beenden = new JButton("Exit");
        beenden.setActionCommand("exit_dialog");
        speichern.setActionCommand("save_dialog");
        beenden.addActionListener(controller);
        speichern.addActionListener(controller);

        selection.add(speichern);
        selection.add(beenden);
        dialogSavePool.add(selection);

        dialogSavePool.setSize(400, 150);
        dialogSavePool.setLocationRelativeTo(this);
        dialogSavePool.setVisible(true);
    }
    public void hideExitDialog() {
        dialogSavePool.setVisible(false);
    }



}