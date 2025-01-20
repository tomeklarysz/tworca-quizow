package com.example.tworcaquizow;

import Okno.ZmianaOkna;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Uzytkownik.Uzytkownik;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController extends ZmianaOkna {
    private Uzytkownik uzytkownik;

    @FXML
    private Label statusLabel;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    private LoginDataBase loginDataBase;

    public void initialize() {
        try {
            loginDataBase = new LoginDataBase();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
        }
    }
    public void loginButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (loginDataBase.check_account(login, password)) {
            uzytkownik = new Uzytkownik(login, password);
            statusLabel.setText("Zalogowano");
            uzytkownik.Zaloguj();
            statusLabel.setTextFill(Color.GREEN);
            loginField.clear();
            passwordField.clear();
        } else {
            statusLabel.setText("Nie ma takiego konta");
            uzytkownik.Wyloguj();
            statusLabel.setTextFill(Color.RED);
            loginField.clear();
            passwordField.clear();
        }
        ZalogowanieUzytkownika(uzytkownik);
    }

    @FXML
    public void registerButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (loginDataBase.check_logins(login)) {
            statusLabel.setText("Ten login już istnieje");
            statusLabel.setTextFill(Color.RED);
            loginField.clear();
            passwordField.clear();
        }
        else {
            loginDataBase.addUserData(login, password);
            statusLabel.setText("Konto zostało dodane poprawnie");
            uzytkownik = new Uzytkownik(login, password);
            uzytkownik.Zaloguj();
            statusLabel.setTextFill(Color.GREEN);
            loginField.clear();
            passwordField.clear();
        }
        ZalogowanieUzytkownika(uzytkownik);
    }

    public void ZalogowanieUzytkownika(Uzytkownik uzytkownik) {
        if (uzytkownik.getStanZalogowania()) {

            FXMLLoader fxmlLoader = załadujOkno("MainPanel.fxml");

            MainController mainController = fxmlLoader.getController();

            mainController.UstawLogin(uzytkownik.getLogin());
            mainController.DodajUzytkownika(uzytkownik);
            mainController.WyswietlLiczbePunktow();

            Stage currentStage = (Stage) loginField.getScene().getWindow();
            currentStage.close();
        }
    }
}