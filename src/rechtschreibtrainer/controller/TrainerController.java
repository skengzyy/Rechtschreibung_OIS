package rechtschreibtrainer.controller;

import rechtschreibtrainer.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TrainerController implements ActionListener, WindowListener {
    private MenüPanel mp = new MenüPanel(this);
    private MenüFrame mf = new MenüFrame(mp);
    private FragenpoolPanel fragenPanel = new FragenpoolPanel(this);
    private  FragenpoolFrame fragenFrame = new FragenpoolFrame(fragenPanel);
    private QuizPanel  quizPanel = new QuizPanel(this);
    private QuizFrame quizFrame = new QuizFrame(quizPanel);
    public static void main(String[] args) {
        new TrainerController();
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

        } else if(ac.equals("hilfe")) {

        } else if(ac.equals("exit")) {

        }
    }


}
