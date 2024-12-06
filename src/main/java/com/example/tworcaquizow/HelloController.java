package com.example.tworcaquizow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void loginButton(ActionEvent event) {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void registerButton(ActionEvent event) {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}