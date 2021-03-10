package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UserCheckerCommand extends Command {

    public UserCheckerCommand() {
        super(UserCheckerCommand.class, "usercheck", null, "");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        User user = CommandUtil.getArgUser(e, 1);
        e.getChannel().sendMessage("Nazwa: " + user.getAsTag() + "\nWzmianka: " + user.getAsMention()).queue();
    }
}
