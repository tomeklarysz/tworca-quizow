package com.example.tworcaquizow;

import Okno.ZmianaOkna;
import Uzytkownik.Uzytkownik;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DodanieQuizuController extends ZmianaOkna {
    public Button WyjdzButtonField;
    public Label LoginField;
    public Button DodajButtonField;
    public TextField PoprawnaOdpowiedzField;
    public TextField ZlaOdpowiedz1Field;
    public TextField ZlaOdpowiedz2Field;
    public TextField ZlaOdpowiedz3Field;
    public TextField PytanieField;
    private Uzytkownik ZalogowanyUzytkownik;
    private QuizDatabase quizDatabase;
    private int currentQuizId;

    public void initialize() {
        try {
            quizDatabase = new QuizDatabase();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
        }
    }

    public void WyjdzButton(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = załadujOkno("MainPanel.fxml");

        MainController mainController = fxmlLoader.getController();
        mainController.UstawLogin(ZalogowanyUzytkownik.getLogin());
        mainController.DodajUzytkownika(ZalogowanyUzytkownik);
        mainController.WyswietlLiczbePunktow();

        Stage currentStage = (Stage) WyjdzButtonField.getScene().getWindow();
        currentStage.close();
    }

    public void UstawLogin(String login) {
        if (login != null && !login.isEmpty()) {
            LoginField.setText(login);
        } else {
            LoginField.setText("Nieznany użytkownik");
        }
    }

    public void DodajUzytkownika(Uzytkownik uzytkownik) {
        ZalogowanyUzytkownik = uzytkownik;
    }

    public void DodajButton(ActionEvent actionEvent) {
        try {
            String pytanie = PytanieField.getText().trim();
            String poprawnaOdpowiedz = PoprawnaOdpowiedzField.getText().trim();
            String zlaOdpowiedz1 = ZlaOdpowiedz1Field.getText().trim();
            String zlaOdpowiedz2 = ZlaOdpowiedz2Field.getText().trim();
            String zlaOdpowiedz3 = ZlaOdpowiedz3Field.getText().trim();

            if (pytanie.isEmpty() || poprawnaOdpowiedz.isEmpty() || zlaOdpowiedz1.isEmpty() || zlaOdpowiedz2.isEmpty() || zlaOdpowiedz3.isEmpty()) {
                System.out.println("Wszystkie pola muszą być wypełnione!");
                return;
            }

            int questionId = quizDatabase.addQuestion(currentQuizId, pytanie);
            if (questionId != -1) {
                quizDatabase.addAnswer(currentQuizId, questionId, poprawnaOdpowiedz);
                quizDatabase.addAnswer(currentQuizId, questionId, zlaOdpowiedz1);
                quizDatabase.addAnswer(currentQuizId, questionId, zlaOdpowiedz2);
                quizDatabase.addAnswer(currentQuizId, questionId, zlaOdpowiedz3);

                System.out.println("Dodano pytanie i odpowiedzi do bazy danych.");
            } else {
                System.out.println("Nie udało się dodać pytania do bazy danych.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania pytania lub odpowiedzi: " + e.getMessage());
        }
        PytanieField.clear();
        PoprawnaOdpowiedzField.clear();
        ZlaOdpowiedz1Field.clear();
        ZlaOdpowiedz2Field.clear();
        ZlaOdpowiedz3Field.clear();
    }

    public void setCurrentQuizId(int quizId) {
        this.currentQuizId = quizId;
    }
}
