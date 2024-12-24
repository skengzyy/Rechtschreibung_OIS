package rechtschreibtrainer.controller;

import rechtschreibtrainer.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TrainerController implements ActionListener {
    private MenüPanel mp = new MenüPanel(this);
    private MenüFrame mf = new MenüFrame(mp);
    private FragenpoolPanel fragenPanel = new FragenpoolPanel(this);
    private FragenpoolFrame fragenFrame = new FragenpoolFrame(fragenPanel);
    private QuizPanel  quizPanel = new QuizPanel(this);
    private QuizFrame quizFrame = new QuizFrame(quizPanel);
    private SpielPanel spielPanel = new SpielPanel(this);
    private SpielFrame spielFrame = new SpielFrame(spielPanel);

    public static void main(String[] args) {
        new TrainerController();
    }

    public TrainerController() {
        fragenFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                fragenFrame.setVisible(false);
                mf.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        // WindowListener für QuizFrame hinzufügen
        quizFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                // MenüFrame wieder sichtbar machen
                quizFrame.setVisible(false);
                mf.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        if(ac.equals("verwalten")) {
            mf.setVisible(false);
            fragenFrame.setVisible(true);
        } else if(ac.equals("quizmode")) {
            mf.setVisible(false);
            quizFrame.setVisible(true);

        } else if(ac.equals("spielmode")) {
            mf.setVisible(false);
            spielFrame.setVisible(true);

        } else if(ac.equals("hilfe")) {


        } else if(ac.equals("exit")) {
            System.exit(0);
        }
        else if(ac.equals("exitQuizPage")){
            quizFrame.setVisible(false);
            mf.setVisible(true);
        }
    }


}
