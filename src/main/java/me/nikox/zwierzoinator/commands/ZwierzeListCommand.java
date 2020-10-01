package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ZwierzeListCommand extends Command {

    public ZwierzeListCommand() {
        super(ZwierzeListCommand.class, "lista-zwierzat", Permission.MANAGE_ROLES, "Sprawdz liste zwierzat na serwerze");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        Guild guild = e.getJDA().getGuildById("493007787622662146");
        Role zwierze = guild.getRoleById(VariableHolder.ZWIERZE_ID);
        List<Member> list = guild.getMembersWithRoles(zwierze);
        StringBuilder b = new StringBuilder("Lista zwierzat:\n\n");
        for(Member m : list){
            b.append(m.getUser().getAsTag()).append("\n");
        }
        e.getChannel().sendMessage(b.toString()).queue();

    }
}
