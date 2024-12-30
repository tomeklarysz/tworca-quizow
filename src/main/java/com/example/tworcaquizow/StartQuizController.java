package com.example.tworcaquizow;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartQuizController {

    @FXML Label quizLabel;
    @FXML TextField quizDesc;

    public void setLabel(String label) {
        quizLabel.setText(label);
    }
}
