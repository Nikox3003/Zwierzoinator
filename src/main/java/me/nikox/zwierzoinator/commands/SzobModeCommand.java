package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SzobModeCommand extends Command {

    public SzobModeCommand() {
        super(SzobModeCommand.class, "sz00b", Permission.ADMINISTRATOR, "sz00b");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        if(VariableHolder.szobMode){
            VariableHolder.szobMode = false;
            e.getChannel().sendMessage("Sz00b mode wyłączony :(").queue();
        } else{
            e.getChannel().sendMessage("Sz00b mode włączony :))").queue();
            VariableHolder.szobMode = true;
        }
    }
}
