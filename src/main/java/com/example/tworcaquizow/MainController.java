package com.example.tworcaquizow;

import Uzytkownik.Uzytkownik;
import javafx.event.ActionEvent;
import Quiz.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class MainController {
    @FXML
    public TextField LiczbaPunktowField;
    @FXML
    private Button WylogujButton;
    @FXML
    private Button DodajQuizButton;
    @FXML
    private Label LoginField;
    @FXML VBox quizContainer;
    private Uzytkownik ZalogowanyUzytkownik;

    public void initialize() {
        populateQuiz();
    }

    private void populateQuiz() {
        quizContainer.getChildren().clear();

        try {
            Connection conn = QuizyDatabase.connectToDatabase();
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
        try {
            // mozna pomyslec stworzeniu klasy/interfejsu kontroler,
            // z ktorego inne kontrolery beda mogly dziedziczyc zeby np.
            // wywolac funkcje do ladowania nowego okna, bo to sie powtarza

            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartQuizPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Start Quiz");
            newStage.setScene(new Scene(root));
            newStage.show();

            StartQuizController controller = fxmlLoader.getController();
            controller.setQuiz(quiz);
            controller.setLabel(quiz.getName());
            controller.setDescription(quiz.getDescription());

            // Zamknij bieżące okno
            Stage currentStage = (Stage) quizContainer.getScene().getWindow(); // Pobierz bieżący Stage
            currentStage.close();

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DodanieNazwyQuizuPanel.fxml"));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Main Panel");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();

            DodanieNazwyQuizuController dodanieNazwyQuizuController = fxmlLoader.getController();
            dodanieNazwyQuizuController.UstawLogin(ZalogowanyUzytkownik.getLogin());
            dodanieNazwyQuizuController.DodajUzytkownika(ZalogowanyUzytkownik);

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
