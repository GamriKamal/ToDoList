package com.example.final_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5433/todo_list";
    private static final String username = "postgres";
    private static final String password = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
