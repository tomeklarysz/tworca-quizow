package com.example.tworcaquizow;

import Quiz.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class StartQuizController {

    @FXML Button startQuiz;
    @FXML Button anulujQuiz;
    @FXML Label quizLabel;
    @FXML
    TextArea quizDesc;
    private Quiz quiz;

    public void setLabel(String label) {
        quizLabel.setText(label);
    }
    public void setDescription(String description) {
        quizDesc.setText(description);
    }

    public void quitQuiz(ActionEvent actionEvent) {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) quizLabel.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public void startQuiz(ActionEvent actionEvent) {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("QuizPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Quiz");
            newStage.setScene(new Scene(root));
            newStage.show();

            QuizController controller = fxmlLoader.getController();
            controller.setQuiz(quiz);

            Stage currentStage = (Stage) quizLabel.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

            controller.startQuiz();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
