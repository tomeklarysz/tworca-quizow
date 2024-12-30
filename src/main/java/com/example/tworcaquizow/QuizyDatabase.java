package com.example.tworcaquizow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuizyDatabase {
//    public static final String DATABASE_URL = "jdbc:sqlite:/home/karol/IdeaProjects/tworca-quizow/src/main/databases/db_login.db";
    public static final String DATABASE_URL = "jdbc:sqlite:C:/Users/tomek/IdeaProjects/tworca-quizow/src/main/databases/db_login.db";

    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

}
