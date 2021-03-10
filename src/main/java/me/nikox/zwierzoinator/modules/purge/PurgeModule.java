package me.nikox.zwierzoinator.modules.purge;

import me.nikox.zwierzoinator.boot.CommandInitializer;
import me.nikox.zwierzoinator.modules.DiscordModule;

public class PurgeModule implements DiscordModule {

    @Override
    public void initialize() {
        CommandInitializer.registerCommand(new PurgeCommand());
    }
}
