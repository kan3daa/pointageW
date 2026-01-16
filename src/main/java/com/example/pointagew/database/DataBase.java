package com.example.pointagew.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public final class DataBase {
    private static final Path DB_PATH =
            Path.of(System.getProperty("user.dir"), "data", "pointage.db");

    private static final String URL = "jdbc:sqlite:" + DB_PATH.toString();

    private DataBase() {}

    public static Connection getConnection() throws SQLException {
        try {
            Files.createDirectories(DB_PATH.getParent());
        } catch (Exception e) {
            throw new SQLException("Impossible de créer le dossier DB: " + DB_PATH.getParent(), e);
        }

        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

}
