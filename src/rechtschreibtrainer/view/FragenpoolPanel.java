package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import rechtschreibtrainer.model.Frage;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FragenpoolPanel extends JPanel {
    private TrainerController tc;
    private JTextArea questionDisplay;
    private JFileChooser fileChooser;

    private Frage[] fragenpool;

    public FragenpoolPanel(TrainerController tc) {
        this.tc = tc;
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(new Color(40, 44, 52));
        JPanel fullPanel = new JPanel(new BorderLayout(10,10));
        fullPanel.setBackground(new Color(40, 44, 52));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(40, 44, 52));

        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(new Color(40, 44, 52));
        toolBar.setFloatable(false);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        JButton loadButton = new JButton(resizeIcon("/pngs/load.png"));
        JButton saveButton = new JButton(resizeIcon("/pngs/save.png"));
        JButton infoButton = new JButton(resizeIcon("/pngs/info.png"));


        Font toolBarFont = new Font("Arial", Font.BOLD, 16);
        loadButton.setFont(toolBarFont);
        saveButton.setFont(toolBarFont);
        infoButton.setFont(toolBarFont);

        loadButton.setPreferredSize(new Dimension(40, 40));
        saveButton.setPreferredSize(new Dimension(40, 40));
        infoButton.setPreferredSize(new Dimension(40, 40));

        loadButton.setFocusable(false);
        saveButton.setFocusable(false);
        infoButton.setFocusable(false);


        loadButton.addActionListener(e -> loadQuestions());

        saveButton.addActionListener(e -> saveQuestions());

        infoButton.addActionListener(e -> showInfoDialog());


        toolBar.add(loadButton);
        toolBar.add(saveButton);
        toolBar.add(infoButton);


        JPanel toolbarPanel = new JPanel(new BorderLayout());
        toolbarPanel.add(toolBar, BorderLayout.CENTER);


        questionDisplay = new JTextArea();
        questionDisplay.setEditable(false);
        questionDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        questionDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(questionDisplay);
        scrollPane.setPreferredSize(new Dimension(800, 200));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(new Color(40, 44, 52));
        JButton addButton = new JButton("Hinzufügen");
        JButton editButton = new JButton("Ändern");
        JButton deleteButton = new JButton("Löschen");


        addButton.setPreferredSize(new Dimension(80, 30));
        editButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setPreferredSize(new Dimension(80, 30));

        addButton.setBackground(new Color(214,253,212));
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);

        editButton.setBackground(new Color(248,253,212));
        editButton.setOpaque(true);
        editButton.setBorderPainted(false);

        deleteButton.setBackground(new Color(253,212,212));
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        addButton.addActionListener(e -> addQuestion());
        editButton.addActionListener(e -> editQuestion());
        deleteButton.addActionListener(e -> deleteQuestion());


        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);


        mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        fullPanel.add(mainPanel, BorderLayout.CENTER);
        this.add(fullPanel);
    }

    // Hilfsmethode zum Skalieren der Icons
    private ImageIcon resizeIcon(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }


    private void saveQuestions() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }

        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Datei gespeichert unter: " + file.getAbsolutePath());
        }
    }


    private void loadQuestions() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Datei ausgewählt: " + file.getAbsolutePath());
        }
    }


    private void showInfoDialog() {
        JOptionPane.showMessageDialog(this,
                "Im Fragenpool Verwaltungsdialog können Sie Fragen bearbeiten, hinzufügen, löschen und aus einer Datei laden. \n Laden - Erstes Button oben. \n Speichern - Zweites Button vor Info",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadQuestion() {

    }
    private void addQuestion() {
        JDialog dialog = new JDialog((Frame) null, "Frage Hinzufügen", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel frageLabel = new JLabel("Frage:");
        JTextField frageField = new JTextField();

        JLabel typLabel = new JLabel("Typ:");
        JComboBox<String> typBox = new JComboBox<>(new String[]{"String", "Bild", "Integer", "Boolean"});

        JLabel antwortLabel = new JLabel("Antwort:");
        JTextField antwortField = new JTextField();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String frage = frageField.getText();
            String typ = (String) typBox.getSelectedItem();
            String antwort = antwortField.getText();
            // Logik zum Hinzufügen der Frage
            questionDisplay.append("Frage hinzugefügt: " + frage + " (" + typ + ") -> " + antwort + "\n");
            dialog.dispose();
        });

        dialog.add(frageLabel);
        dialog.add(frageField);
        dialog.add(typLabel);
        dialog.add(typBox);
        dialog.add(antwortLabel);
        dialog.add(antwortField);
        dialog.add(new JLabel());
        dialog.add(okButton);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void editQuestion() {
        JDialog dialog = new JDialog((Frame) null, "Frage Ändern", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel indexLabel = new JLabel("Index:");
        JTextField indexField = new JTextField();

        JLabel frageLabel = new JLabel("Neue Frage:");
        JTextField frageField = new JTextField();

        JLabel typLabel = new JLabel("Neuer Typ:");
        JComboBox<String> typBox = new JComboBox<>(new String[]{"String", "Bild", "Integer", "Boolean"});

        JLabel antwortLabel = new JLabel("Neue Antwort:");
        JTextField antwortField = new JTextField();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int index = Integer.parseInt(indexField.getText());
            String frage = frageField.getText();
            String typ = (String) typBox.getSelectedItem();
            String antwort = antwortField.getText();
            // Logik zum Bearbeiten der Frage
            questionDisplay.append("Frage geändert (Index " + index + "): " + frage + " (" + typ + ") -> " + antwort + "\n");
            dialog.dispose();
        });

        dialog.add(indexLabel);
        dialog.add(indexField);
        dialog.add(frageLabel);
        dialog.add(frageField);
        dialog.add(typLabel);
        dialog.add(typBox);
        dialog.add(antwortLabel);
        dialog.add(antwortField);
        dialog.add(new JLabel());
        dialog.add(okButton);

        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public void deleteQuestion() {
        JDialog dialog = new JDialog((Frame) null, "Frage Löschen", true);
        dialog.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel indexLabel = new JLabel("Index der zu löschenden Frage:");
        JTextField indexField = new JTextField();
        inputPanel.add(indexLabel, BorderLayout.NORTH);
        inputPanel.add(indexField, BorderLayout.CENTER);

        JButton okButton = new JButton("Löschen");
        okButton.addActionListener(e -> {
            int index = Integer.parseInt(indexField.getText());
            int confirmation = JOptionPane.showConfirmDialog(dialog, "Sind Sie sicher, dass Sie die Frage löschen möchten?", "Bestätigung", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                // Logik zum Löschen der Frage
                questionDisplay.append("Frage gelöscht (Index " + index + ")\n");
                dialog.dispose();
            }
        });

        dialog.add(inputPanel);
        dialog.add(okButton);

        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}