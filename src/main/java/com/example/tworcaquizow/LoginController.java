package com.example.tworcaquizow;

import Uzytkownik.Uzytkownik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import java.sql.SQLException;

import java.sql.Connection;

public class LoginController {
    String DATABASE_URL = "jdbc:sqlite:C:\\Users\\araba\\IdeaProjects\\tworca-quizow\\src\\main\\databases\\db_login.db";

    @FXML
    private Label statusLabel;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    private Uzytkownik uzytkownik;

    public void loginButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        try (Connection conn = LoginDataBase.connectToDatabase(DATABASE_URL)) {
            if (LoginDataBase.check_account(conn, login, password)) {
                uzytkownik = new Uzytkownik(login, password);
                statusLabel.setText("Zalogowano");
                uzytkownik.uzytkownikZalogowany();
                statusLabel.setTextFill(Color.GREEN);
                loginField.clear();
                passwordField.clear();
            }
            else {
                statusLabel.setText("Nie ma takiego konta");
                uzytkownik.uzytkownikNieZalogowany();
                statusLabel.setTextFill(Color.RED);
                loginField.clear();
                passwordField.clear();
            }
        }
        catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        if (uzytkownik.getStanZalogowania()) {
            try {
                // Załaduj nowe okno
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
                Parent root = fxmlLoader.load();

                // Utwórz nową scenę i okno
                Stage newStage = new Stage();
                newStage.setTitle("Main Panel");
                newStage.setScene(new Scene(root));
                newStage.show();

                // Zamknij bieżące okno
                Stage currentStage = (Stage) loginField.getScene().getWindow(); // Pobierz bieżący Stage
                currentStage.close();
            } catch (IOException e) {
                System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
            }
        }

    }
    @FXML
    public void registerButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        try (Connection conn = LoginDataBase.connectToDatabase(DATABASE_URL)) {
            LoginDataBase.createTableIfNotExists(conn);
            if (LoginDataBase.check_logins(conn, login) == true) {
                statusLabel.setText("Ten login już istnieje");
                statusLabel.setTextFill(Color.RED);
                loginField.clear();
                passwordField.clear();
            }
            else {
                LoginDataBase.addUserData(conn, login, password);
                statusLabel.setText("Konto zostało dodane poprawnie");
                statusLabel.setTextFill(Color.GREEN);
                loginField.clear();
                passwordField.clear();
            }
        }
        catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}