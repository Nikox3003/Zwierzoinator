package me.nikox.zwierzoinator.events;

import me.nikox.zwierzoinator.VariableHolder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Map;

public class AutoResponseEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if(e.getGuild().getId().equals(VariableHolder.OFFICIAL_GUILD_ID)){
            return;
        }
        if(e.getAuthor().isBot()){
            return;
        }
        String message = e.getMessage().getContentRaw();
        for(Map.Entry<String, String> entry : VariableHolder.autoResponseList.entrySet()){
            if(message.equals(entry.getKey())){
                e.getChannel().sendMessage(entry.getValue()).queue();
                return;
            }
        }
    }
}
