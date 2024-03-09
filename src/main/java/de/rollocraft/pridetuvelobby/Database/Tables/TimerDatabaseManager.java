package de.rollocraft.pridetuvelobby.Database.Tables;

import de.rollocraft.pridetuvelobby.Objects.Time;

import java.sql.*;


public class TimerDatabaseManager {
    private final Connection connection;

    public TimerDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void createTimerTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS timer (id INTEGER PRIMARY KEY, player VARCHAR(255) NOT NULL, days INTEGER NOT NULL, hours INTEGER NOT NULL, minutes INTEGER NOT NULL, seconds INTEGER NOT NULL)");
        }
    }

    public void saveToDatabase(String playerName, Time time) throws SQLException {
        // Zuerst die alten Werte des Spielers aus der Datenbank abrufen
        String query = "SELECT days, hours, minutes, seconds FROM timer WHERE player = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int oldDays = rs.getInt("days");
                int oldHours = rs.getInt("hours");
                int oldMinutes = rs.getInt("minutes");
                int oldSeconds = rs.getInt("seconds");

                // Die neuen Werte addieren
                int newDays = oldDays + time.getDays();
                int newHours = oldHours + time.getHours();
                int newMinutes = oldMinutes + time.getMinutes();
                int newSeconds = oldSeconds + time.getSeconds();

                // Die Summen der Zeitwerte korrigieren, falls sie überlaufen
                if (newSeconds >= 60) {
                    newMinutes += newSeconds / 60;
                    newSeconds %= 60;
                }
                if (newMinutes >= 60) {
                    newHours += newMinutes / 60;
                    newMinutes %= 60;
                }
                if (newHours >= 24) {
                    newDays += newHours / 24;
                    newHours %= 24;
                }

                // Die aktualisierten Werte in die Datenbank speichern
                query = "UPDATE timer SET days = ?, hours = ?, minutes = ?, seconds = ? WHERE player = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(query)) {
                    updateStmt.setInt(1, newDays);
                    updateStmt.setInt(2, newHours);
                    updateStmt.setInt(3, newMinutes);
                    updateStmt.setInt(4, newSeconds);
                    updateStmt.setString(5, playerName);
                    updateStmt.executeUpdate();
                }
            } else {
                // Spieler nicht in der Datenbank gefunden, einen neuen Eintrag erstellen
                query = "INSERT INTO timer (player, days, hours, minutes, seconds) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(query)) {
                    insertStmt.setString(1, playerName);
                    insertStmt.setInt(2, time.getDays());
                    insertStmt.setInt(3, time.getHours());
                    insertStmt.setInt(4, time.getMinutes());
                    insertStmt.setInt(5, time.getSeconds());
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}

