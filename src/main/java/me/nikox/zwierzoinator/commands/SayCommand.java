package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SayCommand extends Command {

    public SayCommand() {
        super(SayCommand.class, "say", Permission.ADMINISTRATOR, "Wywołaj komendę czatu botem.", "powiedz");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        if(args.length < 2) {
            e.getChannel().sendMessage("Invalid args").queue();
            return;
        }
        int arg = 0;
        TextChannel channel = CommandUtil.getArgChannel(e, 1);
        if(channel == null) {
            arg = 1;
            channel = e.getChannel();
        } else {
            arg = 2;
            if(args.length < 2) {
                e.getChannel().sendMessage("Invalid args").queue();
                return;
            }
        }
        String message = MessageUtil.getTextArgument(args, arg);
        channel.sendMessage(message).queue();
    }
}
