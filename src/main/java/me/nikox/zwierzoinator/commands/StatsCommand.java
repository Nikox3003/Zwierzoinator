package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.init.DatabaseInitializer;
import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

import static me.nikox.zwierzoinator.VariableHolder.*;

public class StatsCommand extends Command {
    public StatsCommand() {
        super(StatsCommand.class, "stats", Permission.ADMINISTRATOR, "Statystyki", "statystyki", "stat");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        Guild caton = e.getJDA().getGuildById(OFFICIAL_GUILD_ID);
        int currentSinners = caton.getMembersWithRoles(e.getJDA().getRoleById(ZWIERZE_ID)).size();
        int allTimeSinners = 0;
        try {
            Statement stat = DatabaseInitializer.getDataSource().getConnection().createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM zwierzoinator_entries");
            while(set.next()) {
                allTimeSinners++;
            }
            set.close();
            stat.close();
            DatabaseInitializer.getDataSource().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        int bans = caton.retrieveBanList().complete().size();
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle(":bar_chart: Statystyki")
                .setColor(e.getMember().getColor())
                .addField("Odbywający karę", String.valueOf(currentSinners), false)
                .addField("Zarejestrowane odsiadki", String.valueOf(allTimeSinners), false)
                .addField("Zbanowani", String.valueOf(bans), false)
                .addField("Próby ucieczki", "Soon", false)
                .setFooter(e.getAuthor().getAsTag(), e.getAuthor().getAvatarUrl())
                .setTimestamp(Instant.now());
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
