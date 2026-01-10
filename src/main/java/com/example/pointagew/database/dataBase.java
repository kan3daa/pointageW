package com.example.pointagew.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class dataBase {
    private static final Path DB_PATH = Path.of("data", "pointage.db");
    private static final String URL = "jdbc:sqlite:" + DB_PATH.toString();

    private dataBase() {}

    public static Connection getConnection() throws SQLException {
        try {
            Files.createDirectories(DB_PATH.getParent());
        } catch (Exception e) {
            throw new SQLException("Impossible de créer le dossier DB: " + DB_PATH.getParent(), e);
        }
        return DriverManager.getConnection(URL);
    }
}
