package com.example.tworcaquizow;

import Quiz.Quiz;
import Uzytkownik.Uzytkownik;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizController {

    @FXML public Button buttonA;
    @FXML public Button buttonB;
    @FXML public Button buttonC;
    @FXML public Button buttonD;
    public Button nextQuestionButton;
    public Button finishQuizButton;
    public Label loginField;
    @FXML Label pytanieText;
    Image redXIcon;
    Image greenIcon;
    private Quiz quiz;
    private Uzytkownik uzytkownik;
    private int points;
    private LoginDataBase loginDataBase;
    private QuizDatabase quizDatabase;

    public void initialize() {
        redXIcon = new Image(getClass().getResource("/icons/red-x.png").toExternalForm());
        greenIcon = new Image(getClass().getResource("/icons/green-tick.png").toExternalForm());
    }

    public QuizController() {
        try {
            loginDataBase = new LoginDataBase();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
        }
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void startQuiz() {
        points = 0;
        try {
            int quiz_id = quiz.getId();
            quizDatabase = new QuizDatabase();
            Connection conn = quizDatabase.getConnection();
            String query = "SELECT * FROM questions WHERE quiz_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quiz_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            loadQuestion(rs);
        } catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public void loadQuestion(ResultSet rs) throws SQLException {

        String text = rs.getString("text");
        int question_id = rs.getInt("id");
        pytanieText.setText(text);

        finishQuizButton.setOnAction(event -> {
            finishQuiz();
        });

        nextQuestionButton.setDisable(true);
        if (rs.next()) {
            nextQuestionButton.setDisable(false);
        }
        nextQuestionButton.setOnAction(event -> {
            try {
                loadQuestion(rs);
            } catch (SQLException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        });

        String[] answers = quizDatabase.loadAnswers(question_id, quiz.getId());
        String correctAnswer = answers[0];
        List<String> answersList = Arrays.asList(answers);
        Collections.shuffle(answersList);
        answers = answersList.toArray(new String[answersList.size()]);

//        display answers
        buttonA.setText(answers[0]);
        buttonB.setText(answers[1]);
        buttonC.setText(answers[2]);
        buttonD.setText(answers[3]);

        List<Button> buttons = Arrays.asList(buttonA, buttonB, buttonC, buttonD);
        List<ImageView> icons = new ArrayList<>();
        for (Button button : buttons) {
            button.setGraphic(null);
            ImageView icon = new ImageView(button.getText().equals(correctAnswer) ? greenIcon : redXIcon);
            icon.setFitHeight(16);
            icon.setFitWidth(16);
            icons.add(icon);
        }
        for (Button button : buttons) {
            button.setOnAction(event -> {
                if (button.getText().equals(correctAnswer)) {
                    uzytkownik.ZwiekszLiczbePunktow();
                    points = uzytkownik.getLiczbaPunktow();
                    System.out.println(points);
                }
                for (int i = 0; i < buttons.size(); i++) {
                    buttons.get(i).setGraphic(icons.get(i));
                }
            });
        }
    }

    public void finishQuiz() {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndQuiz.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Quiz");
            newStage.setScene(new Scene(root));
            newStage.show();

            EndQuizController controller = fxmlLoader.getController();
            controller.setGoodbyeAndPoints(points);
            controller.setUzytkownik(uzytkownik);
            controller.ustawLogin(uzytkownik.getLogin());
            uzytkownik.WyzerujLiczbePunktow();
            loginDataBase.updatePoints(uzytkownik.getLogin(), uzytkownik.getHaslo(), points);

            Stage currentStage = (Stage) finishQuizButton.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    //    to sie powtarza we wszystkich kontrolerach
    public void ustawLogin(String login) {
        if (login != null && !login.isEmpty()) {
            loginField.setText(login);
        } else {
            loginField.setText("Nieznany użytkownik");
        }
    }

}
