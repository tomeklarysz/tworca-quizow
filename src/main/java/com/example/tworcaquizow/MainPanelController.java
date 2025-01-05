package com.example.tworcaquizow;

import Quiz.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainPanelController {
    @FXML
    VBox quizContainer;

    public void initialize() {
        populateQuiz();
    }

    private void populateQuiz() {
        quizContainer.getChildren().clear();

        try {
            Connection conn = QuizyDatabase.connectToDatabase();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizy");

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int id = rs.getInt("id");
                Hyperlink link = new Hyperlink(name);
                link.setOnAction(e -> {
                    Quiz quiz = new Quiz(name);
                    quiz.setDescription(description);
                    quiz.setId(id);
                    openQuiz(quiz);
                });
                quizContainer.getChildren().add(link);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void openQuiz(Quiz quiz) {
        try {
            // mozna pomyslec stworzeniu klasy/interfejsu kontroler,
            // z ktorego inne kontrolery beda mogly dziedziczyc zeby np.
            // wywolac funkcje do ladowania nowego okna, bo to sie powtarza

            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartQuizPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Start Quiz");
            newStage.setScene(new Scene(root));
            newStage.show();

            StartQuizController controller = fxmlLoader.getController();
            controller.setQuiz(quiz);
            controller.setLabel(quiz.getName());
            controller.setDescription(quiz.getDescription());

            // Zamknij bieżące okno
            Stage currentStage = (Stage) quizContainer.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }
}
