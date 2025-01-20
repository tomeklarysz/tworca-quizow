package com.example.tworcaquizow;

import Okno.ZmianaOkna;
import Uzytkownik.Uzytkownik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndQuizController extends ZmianaOkna {

    @FXML public Label loginField;
    @FXML private Button quitButton;
    @FXML private Label goodbyeText;
    private Uzytkownik uzytkownik;

    public void initialize() {
        quitButton.setOnAction((ActionEvent event) -> quitQuiz());
    }

    public void setGoodbyeAndPoints(int points) {
        this.goodbyeText.setText("Gratulujemy ukończenia quizu! Zdobyłeś " + points+ " pkt");
    }

    public void quitQuiz() {

        FXMLLoader fxmlLoader = załadujOkno("MainPanel.fxml");

        MainController controller = fxmlLoader.getController();
        controller.UstawLogin(uzytkownik.getLogin());
        controller.DodajUzytkownika(uzytkownik);
        controller.WyswietlLiczbePunktow();

        Stage currentStage = (Stage) goodbyeText.getScene().getWindow(); // Pobierz bieżący Stage
        currentStage.close();

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
