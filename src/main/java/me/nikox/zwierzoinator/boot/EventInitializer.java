package me.nikox.zwierzoinator.boot;

import me.nikox.zwierzoinator.commands.CommandExecutor;
import me.nikox.zwierzoinator.events.AutoResponseEvent;

public class EventInitializer {

    public static void load() {
        registerEvent(new CommandExecutor());
        registerEvent(new AutoResponseEvent());
    }

    public static void registerEvent(Object event) {
        Bootstrap.getJDA().addEventListener(event);
    }
}
