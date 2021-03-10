package me.nikox.zwierzoinator.modules.escape_logging;

import me.nikox.zwierzoinator.modules.DiscordModule;
import me.nikox.zwierzoinator.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;

public class EscapeLoggingModule implements DiscordModule {

    @Override
    public void initialize() {
        Scheduler.scheduleRepeatingTask(new EscapeUpdateTask(), 1, 1, TimeUnit.MINUTES);

    }
}
