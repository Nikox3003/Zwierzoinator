package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

public class EmojiCommand extends Command {

    public EmojiCommand() {
        super(EmojiCommand.class, "emoji", Permission.MANAGE_EMOTES, "Emoji info", "emote");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        Guild g = e.getGuild();
        int max = g.getMaxEmotes();
        int boost = g.getBoostTier().getKey();
        int normal = 0;
        int animated = 0;
        for(Emote emote : g.getEmotes()){
            if(emote.isAnimated()) {
                animated++;
            } else{
                normal++;
            }
        }
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Informacja o emotkach")
                .setDescription("Serwer posiada **" + boost + " poziom** ulepszenia nitro.")
                .addField("Emoji Statyczne", normal + "/" + max, true)
                .addField("Emoji Animowane", animated + "/" + max, true)
                .setTimestamp(Instant.now())
                .setFooter(e.getAuthor().getAsTag(), e.getAuthor().getAvatarUrl());
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
