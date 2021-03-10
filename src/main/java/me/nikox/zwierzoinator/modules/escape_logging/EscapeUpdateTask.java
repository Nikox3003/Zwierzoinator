package me.nikox.zwierzoinator.modules.escape_logging;

import com.zaxxer.hikari.HikariDataSource;
import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.boot.Bootstrap;
import me.nikox.zwierzoinator.boot.DatabaseInitializer;
import me.nikox.zwierzoinator.objects.BotInstance;
import me.nikox.zwierzoinator.objects.Entry;
import me.nikox.zwierzoinator.scheduler.Task;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EscapeUpdateTask extends Task {

    static Runnable run = () -> {
        HikariDataSource source = DatabaseInitializer.getDataSource();
        Connection conn = null;
        try {
            conn = source.getConnection();
            Statement stat = conn.createStatement();
            for (Entry entry : VariableHolder.entryList) {
                if (!entry.isActive()) {
                    continue;
                }
                Guild guild = Bootstrap.getJDA().getGuildById(VariableHolder.OFFICIAL_GUILD_ID);
                Member member = guild.getMemberById(entry.getPunishedId());
                if (member != null) {
                    continue;
                }
                User user = Bootstrap.getJDA().getUserById(entry.getPunishedId());
//                stat.execute("UPDATE zwierzoinator_entries SET is_active = false WHERE (entry_id = " + entry.getEntryId() + ")");
                int id = 0;
                ResultSet set = stat.executeQuery("SELECT entry_id FROM zwierzoinator_escapes");
                while (set.next()) {
                    id++;
                }
                stat.execute("INSERT INTO zwierzoinator_escapes (entry_id, escaped_at, escaper_id) VALUES (" + id + ", " +
                        System.currentTimeMillis() + ", " + entry.getPunishedId() + ")");
                System.out.println(user.getAsTag());
                TextChannel channel = guild.getTextChannelById(774704512258277376L);
                channel.sendMessage(user.getAsTag() + "\n" + user.getAsMention() + "\n" + user.getId()).queue();
                stat.close();
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        source.close();
    };

    public EscapeUpdateTask() {
        super(run);
    }
}
