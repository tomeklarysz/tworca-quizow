package com.example.tworcaquizow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
    @FXML

    protected void loginButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        try (Connection conn = LoginDataBase.connectToDatabase(DATABASE_URL)) {
            if (LoginDataBase.check_account(conn, login, password)) {
                statusLabel.setText("Zalogowano");
                statusLabel.setTextFill(Color.GREEN);
                loginField.clear();
                passwordField.clear();
            }
            else {
                statusLabel.setText("Nie ma takiego konta");
                statusLabel.setTextFill(Color.RED);
                loginField.clear();
                passwordField.clear();
            }
        }
        catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
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