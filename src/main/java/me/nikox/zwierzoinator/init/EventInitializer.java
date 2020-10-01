package me.nikox.zwierzoinator.init;

import me.nikox.zwierzoinator.commands.CommandExecutor;
import me.nikox.zwierzoinator.events.AutoResponseEvent;
import me.nikox.zwierzoinator.events.SzobModeEvent;

public class EventInitializer {

    public static void load() {
        Bootstrap.getJDA().addEventListener(new CommandExecutor());
        Bootstrap.getJDA().addEventListener(new AutoResponseEvent());
        Bootstrap.getJDA().addEventListener(new SzobModeEvent());
    }
}
