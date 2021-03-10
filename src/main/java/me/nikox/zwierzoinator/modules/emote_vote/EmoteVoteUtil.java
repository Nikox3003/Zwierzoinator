package me.nikox.zwierzoinator.modules.emote_vote;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.boot.Bootstrap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class EmoteVoteUtil {

    public static EmbedBuilder[] constructVoteEmbed() {
        JDA jda = Bootstrap.getJDA();
        Guild guild = jda.getGuildById(VariableHolder.OFFICIAL_GUILD_ID);
        int normal = guild.getEmotes().size();
        int pages = 0;
        if(normal <= 20) {
            pages = 1;
        } else if (normal <= 40) {
            pages = 2;
        } else if (normal <= 60) {
            pages = 3;
        } else if (normal <= 80) {
            pages = 4;
        } else if (normal <= 100) {
            pages = 5;
        } else if (normal <= 120) {
            pages = 6;
        } else if (normal <= 140) {
            pages = 7;
        } else if (normal <= 160) {
            pages = 8;
        } else if (normal <= 180) {
            pages = 9;
        } else if (normal <= 200) {
            pages = 10;
        }
        System.out.println(pages);
        EmbedBuilder[] eb = new EmbedBuilder[pages];
        for(int i2 = 0; i2 < pages; i2++) {
            if(eb[i2] == null) {
                eb[i2] = new EmbedBuilder();
            }
            eb[i2].setTitle("Głosowanie");
            eb[i2].setDescription("Zareaguj emotką, która uważasz za zbędną na serwerze.");
            eb[i2].setColor(Color.decode("#346beb"));
            eb[i2].setTimestamp(Instant.now());
        }
        return eb;
    }

}
