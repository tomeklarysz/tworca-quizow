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
            CREATE TABLE IF NOT EXISTS quiz (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT
            );
        """;

        String createQuestionsTable = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                quiz_id INTEGER,
                question_text TEXT,
                FOREIGN KEY (quiz_id) REFERENCES quiz(id)
            );
        """;

        String createAnswersTable = """
            CREATE TABLE IF NOT EXISTS answers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question_id INTEGER,
                answer_text TEXT,
                FOREIGN KEY (question_id) REFERENCES questions(id)
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createQuizTable);
            stmt.execute(createQuestionsTable);
            stmt.execute(createAnswersTable);
        }
    }

    public int addQuiz(String quizName) throws SQLException {
        String sql = "INSERT INTO quiz (name) VALUES (?)";
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
        String sql = "INSERT INTO questions (quiz_id, question_text) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, quizId);
            pstmt.setString(2, questionText);
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public void addAnswer(int questionId, String answerText) throws SQLException {
        String sql = "INSERT INTO answers (question_id, answer_text) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.setString(2, answerText);
            pstmt.executeUpdate();
        }
    }

    public void getQuestionsAndAnswersForQuiz(int quizId) throws SQLException {
        String sql = """
            SELECT q.question_text, a.answer_text
            FROM questions q
            LEFT JOIN answers a ON q.id = a.question_id
            WHERE q.quiz_id = ?
            ORDER BY q.id, a.id
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String questionText = rs.getString("question_text");
                    String answerText = rs.getString("answer_text");
                    System.out.println("Question: " + questionText + " | Answer: " + answerText);
                }
            }
        }
    }

    public void listAllQuizzes() throws SQLException {
        String sql = "SELECT * FROM quiz";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("Quiz ID: " + id + ", Name: " + name);
            }
        }
    }
}
