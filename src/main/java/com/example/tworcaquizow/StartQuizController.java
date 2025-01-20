package com.example.tworcaquizow;

import Okno.ZmianaOkna;
import Quiz.Quiz;
import Uzytkownik.Uzytkownik;
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

public class StartQuizController extends ZmianaOkna {

    @FXML Button startQuiz;
    @FXML Button anulujQuiz;
    @FXML Label quizLabel;
    @FXML TextArea quizDesc;
    @FXML public Label loginField;
    private Quiz quiz;
    private Uzytkownik uzytkownik;

    public void setLabel(String label) {
        quizLabel.setText(label);
    }
    public void setDescription(String description) {
        quizDesc.setText(description);
    }

    public void quitQuiz(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = załadujOkno("MainPanel.fxml");

        MainController controller = fxmlLoader.getController();
        controller.DodajUzytkownika(uzytkownik);
        controller.UstawLogin(uzytkownik.getLogin());
        controller.WyswietlLiczbePunktow();

        Stage currentStage = (Stage) quizLabel.getScene().getWindow(); // Pobierz bieżący Stage
        currentStage.close();

    }

    public void startQuiz(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = załadujOkno("QuizPanel.fxml");

        QuizController controller = fxmlLoader.getController();
        controller.setQuiz(quiz);
        controller.setUzytkownik(uzytkownik);
        controller.ustawLogin(uzytkownik.getLogin());

        Stage currentStage = (Stage) quizLabel.getScene().getWindow(); // Pobierz bieżący Stage
        currentStage.close();

        controller.startQuiz();

    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
