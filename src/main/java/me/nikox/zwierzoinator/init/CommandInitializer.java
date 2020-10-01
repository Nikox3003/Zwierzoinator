package me.nikox.zwierzoinator.init;

import me.nikox.zwierzoinator.commands.*;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.ship_module.ShipCommand;

import java.util.ArrayList;

import static me.nikox.zwierzoinator.VariableHolder.commandList;

public class CommandInitializer {

    public static void load() {
        commandList = new ArrayList<>();
        registerCommand(new ZwierzeListCommand());
        registerCommand(new ZwierzeCommand());
        registerCommand(new WypuscCommand());
        //registerCommand(new IgrzyskaCommand());
        registerCommand(new AutoResponseCommand());
        registerCommand(new CzasCommand());
        registerCommand(new SzobModeCommand());
        registerCommand(new SayCommand());
        registerCommand(new EmojiCommand());
        registerCommand(new StatsCommand());
        //registerCommand(new ShipCommand());
        registerCommand(new ImposterCommand());
        registerCommand(new ValueCommand());
        registerCommand(new FixRolesCommand());
        registerCommand(new SwitchCommand());
    }


    public static void registerCommand(Command command){
        commandList.add(command);
    }
}
