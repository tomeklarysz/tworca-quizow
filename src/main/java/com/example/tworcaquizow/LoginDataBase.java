package com.example.tworcaquizow;

import java.sql.*;

public class LoginDataBase {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\araba\\IdeaProjects\\tworca-quizow\\src\\main\\databases\\db_login.db";
    private Connection connection;

    public LoginDataBase() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTableIfNotExists();
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTableIfNotExists() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS uzytkownicy (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                login TEXT NOT NULL,
                password TEXT NOT NULL,
                punkty INTEGER NOT NULL DEFAULT 0
            );
            """;
        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
            System.out.println("Tabela została utworzona (lub już istnieje).");
        } catch (SQLException e) {
            System.out.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }

    public void addUserData(String login, String password) {
        String insertSQL = "INSERT INTO uzytkownicy (login, password, punkty) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setInt(3, 0);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dane zostały dodane pomyślnie.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania danych: " + e.getMessage());
        }
    }

    public boolean check_logins(String login) {
        String query = "SELECT login FROM uzytkownicy";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                String existingLogin = resultSet.getString("login");
                if (existingLogin.equals(login)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas sprawdzania loginów: " + e.getMessage());
        }
        return false;
    }

    public boolean check_account(String login, String password) {
        String query = "SELECT login, password FROM uzytkownicy WHERE login = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas sprawdzania konta: " + e.getMessage());
        }
        return false;
    }

    public void updatePoints(String login, String password, int additionalPoints) {
        String updateSQL = "UPDATE uzytkownicy SET punkty = punkty + ? WHERE login = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, additionalPoints);
            stmt.setString(2, login);
            stmt.setString(3, password);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Liczba punktów została zaktualizowana.");
            } else {
                System.out.println("Nie znaleziono konta o podanym loginie i haśle.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas aktualizacji punktów: " + e.getMessage());
        }
    }

    public int getPointsForUser(String login) {
        String query = "SELECT punkty FROM uzytkownicy WHERE login = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("punkty");
                } else {
                    System.out.println("Nie znaleziono użytkownika o podanym loginie.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania punktów: " + e.getMessage());
        }

        return -1;  // Zwraca -1, jeśli użytkownik nie został znaleziony
    }
}
