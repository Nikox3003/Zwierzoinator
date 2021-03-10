package me.nikox.zwierzoinator.scheduler.tasks;

import me.nikox.zwierzoinator.boot.Bootstrap;
import me.nikox.zwierzoinator.boot.DatabaseInitializer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;

import static me.nikox.zwierzoinator.VariableHolder.*;

public class AutoReleaseTask implements Runnable {

    @Override
    public void run() {
        try {
            JDA jda = Bootstrap.getJDA();
            Guild guild = jda.getGuildById(OFFICIAL_GUILD_ID);
            Role zwierze = guild.getRoleById(ZWIERZE_ID);
            TextChannel wypisy = guild.getTextChannelById(WYPISY_ID);
            Connection con = DatabaseInitializer.getDataSource().getConnection();
            Statement stat = con.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM zwierzoinator_entries WHERE (punished_to < " + System.currentTimeMillis() + " AND is_active = true AND punished_to != -1)");
            while (set.next()) {
                long id = set.getLong("punished_id");
                Member member = guild.getMemberById(id);
                if(member != null) {
                    guild.removeRoleFromMember(member, zwierze).queue();
                    member.getUser().openPrivateChannel().queue(channel -> {
                        channel.sendMessage("Zostałeś wypuszczony z ZOO!").queue();
                        channel.close().queue();
                    });
                    EmbedBuilder wy = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setThumbnail(member.getUser().getAvatarUrl())
                            .setAuthor("AUTOMAT")
                            .setTitle("Zwierze zostało wypuszczone")
                            .setColor(Color.YELLOW)
                            .addField("Zwierze", member.getAsMention() + "\n(`" + member.getUser().getAsTag() + "`)", false)
                            .setFooter("Akcja wykonana automatycznie.")
                            .addField("Moderator", jda.getSelfUser().getAsMention() + "\n(`" + jda.getSelfUser().getAsTag() + "`)", false);
                    wypisy.sendMessage(wy.build()).queue();
                }
                stat.execute("UPDATE zwierzoinator_entries SET is_active = false WHERE (entry_id = " + set.getInt("entry_id") + ")");
            }
            set.close();
            stat.close();
            con.close();
            DatabaseInitializer.getDataSource().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}