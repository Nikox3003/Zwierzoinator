package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.HasteUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class SwitchCommand extends Command {

    public SwitchCommand() {
        super(SwitchCommand.class, "switch", Permission.ADMINISTRATOR, "Switch ranks from accounts", "przenies");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        if(e.getMessage().getMentionedMembers().isEmpty() || e.getMessage().getMentionedMembers().size() < 2) {
            e.getChannel().sendMessage("Musisz wzmienić dwóch użytkowników.").queue();
            return;
        }
        Member m1 = e.getMessage().getMentionedMembers().get(0);
        Member m2 = e.getMessage().getMentionedMembers().get(1);
        List<Role> roles = m1.getRoles();
        StringBuilder builder = new StringBuilder("Przeniesione rangi z konta " + m1.getUser().getAsTag() + " na " + m2.getUser().getAsTag()  + ":\n\n");
        for(Role role : roles) {
            e.getGuild().addRoleToMember(m2, role).queue();
            builder.append("- ").append(role.getName()).append("\n");
        }
        HasteUtil util = new HasteUtil();
        String url = "";
        try {
            url = util.post(builder.toString(), false);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        e.getChannel().sendMessage("Przeniesiono role z konta " + m1.getAsMention() + " na " + m2.getAsMention() + "\nLista ról: " + url).queue();

    }
}
