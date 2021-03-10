package me.nikox.zwierzoinator.boot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static HikariConfig hikariConfig;
    private static HikariDataSource dataSource;

    public static void load() throws SQLException {
        hikariConfig = new HikariConfig();
        String jdbcUrl = FileInitializer.getConfig().getProperty("database.url");
        String username = FileInitializer.getConfig().getProperty("database.username");
        String password = FileInitializer.getConfig().getProperty("database.password");
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.addDataSourceProperty("useLocalSessionState", true);
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", true);
        hikariConfig.addDataSourceProperty("maintainTimeStats", false);
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", true);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", true);
        hikariConfig.addDataSourceProperty("connectionTimeout", 31000);
        dataSource = new HikariDataSource(hikariConfig);
        Statement statement = dataSource.getConnection().createStatement();
        createStructure(statement);
        loadEntries(statement);
        statement.close();
    }

    private static void createStructure(Statement stat) throws SQLException {
        stat.execute("CREATE TABLE IF NOT EXISTS zwierzoinator_entries (entry_id INT, punished_at BIGINT, punished_to BIGINT, punished_id BIGINT, executor_id BIGINT, is_active BOOLEAN, is_accepted BOOLEAN, reason VARCHAR(1990), entry_message BIGINT)");
        stat.execute("CREATE TABLE IF NOT EXISTS zwierzoinator_escapes (entry_id INT, escaped_at BIGINT, punish_time BIGINT, escaper_id BIGINT, has_returned BOOLEAN, is_banned BOOLEAN)");
    }

    public static void loadEntries(Statement stat) throws SQLException {
        ResultSet r = stat.executeQuery("SELECT * FROM zwierzoinator_entries");
        VariableHolder.entryList.clear();
        while(r.next()) {
            Entry entry = Entry.builder()
                    .entryId(r.getInt(1))
                    .punishedAt(r.getLong(2))
                    .punishedTo(r.getLong(3))
                    .punishedId(r.getLong(4))
                    .executorId(r.getLong(5))
                    .isActive(r.getBoolean(6))
                    .isAccepted(r.getBoolean(7))
                    .reason(r.getString(8))
                    .build();
            VariableHolder.entryList.add(entry);
        }
    }

    public static HikariDataSource getDataSource() {
        if(dataSource.isClosed()) {
            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }

    public static HikariConfig getHikariConfig() {
        return hikariConfig;
    }
}
