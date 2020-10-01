package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.init.DatabaseInitializer;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.objects.Variable;
import me.nikox.zwierzoinator.util.CommandUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public class WypuscCommand extends Command {

    public WypuscCommand() {
        super(WypuscCommand.class, "wypusc", Permission.MANAGE_ROLES, "Wypusc zwierze z zoo");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        if(msg.length < 2){
            e.getChannel().sendMessage("Musisz podać użytkownika.").queue();
            return;
        }
        Member member = CommandUtil.getArgMember(e, 1);
        if(member == null){
            e.getChannel().sendMessage("Nie odnaleziono użytkownika " + msg[1] + " na serwerze.").queue();
            return;
        }
        Guild guild = e.getGuild();
        Role zwierze;
        TextChannel wypisy;
        if(guild.getId().equals(VariableHolder.OFFICIAL_GUILD_ID)){
            zwierze = guild.getRoleById(VariableHolder.ZWIERZE_ID);
            wypisy = guild.getTextChannelById(VariableHolder.WYPISY_ID);
        } else{
            zwierze = guild.getRoleById(VariableHolder.TEST_ZWIERZE_ID);
            wypisy = guild.getTextChannelById(VariableHolder.TEST_WYPISY_ID);
        }
        if(!member.getRoles().contains(zwierze)){
            e.getChannel().sendMessage("Ten użytkownik nie jest zwierzęciem.").queue();
            return;
        }
        try {
            Statement stat = DatabaseInitializer.getDataSource().getConnection().createStatement();
            stat.execute("UPDATE zwierzoinator_entries SET is_active = false WHERE (punished_id = " + member.getId() + " AND is_active = true)");
            stat.close();
            DatabaseInitializer.getDataSource().close();
        } catch (SQLException ex){
            e.getChannel().sendMessage("Wystąpił błąd podczas wykonywania zapytania do bazy danych:\n" + ex.getLocalizedMessage()).queue();
            return;
        }
        guild.removeRoleFromMember(member, zwierze).queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTimestamp(Instant.now());
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setAuthor(e.getAuthor().getAsTag(), null, e.getAuthor().getAvatarUrl());
        eb.setTitle("Zwierze zostało wypuszczone");
        eb.setColor(Color.YELLOW);
        eb.addField("Zwierze", member.getAsMention() + "\n(`" + member.getUser().getAsTag() + "`)", false);
        eb.addField("Moderator", e.getMember().getAsMention() + "\n(`" + e.getMember().getUser().getAsTag() + "`)", false);
        wypisy.sendMessage(eb.build()).queue();
        member.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage("Zostałeś wypuszczony z ZOO!").queue();
            channel.close().queue();
        });
        e.getChannel().sendMessage("Klatka " + member.getAsMention() + " otwarta! :thumbsup:").queue();
    }
}
