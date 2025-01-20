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

public class DodanieNazwyQuizuController extends ZmianaOkna {
    public Label LoginField;
    public Button WyjdzButtonField;
    public Uzytkownik ZalogowanyUzytkownik;
    public Button DalejButtonField;
    public TextField NazwaQuizuField;

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

    public void DalejButton(ActionEvent actionEvent) {
        String nazwaQuizu = NazwaQuizuField.getText();

        if (nazwaQuizu == null || nazwaQuizu.trim().isEmpty()) {
            System.out.println("Nazwa quizu nie może być pusta.");
            return;
        }

        try {
            QuizDatabase quizDatabase = new QuizDatabase();
            int quizId = quizDatabase.addQuiz(nazwaQuizu);

            if (quizId > 0) {
                System.out.println("Dodano quiz o nazwie: " + nazwaQuizu + ", ID: " + quizId);

                FXMLLoader fxmlLoader = załadujOkno("DodanieQuizPanel.fxml");

                DodanieQuizuController dodanieQuizuController = fxmlLoader.getController();
                dodanieQuizuController.setCurrentQuizId(quizId);
                dodanieQuizuController.UstawLogin(ZalogowanyUzytkownik.getLogin());
                dodanieQuizuController.DodajUzytkownika(ZalogowanyUzytkownik);

                Stage currentStage = (Stage) WyjdzButtonField.getScene().getWindow();
                currentStage.close();
            } else {
                System.out.println("Wystąpił problem podczas dodawania quizu do bazy danych.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd bazy danych: " + e.getMessage());
        }
    }
}
