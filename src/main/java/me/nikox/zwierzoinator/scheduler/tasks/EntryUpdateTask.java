package me.nikox.zwierzoinator.scheduler.tasks;

import lombok.SneakyThrows;
import me.nikox.zwierzoinator.init.DatabaseInitializer;

import java.sql.Statement;

public class EntryUpdateTask implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        Statement stat = DatabaseInitializer.getDataSource().getConnection().createStatement();
        DatabaseInitializer.loadEntries(stat);
    }
}
