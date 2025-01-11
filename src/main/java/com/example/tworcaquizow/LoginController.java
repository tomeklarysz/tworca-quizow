package com.example.tworcaquizow;

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

public class LoginController {
    String DATABASE_URL = "jdbc:sqlite:C:\\Users\\araba\\IdeaProjects\\tworca-quizow\\src\\main\\databases\\db_login.db";
    private Uzytkownik uzytkownik;

    @FXML
    private Label statusLabel;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public void loginButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        try (Connection conn = LoginDataBase.connectToDatabase(DATABASE_URL)) {
            if (LoginDataBase.check_account(conn, login, password)) {
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
        } catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        ZalogowanieUzytkownika(uzytkownik);
    }

    @FXML
    public void registerButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        try (Connection conn = LoginDataBase.connectToDatabase(DATABASE_URL)) {
            LoginDataBase.createTableIfNotExists(conn);
            if (LoginDataBase.check_logins(conn, login)) {
                statusLabel.setText("Ten login już istnieje");
                statusLabel.setTextFill(Color.RED);
                loginField.clear();
                passwordField.clear();
            }
            else {
                LoginDataBase.addUserData(conn, login, password);
                statusLabel.setText("Konto zostało dodane poprawnie");
                uzytkownik = new Uzytkownik(login, password);
                uzytkownik.Zaloguj();
                statusLabel.setTextFill(Color.GREEN);
                loginField.clear();
                passwordField.clear();
            }
        }
        catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        ZalogowanieUzytkownika(uzytkownik);
    }

    public void ZalogowanieUzytkownika(Uzytkownik uzytkownik) {
        if (uzytkownik.getStanZalogowania()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
                Parent root = fxmlLoader.load();

                MainController mainController = fxmlLoader.getController();

                mainController.UstawLogin(uzytkownik.getLogin());
                mainController.DodajUzytkownika(uzytkownik);
                mainController.WyswietlLiczbePunktow();

                Stage newStage = new Stage();
                newStage.setTitle("Main Panel");
                newStage.setScene(new Scene(root,800,600));
                newStage.show();

                Stage currentStage = (Stage) loginField.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
            }
        }
    }
}