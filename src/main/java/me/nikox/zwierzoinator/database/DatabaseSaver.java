package me.nikox.zwierzoinator.database;

import com.zaxxer.hikari.HikariDataSource;
import me.nikox.zwierzoinator.init.DatabaseInitializer;
import me.nikox.zwierzoinator.objects.Entry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSaver {

    public static void saveNewEntry(Entry e) throws SQLException {
        int id = 0;
        e.setEntryId(id);
        HikariDataSource source = DatabaseInitializer.getDataSource();
        Connection conn = source.getConnection();
        Statement stat = conn.createStatement();
        ResultSet set = stat.executeQuery("SELECT entry_id FROM zwierzoinator_entries");
        while(set.next()){
            id++;
        }
        e.setEntryId(id);
        stat.execute("INSERT INTO zwierzoinator_entries (entry_id, punished_at, punished_to, punished_id, executor_id, is_active, is_accepted, reason, entry_message) VALUES (" +
                id + ", " + e.getPunishedAt() + ", -1, " + e.getPunishedId() + ", " + e.getExecutorId() + ", " + e.isActive() + ", " + e.isAccepted() + ", \"" + e.getReason() + "\", " + e.getEntryMessage().getIdLong() + ")");
        stat.close();
        DatabaseInitializer.getDataSource().close();
    }

    public static void saveEntry(Entry e) throws SQLException{
        HikariDataSource source = DatabaseInitializer.getDataSource();
        Connection conn = source.getConnection();
        Statement stat = conn.createStatement();
        stat.execute("INSERT INTO zwierzoinator_entries (entry_id, punished_at, punished_to, punished_id, executor_id, is_active, is_accepted, reason, entry_message) VALUES (" +
                e.getEntryId() + ", " + e.getPunishedAt() + ", " + e.getPunishedTo() + e.getPunishedId() + ", " + e.getExecutorId() + ", " + e.isActive() + ", " + e.isAccepted() + ", \"" + e.getReason() + "\", " + e.getEntryMessage().getIdLong() + ")");
    }
}
