package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class FixRolesCommand extends Command {

    public FixRolesCommand() {
        super(FixRolesCommand.class, "fix-roles", Permission.ADMINISTRATOR, "");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        e.getChannel().sendMessage("Naprawianie ról... (to może trochę potrwać)").queue();
        Guild g = e.getGuild();
        int i = 0;
        Role mieso = g.getRoleById(VariableHolder.MIESO_ID);
        List<Member> members = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Member> members = g.loadMembers().get();
                for(Member member : members) {
                    if(member.getRoles().isEmpty()) {
                        g.addRoleToMember(member, mieso).queue();
                        System.out.println("[DEBUG] Dodano mięso armatnie dla " + member.getEffectiveName());
                    }
                }
            }
        });
        thread.start();
        e.getChannel().sendMessage("Gotowe! Dodano rolę dla " + i + " użytkowników.").queue();
    }
}
