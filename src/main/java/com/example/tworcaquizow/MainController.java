package com.example.tworcaquizow;

import Uzytkownik.Uzytkownik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicLabelUI;
import java.io.IOException;

import java.sql.SQLException;

import java.sql.Connection;

public class MainController {
    @FXML
    public TextField LiczbaPunktowField;
    @FXML
    private Button WylogujButton;
    @FXML
    private Button DodajQuizButton;
    @FXML
    private Label LoginField;

    private Uzytkownik ZalogowanyUzytkownik;

    public void WylogujButton(ActionEvent actionEvent) {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();

            // Zamknij bieżące okno
            Stage currentStage = (Stage) DodajQuizButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public void DodajQuizButton(ActionEvent actionEvent) {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DodanieQuizPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();

            // Zamknij bieżące okno
            Stage currentStage = (Stage) DodajQuizButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public void UstawLogin(String login) {
        if (login != null && !login.isEmpty()) {
            LoginField.setText(login);
        } else {
            LoginField.setText("Nieznany użytkownik");
        }
    }

    public void WyswietlLiczbePunktow() {
        LiczbaPunktowField.setText(String.valueOf(ZalogowanyUzytkownik.getLiczbaPunktow()));
    }

    public void DodajUzytkownika(Uzytkownik uzytkownik) {
        ZalogowanyUzytkownik = uzytkownik;
    }
}
