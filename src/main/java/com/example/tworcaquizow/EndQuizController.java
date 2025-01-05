package com.example.tworcaquizow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndQuizController {

    @FXML private Button quitButton;
    @FXML private Label goodbyeText;

    public void initialize() {
        quitButton.setOnAction((ActionEvent event) -> quitQuiz());
    }

    public void setGoodbyeAndPoints(int points) {
        this.goodbyeText.setText("Gratulujemy ukończenia quizu! Zdobyłeś " + points+ " pkt");
    }

    public void quitQuiz() {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) goodbyeText.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }
}
