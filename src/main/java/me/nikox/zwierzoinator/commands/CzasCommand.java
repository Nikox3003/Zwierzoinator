package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.boot.DatabaseInitializer;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import me.nikox.zwierzoinator.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CzasCommand extends Command {

    public CzasCommand() {
        super(CzasCommand.class, "czas", Permission.MANAGE_ROLES, "Ustaw czas odsiadki", "ustaw-czas");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] msg = MessageUtil.getArgs(e.getMessage().getContentRaw());
        if(msg.length < 3){
            e.getChannel().sendMessage("Invalid args").queue();
            return;
        }
        Member member = CommandUtil.getArgMember(e, 1);
        if(member == null) {
            e.getChannel().sendMessage("Invalid member").queue();
            return;
        }
        long millis = TimeUtil.getTimeMillis(msg[2]);
        EmbedBuilder eb = new EmbedBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.systemDefault());
        try {
            Statement stat = DatabaseInitializer.getDataSource().getConnection().createStatement();
            String sql = "SELECT * FROM zwierzoinator_entries WHERE (punished_id = " + member.getId() + " AND is_active = true AND is_accepted = false)";
            ResultSet set = stat.executeQuery(sql);
            if(!set.next()){
                e.getChannel().sendMessage("Nie odnaleziono użytkownika w bazie danych!").queue();
                return;
            }
            eb.setTimestamp(Instant.now())
                    .setTimestamp(Instant.now())
                    .setThumbnail(member.getUser().getAvatarUrl())
                    .setAuthor(e.getAuthor().getAsTag(), null, e.getAuthor().getAvatarUrl())
                    .setDescription(null)
                    .setColor(Color.red)
                    .addField("Karany", member.getAsMention() + "\n(`" + member.getUser().getAsTag() + "`)", true)
                    .addBlankField(true)
                    .addField("Typ kary", "`Zwierze`", true)
                    .addField("Moderator", e.getMember().getAsMention() + "\n(`" + e.getMember().getUser().getAsTag() + "`)", false)
                    .addField("Przewidywany czas wyjścia", Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC).format(format), true)
                    .addField("ID wpisu", String.valueOf(set.getInt("entry_id")), true)
                    .addField("Powód", set.getString("reason"), false);
            TextChannel wpisy;
            if(e.getGuild().getId().equals(VariableHolder.OFFICIAL_GUILD_ID)){
                wpisy = e.getGuild().getTextChannelById(VariableHolder.WPISY_ID);
            } else {
                wpisy = e.getGuild().getTextChannelById(VariableHolder.TEST_WPISY_ID);
            }
            wpisy.retrieveMessageById(set.getLong("entry_message")).queue(message -> message.editMessage(eb.build()).queue());
            stat.execute("UPDATE zwierzoinator_entries SET punished_to = " + millis + ", is_accepted = true WHERE (punished_id = " + member.getId() + " AND is_active = true AND is_accepted = false)");
            set.close();
            stat.close();
            DatabaseInitializer.getDataSource().close();
        } catch (SQLException ex) {
            e.getChannel().sendMessage(ex.getLocalizedMessage()).queue();
            ex.printStackTrace();
            return;
        }
        e.getMessage().delete().queue();
    }
}
