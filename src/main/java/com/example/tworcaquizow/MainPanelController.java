package com.example.tworcaquizow;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.sql.*;

public class MainPanelController {
    @FXML
    VBox quizContainer;

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
                Hyperlink link = new Hyperlink(name);
                link.setOnAction(e -> {
//                    tu bedzie metoda do wlaczenia quizu
                });
                quizContainer.getChildren().add(link);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}
