package com.example.tworcaquizow;

import java.sql.*;

public class QuizDatabase {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\araba\\IdeaProjects\\tworca-quizow\\src\\main\\databases\\quiz.db";
    private Connection connection;

    public QuizDatabase() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTables();
    }

    private void createTables() throws SQLException {
        String createQuizTable = """
            CREATE TABLE IF NOT EXISTS quizy (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT
            );
        """;

        String createQuestionsTable = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                text TEXT NOT NULL,
                quiz_id INTEGER,
                FOREIGN KEY (quiz_id) REFERENCES quizy(id)
            );
        """;

        String createAnswersTable = """
            CREATE TABLE IF NOT EXISTS answers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                text TEXT NOT NULL,
                question_id INTEGER,
                quiz_id INTEGER,
                FOREIGN KEY (question_id) REFERENCES questions(id)
                FOREIGN KEY (quiz_id) REFERENCES quizy(id)
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createQuizTable);
            stmt.execute(createQuestionsTable);
            stmt.execute(createAnswersTable);
        }
    }

    public int addQuiz(String quizName) throws SQLException {
        String sql = "INSERT INTO quizy (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, quizName);
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

        public int addQuestion(int quizId, String questionText) throws SQLException {
        String sql = "INSERT INTO questions (text, quiz_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, questionText);
            pstmt.setInt(2, quizId);
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public void addAnswer(int quizId, int questionId, String answerText) throws SQLException {
        String sql = "INSERT INTO answers (text, question_id, quiz_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, answerText);
            pstmt.setInt(2, questionId);
            pstmt.setInt(3, quizId);
            pstmt.executeUpdate();
        }
    }

    public String[] loadAnswers(int question_id, int quiz_id) throws SQLException {
        String query = "SELECT * FROM answers WHERE question_id = ? AND quiz_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, question_id);
        preparedStatement.setInt(2, quiz_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String[] answers = new String[4];
        int counter = 0;
        while (resultSet.next()) {
            answers[counter] = resultSet.getString("text");
            counter++;
        }
        return answers;

    }

    public Connection getConnection() {
        return connection;
    }
}
