package me.nikox.zwierzoinator.modules.purge;

import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PurgeCommand extends Command {

    public PurgeCommand() {
        super(PurgeCommand.class, "purge", null, "Purge", "clear");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        PurgeLevel level = null;
        boolean hasPerms = false;
        if(e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            level = PurgeLevel.UNLIMITED;
            hasPerms = true;
        } else {
            for(PurgeLevel loopLevel : PurgeLevel.values()) {
                for (long allowedRank : loopLevel.getAllowedRanks()) {
                    Role role = e.getGuild().getRoleById(allowedRank);
                    if(e.getMember().getRoles().contains(role)) {
                        level = loopLevel;
                        if(level == PurgeLevel.UNLIMITED) {
                            hasPerms = true;
                        }
                        break;
                    }
                }
            }
        }
        if(level == null) {
            e.getChannel().sendMessage("Nie posiadasz uprawnień.").queue();
            return;
        }
        long textChannel = e.getChannel().getIdLong();
        switch (level){
            case MP:
                if(textChannel != 493022356671823872L && textChannel != 695632820500037673L && textChannel != 717051270443040869L
                && textChannel != 795695462048202762L) {
                    e.getChannel().sendMessage("Nie posiadasz uprawnień do wykonania tej komendy na tym kanale.").queue();
                    return;
                }
                hasPerms = true;
                break;
            case PA:
                if(textChannel != 493019604424065024L && textChannel != 676493906220810240L && textChannel != 494081855151407124L
                && textChannel != 676493798116687909L && textChannel != 717060508636676187L) {
                    e.getChannel().sendMessage("Nie posiadasz uprawnień do wykonania tej komendy na tym kanale.").queue();
                    return;
                }
                hasPerms = true;
                break;
        }
        if(!hasPerms) {
            return;
        }
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        if(args.length < 2) {
            e.getChannel().sendMessage("invalid args").queue();
            return;
        }
        int number = Integer.parseInt(args[1]);
        e.getChannel().getHistory().retrievePast(number).queue(list -> {
            e.getChannel().deleteMessages(list).queue();
            e.getChannel().sendMessage("Usunięto " + number + " wiadomości.").queue();
        });
    }
}
