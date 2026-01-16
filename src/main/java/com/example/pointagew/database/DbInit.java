package com.example.pointagew.database;

import java.sql.Connection;
import java.sql.Statement;

public final class DbInit {
    private DbInit() {}

    public static void init() {
        String sql = """
    PRAGMA foreign_keys = ON;

    CREATE TABLE IF NOT EXISTS benevole (
      id_benevole INTEGER PRIMARY KEY,
      nom         TEXT NOT NULL,
      prenom      TEXT NOT NULL,
      actif       INTEGER NOT NULL DEFAULT 1 CHECK (actif IN (0,1)),
      CHECK (id_benevole BETWEEN 0 AND 9999)
    );

    CREATE TABLE IF NOT EXISTS pointage (
      id_pointage INTEGER PRIMARY KEY AUTOINCREMENT,
      id_benevole INTEGER NOT NULL,
      jour        TEXT NOT NULL,
      moment      TEXT NOT NULL CHECK (moment IN ('MATIN','APRES_MIDI')),
      repas       INTEGER NOT NULL CHECK (repas IN (0,1)),
      ts          TEXT NOT NULL,
      FOREIGN KEY (id_benevole) REFERENCES benevole(id_benevole) ON DELETE CASCADE
    );

    CREATE UNIQUE INDEX IF NOT EXISTS ux_pointage_jour_moment
    ON pointage(id_benevole, jour, moment);

    -- BENEVOLES
    INSERT OR IGNORE INTO benevole VALUES (1234, 'Dupont', 'Lina', 1);
    INSERT OR IGNORE INTO benevole VALUES (4321, 'Martin', 'Yanis', 1);
    INSERT OR IGNORE INTO benevole VALUES (1111, 'Durand', 'Nina', 1);
    INSERT OR IGNORE INTO benevole VALUES (2222, 'Haddad', 'Karim', 1);
    INSERT OR IGNORE INTO benevole VALUES (3333, 'Nguyen', 'Bao', 1);

    """;

        try (Connection c = DataBase.getConnection();
             Statement st = c.createStatement()) {

            for (String part : sql.split(";")) {
                String s = part.trim();
                if (!s.isEmpty()) st.execute(s);
            }
        } catch (Exception e) {
            throw new RuntimeException("Init DB échouée", e);
        }
    }
}
