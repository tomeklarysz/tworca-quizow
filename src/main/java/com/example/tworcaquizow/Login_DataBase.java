package com.example.tworcaquizow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login_DataBase {
    // Ścieżka do bazy danych SQLite
    public static final String DATABASE_URL = "jdbc:sqlite:/home/karol/IdeaProjects/tworca-quizow/src/main/databases/db_login.db";

    // Metoda do połączenia z bazą danych
    public static Connection connectToDatabase(String DATABASE_URL) throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    // Tworzenie tabeli, jeśli nie istnieje
    public static void createTableIfNotExists(Connection conn) throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS uzytkownicy (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                login TEXT NOT NULL,
                password TEXT NOT NULL
            );
            """;
        try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.execute();
            System.out.println("Tabela została utworzona (lub już istnieje).");
        } catch (SQLException e) {
            System.out.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }

    // Dodawanie danych użytkownika
    public static void addUserData(Connection conn, String login, String password) {
        String insertSQL = "INSERT INTO uzytkownicy (login, password) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, login);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dane zostały dodane pomyślnie.");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas dodawania danych: " + e.getMessage());
        }
    }

    public static boolean check_logins(Connection conn, String login) {
        String query = "SELECT login FROM uzytkownicy";
        try (PreparedStatement stmt = conn.prepareStatement(query);
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

    public static boolean check_account(Connection conn, String login, String password) {
        String query = "SELECT login, password FROM uzytkownicy WHERE login = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Ustawienie parametrów zapytania
            stmt.setString(1, login);
            stmt.setString(2, password);

            // Wykonanie zapytania
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Sprawdzamy, czy wynik zapytania zawiera dane
                if (resultSet.next()) {
                    // Jeżeli wynik istnieje, to znaczy, że login i hasło się zgadzają
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas sprawdzania konta: " + e.getMessage());
        }
        // Jeśli brak wyników, to login lub hasło są niepoprawne
        return false;
    }
}
