package com.example.tworcaquizow;

import java.sql.*;

public class QuizyDatabase {
//    public static final String DATABASE_URL = "jdbc:sqlite:/home/karol/IdeaProjects/tworca-quizow/src/main/databases/db_login.db";
    public static final String DATABASE_URL = "jdbc:sqlite:C:/Users/tomek/IdeaProjects/tworca-quizow/src/main/databases/db_login.db";
    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
    public static String[] loadAnswers(int question_id, int quiz_id) throws SQLException {
        Connection connection = QuizyDatabase.connectToDatabase();
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
}
