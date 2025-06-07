package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton-клас для отримання JDBC-з’єднання з базою media_request_db.
 */
public class DatabaseConnection {
    private static final String URL =
        "jdbc:mysql://localhost:3306/media_request_db"
      + "?allowPublicKeyRetrieval=true"
      + "&useSSL=false"
      + "&serverTimezone=UTC";
    private static final String USER = "root";                   // MySQL користувач
    private static final String PASS = "your_password_here";     // Пароль

    private static Connection connection;

    private DatabaseConnection() { }

    /**
     * Повертає активне з’єднання або створює нове, якщо ще не існувало.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // allowPublicKeyRetrieval=true необхідний для MySQL 8+ з caching_sha2_password
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}