package com.example.tworcaquizow;

import Okno.ZmianaOkna;
import Uzytkownik.Uzytkownik;
import javafx.application.Application;
import javafx.event.ActionEvent;
import Quiz.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class MainController extends ZmianaOkna {
    @FXML
    public TextField LiczbaPunktowField;
    @FXML
    public ListView rankingListView;
    @FXML
    private Button DodajQuizButton;
    @FXML
    private Label LoginField;
    @FXML
    private VBox quizContainer;
    private Uzytkownik ZalogowanyUzytkownik;
    private LoginDataBase loginDataBase;
    private QuizDatabase quizDatabase;

    public void initialize() {
        populateQuiz();
        try {
            loginDataBase = new LoginDataBase();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych: " + e.getMessage());
        }

        if (loginDataBase != null) {
            try {
                String query = "SELECT login, punkty FROM uzytkownicy ORDER BY punkty DESC";
                PreparedStatement stmt = loginDataBase.getConnection().prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    int punkty = resultSet.getInt("punkty");

                    String rankingEntry = login + " - " + punkty + " pkt";
                    rankingListView.getItems().add(rankingEntry);
                }
            } catch (SQLException e) {
                System.out.println("Błąd podczas pobierania danych: " + e.getMessage());
            }
        }
    }

    private void populateQuiz() {
        quizContainer.getChildren().clear();

        try {
            quizDatabase = new QuizDatabase();
            Connection conn = quizDatabase.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizy");

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int id = rs.getInt("id");
                Hyperlink link = new Hyperlink(name);
                link.setOnAction(e -> {
                    Quiz quiz = new Quiz(name);
                    quiz.setDescription(description);
                    quiz.setId(id);
                    openQuiz(quiz);
                });
                quizContainer.getChildren().add(link);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void openQuiz(Quiz quiz) {

        FXMLLoader fxmlLoader = załadujOkno("StartQuizPanel.fxml");

        StartQuizController controller = fxmlLoader.getController();
        controller.setQuiz(quiz);
        controller.setLabel(quiz.getName());
        controller.setDescription(quiz.getDescription());
        controller.setUzytkownik(ZalogowanyUzytkownik);
        controller.ustawLogin(ZalogowanyUzytkownik.getLogin());

        // Zamknij bieżące okno
        Stage currentStage = (Stage) quizContainer.getScene().getWindow(); // Pobierz bieżący Stage
        currentStage.close();

    }

    public void WylogujButton(ActionEvent actionEvent) {

        załadujOkno("LoginPanel.fxml");

        // Zamknij bieżące okno
        Stage currentStage = (Stage) DodajQuizButton.getScene().getWindow();
        currentStage.close();
    }

    public void DodajQuizButton(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = załadujOkno("DodanieNazwyQuizuPanel.fxml");

        DodanieNazwyQuizuController dodanieNazwyQuizuController = fxmlLoader.getController();
        dodanieNazwyQuizuController.UstawLogin(ZalogowanyUzytkownik.getLogin());
        dodanieNazwyQuizuController.DodajUzytkownika(ZalogowanyUzytkownik);

        // Zamknij bieżące okno
        Stage currentStage = (Stage) DodajQuizButton.getScene().getWindow();
        currentStage.close();
    }

    public void UstawLogin(String login) {
        if (login != null && !login.isEmpty()) {
            LoginField.setText(login);
        } else {
            LoginField.setText("Nieznany użytkownik");
        }
    }

    public void WyswietlLiczbePunktow() {
        LiczbaPunktowField.setText(String.valueOf(loginDataBase.getPointsForUser(ZalogowanyUzytkownik.getLogin())));
    }

    public void DodajUzytkownika(Uzytkownik uzytkownik) {
        ZalogowanyUzytkownik = uzytkownik;
    }


}
