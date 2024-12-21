package rechtschreibtrainer.view;

import rechtschreibtrainer.controller.TrainerController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FragenpoolPanel extends JPanel {
    private TrainerController tc;
    private JTextArea questionDisplay;
    private JFileChooser fileChooser;

    public FragenpoolPanel(TrainerController tc) {
        this.tc = tc;
        this.setLayout(new GridLayout(1, 1));
        JPanel fullPanel = new JPanel(new BorderLayout(10,10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JToolBar toolBar = new JToolBar();
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
}