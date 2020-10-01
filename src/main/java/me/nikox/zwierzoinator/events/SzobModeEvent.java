package me.nikox.zwierzoinator.events;

import me.nikox.zwierzoinator.VariableHolder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class SzobModeEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
        if(!VariableHolder.szobMode) {
            return;
        }
        if(e.getAuthor().isBot()){
            return;
        }
        if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
            return;
        }
        if(!e.getChannel().getId().equalsIgnoreCase("493007788134629377")){
            return;
        }
        String msg = e.getMessage().getContentRaw();
        if(!msg.equalsIgnoreCase("<:szob:741039317178122280>") && !msg.equalsIgnoreCase("<:sh00b_dorime:743219387800092723>")
        && !msg.equalsIgnoreCase("<:sh00b_gun:741037281091256361>")){
            e.getMessage().delete().queue();
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if(!VariableHolder.szobMode){
            return;
        }
        if(e.getAuthor().isBot()){
            return;
        }
        if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
            return;
        }
        if(!e.getChannel().getId().equalsIgnoreCase("493007788134629377")){
            return;
        }
        String msg = e.getMessage().getContentRaw();
        if(!msg.equalsIgnoreCase("<:sz00b:741039317178122280>") && !msg.equalsIgnoreCase("<:sh00b_dorime:743219387800092723>")
                && !msg.equalsIgnoreCase("<:sh00bgun:741037281091256361>") && !msg.equalsIgnoreCase("<:sz00b_kocyk:753528973706920008>")){
            e.getMessage().delete().queue();
        }
    }
}
