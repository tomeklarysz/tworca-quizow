package com.example.tworcaquizow;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class StartQuizController {

    @FXML Label quizLabel;
    @FXML
    TextArea quizDesc;

    public void setLabel(String label) {
        quizLabel.setText(label);
    }
    public void setDescription(String description) {
        quizDesc.setText(description);
    }
}
