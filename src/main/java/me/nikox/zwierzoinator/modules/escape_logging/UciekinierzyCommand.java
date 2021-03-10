package me.nikox.zwierzoinator.modules.escape_logging;

import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UciekinierzyCommand extends Command {

    public UciekinierzyCommand() {
        super(UciekinierzyCommand.class, "uciekinierzry", Permission.MANAGE_ROLES, "sprawdz uciekinierow", "ucieczka");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {

    }
}
