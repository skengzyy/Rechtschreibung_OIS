package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;
import rechtschreibtrainer.model.Frage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

/**
 * FragenpoolPanel für Fragenpool Verwaltungsdialog
 * @author Daniel Ofosuhene
 */

public class FragenpoolPanel extends JPanel {



    private TrainerController tc;
    private JTextArea questionDisplay;
    private JFileChooser fileChooser;
    private JDialog dialogAddQuestion, dialogEditQuestion, dialogDeleteQuestion;
    //addQuestion attribute

    private JLabel frageLabelA,typLabelA,antwortLabelA, antwortLabel_Edit, typLabel_Edit, frageLabel_Edit, indexLabel_Edit;
    private JTextField indexField_Edit, frageField_Edit, typField_Edit, antwortField_Edit;
    private JTextField indexField_Delete;
    private JTextField frageFieldA,antwortFieldA;
    private final  String[] typenAdd = {"String", "Bild", "Integer", "Boolean"};
    private final JComboBox typBoxA = new JComboBox(typenAdd);


    //editQuestion attribute


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


        loadButton.setActionCommand("load_fragenpool");
        loadButton.addActionListener(tc);
        saveButton.setActionCommand("save_fragenpool");
        saveButton.addActionListener(tc);
        infoButton.setActionCommand("info_fragenpool");
        infoButton.addActionListener(tc);


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

        addButton.setActionCommand("add_Frage_Dialog");
        addButton.addActionListener(tc);

        editButton.setActionCommand("edit_Frage_Dialog");
        editButton.addActionListener(tc);

        deleteButton.setActionCommand("delete_Frage_Dialog");
        deleteButton.addActionListener(tc);


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


    public String saveQuestions() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }

        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile() ;
            System.out.println("Datei gespeichert unter: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        }
        return null;
    }


    public File loadQuestions() {
        File file = null;
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith(".txt");
                }
                @Override
                public String getDescription() {
                    return "Textdateien (*.txt)";
                }
            });
        }

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
             file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine gültige .txt-Datei aus.", "Ungültige Datei", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            JOptionPane.showMessageDialog(this, "Datei erfolgreich ausgewählt: " + file.getAbsolutePath(), "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Ausgewählte Datei: " + file.getAbsolutePath());
        }
        return file;
    }

    public void showInfoDialog() {
        JOptionPane.showMessageDialog(this,
                "Im Fragenpool Verwaltungsdialog können Sie Fragen bearbeiten, hinzufügen, löschen und aus einer Datei laden. \n Laden - Erstes Button oben. \n Speichern - Zweites Button vor Info",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }


    public void addQuestion() {
        JDialog dialog = new JDialog((Frame) null, "Frage Hinzufügen", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        frageLabelA = new JLabel("Frage:");
        frageFieldA = new JTextField();
        typLabelA = new JLabel("Typ:");
        antwortLabelA = new JLabel("Antwort:");
        antwortFieldA = new JTextField();
        typBoxA.setActionCommand("addFrageTyp");
        JButton okButtonA = new JButton("OK");
        okButtonA.setActionCommand("add_Frage_Ok");
        okButtonA.addActionListener(tc);



        dialog.add(frageLabelA);
        dialog.add(frageFieldA);
        dialog.add(typLabelA);
        dialog.add(typBoxA);
        dialog.add(antwortLabelA);
        dialog.add(antwortFieldA);
        dialog.add(new JLabel());
        dialog.add(okButtonA);

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public void editQuestion() {
        dialogEditQuestion = new JDialog((Frame) null, "Frage Ändern", true);
        dialogEditQuestion.setLayout(new GridLayout(5, 2, 10, 10));

        indexLabel_Edit = new JLabel("Nummer:");
        indexField_Edit = new JTextField();
        indexField_Edit.setActionCommand("edit_Frage_index");
        indexField_Edit.addActionListener(tc);

        frageLabel_Edit = new JLabel("Neue Frage:");
        frageField_Edit = new JTextField();
        frageField_Edit.setEnabled(false);

        typLabel_Edit = new JLabel("Typ:");
        typField_Edit = new JTextField();
        typField_Edit.setEnabled(false);


        antwortLabel_Edit = new JLabel("Neue Antwort:");
        antwortField_Edit = new JTextField();


        JButton okButton_Edit = new JButton("OK");
        okButton_Edit.setActionCommand("edit_Frage_Ok");
        okButton_Edit.addActionListener(tc);

        dialogEditQuestion.add(indexLabel_Edit);
        dialogEditQuestion.add(indexField_Edit);
        dialogEditQuestion.add(frageLabel_Edit);
        dialogEditQuestion.add(frageField_Edit);
        dialogEditQuestion.add(typLabel_Edit);
        dialogEditQuestion.add(typField_Edit);
        dialogEditQuestion.add(antwortLabel_Edit);
        dialogEditQuestion.add(antwortField_Edit);
        dialogEditQuestion.add(new JLabel());
        dialogEditQuestion.add(okButton_Edit);

        dialogEditQuestion.setSize(400, 250);
        dialogEditQuestion.setLocationRelativeTo(this);
        dialogEditQuestion.setVisible(true);
    }
    public void deleteQuestion() {
        dialogDeleteQuestion = new JDialog((Frame) null, "Frage Löschen", true);
        dialogDeleteQuestion.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel indexLabel = new JLabel("Nummer der zu löschenden Frage:");
        indexField_Delete = new JTextField();
        inputPanel.add(indexLabel, BorderLayout.NORTH);
        inputPanel.add(indexField_Delete, BorderLayout.CENTER);

        JButton okButton = new JButton("Löschen");
        okButton.setActionCommand("delete_Frage_Ok");
        okButton.addActionListener(tc);

        dialogDeleteQuestion.add(inputPanel);
        dialogDeleteQuestion.add(okButton);

        dialogDeleteQuestion.setSize(400, 150);
        dialogDeleteQuestion.setLocationRelativeTo(this);
        dialogDeleteQuestion.setVisible(true);
    }
    //ADD QUESTION METHODEN

    public String getAddQuestionText() {
        if(frageFieldA.getText() == null) {
            showErrorMessage("add", "Ungueltige Eingabe fúr den Text");
        }
        return frageFieldA.getText();
    }
    public String getAddQuestionTyp() {
        return (String) typBoxA.getSelectedItem();
    }
    public String getAddAntwort() {
        String antwort = antwortFieldA.getText();
        String typ = (String) typBoxA.getSelectedItem();

        if (antwort == null || antwort.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogAddQuestion, "Bitte geben Sie eine Antwort ein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Typprüfung
        if ("Integer".equalsIgnoreCase(typ)) {
            try {
                Integer.parseInt(antwort);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dialogAddQuestion, "Die Antwort muss eine ganze Zahl sein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else if ("Boolean".equalsIgnoreCase(typ)) {
            if (!"true".equalsIgnoreCase(antwort) && !"false".equalsIgnoreCase(antwort)) {
                JOptionPane.showMessageDialog(dialogAddQuestion, "Die Antwort muss 'true' oder 'false' sein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return antwort;
    }

    ////

    // EDIT QUESION METHODEN
    public int getEditIndex() {
        try {
            int index = Integer.parseInt(indexField_Edit.getText());
            if (index < 0) {
                throw new NumberFormatException();
            }
            return index;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogEditQuestion, "Bitte geben Sie einen gültigen Index ein (positive Zahl).", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            return -1; // Ungültiger Index
        }
    }
    public void enableEdit(boolean yes) {
        if(yes) {
            frageField_Edit.setEnabled(true);
        } else {
            frageField_Edit.setEnabled(false);
        }
    }
    public String getEditFrage() {
        String frage = frageField_Edit.getText();
        if (frage == null || frage.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogEditQuestion, "Bitte geben Sie eine gültige neue Frage ein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return frage;
    }

    public void enableAntwortEdit(boolean yes) {
        if(yes) {
            antwortField_Edit.setEnabled(true);
        } else {
            antwortField_Edit.setEnabled(false);
        }
    }
     public String getEditAntwort() {
        String antwort = antwortField_Edit.getText();
        String typ = typField_Edit.getText();

        if (antwort == null || antwort.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogEditQuestion, "Bitte geben Sie eine neue Antwort ein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if ("Integer".equalsIgnoreCase(typ)) {
            try {
                Integer.parseInt(antwort);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dialogEditQuestion, "Die Antwort muss eine ganze Zahl sein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else if ("Boolean".equalsIgnoreCase(typ)) {
            if (!"true".equalsIgnoreCase(antwort) && !"false".equalsIgnoreCase(antwort)) {
                JOptionPane.showMessageDialog(dialogEditQuestion, "Die Antwort muss 'true' oder 'false' sein.", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return antwort;
    }

    public void setEditText(String text) {
        if(text != null) {
            frageField_Edit.setText(text);
        }
    }
    public void setEditTyp(String typ) {
         typField_Edit.setText(typ);
    }
    public void setEditAntwort(String text){
        if(text != null) {
            antwortField_Edit.setText(text);
        }
    }
    public void setEditFrageText(String text) {
        this.frageLabel_Edit.setText(text);
    }

    // LÖSCH QUESTION METHODEN
    // Getter-Methode für Delete-Dialog
    public int getDeleteIndex() {
        try {
            int index = Integer.parseInt(indexField_Delete.getText());
            if (index < 0) {
                throw new NumberFormatException();
            }
            return index - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogDeleteQuestion, "Bitte geben Sie einen gültigen Index ein (positive Zahl).", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            return -1; // Ungültiger Index
        }
    }
    public void addTextToQuestionDisplay(String action) {
        // "Frage hinzugefügt: " + frage + " (" + typ + ") -> " + antwort + "\n"
        // "Frage geändert (Index " + index + "): " + frage + " (" + typ + ") -> " + antwort + "\n"
        // "Frage gelöscht (Index " + index + ")\n"
        if(action != null) {
            questionDisplay.append("\n" + action);
        }
    }
    public void clearQuestionDisplay() {
        if (questionDisplay != null) {
            questionDisplay.setText("");
        }
    }
    public void showErrorMessage(String dialogType, String text) {
        switch (dialogType) {
            case "add" -> JOptionPane.showMessageDialog(dialogAddQuestion, "Eine oder mehrere der Eingaben sind ungültig", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            case "edit" -> JOptionPane.showMessageDialog(dialogEditQuestion, "Eine oder mehrere der Eingaben sind ungültig", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            case "delete" -> JOptionPane.showMessageDialog(dialogDeleteQuestion, "Eine oder mehrere der Eingaben sind ungültig", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            case "null" -> JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void showSuccessMessage(String dialogType, String text) {
        switch (dialogType) {
            case "add" -> JOptionPane.showMessageDialog(dialogAddQuestion, "Frage erfolgreich hinzugefügt", "Success", JOptionPane.INFORMATION_MESSAGE);
            case "edit" -> JOptionPane.showMessageDialog(dialogEditQuestion, text, "Success", JOptionPane.INFORMATION_MESSAGE);
            case "delete" -> JOptionPane.showMessageDialog(dialogDeleteQuestion, "Frage erfolgreich gelöscht", "Success", JOptionPane.INFORMATION_MESSAGE);
            case "null" -> JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }



}