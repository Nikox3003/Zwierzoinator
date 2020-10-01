package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CalcCommand extends Command {

    public CalcCommand() {
        super(CalcCommand.class, "calc", Permission.ADMINISTRATOR, "");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {

    }
}
