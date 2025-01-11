package com.example.tworcaquizow;

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

public class DodanieNazwyQuizuController {
    public Label LoginField;
    public Button WyjdzButtonField;
    public Button WylogujButtonField;
    public Uzytkownik ZalogowanyUzytkownik;
    public Button DalejButtonField;
    public TextField NazwaQuizuField;

    public void WyjdzButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
            Parent root = fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();
            mainController.UstawLogin(ZalogowanyUzytkownik.getLogin());

            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();
            Stage currentStage = (Stage) WylogujButtonField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    public void WylogujButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPanel.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();
            Stage currentStage = (Stage) WylogujButtonField.getScene().getWindow();
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

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DodanieQuizPanel.fxml"));
                Parent root = fxmlLoader.load();

                DodanieQuizuController dodanieQuizuController = fxmlLoader.getController();

                dodanieQuizuController.setCurrentQuizId(quizId);

                dodanieQuizuController.UstawLogin(ZalogowanyUzytkownik.getLogin());
                dodanieQuizuController.DodajUzytkownika(ZalogowanyUzytkownik);

                Stage newStage = new Stage();
                newStage.setTitle("Dodawanie Pytań");
                newStage.setScene(new Scene(root, 800, 600));
                newStage.show();

                Stage currentStage = (Stage) WylogujButtonField.getScene().getWindow();
                currentStage.close();
            } else {
                System.out.println("Wystąpił problem podczas dodawania quizu do bazy danych.");
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Błąd bazy danych: " + e.getMessage());
        }
    }
}
