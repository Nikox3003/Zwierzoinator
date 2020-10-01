package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.database.DatabaseSaver;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.objects.Entry;
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
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static me.nikox.zwierzoinator.VariableHolder.*;

public class ZwierzeCommand extends Command {

    public ZwierzeCommand() {
        super(ZwierzeCommand.class, "zwierze", Permission.MANAGE_ROLES, "Nadaj range karna", "zwierzę");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        if(msg.length < 3){
            e.getChannel().sendMessage("Komu wystawić bilet? :monkey:").queue();
            return;
        }
        Member member = CommandUtil.getArgMember(e, 1);
        if(member == null){
            e.getChannel().sendMessage("Nie odnaleziono użytkownika " + msg[1] + " na serwerze.").queue();
            return;
        }
        if(member == e.getGuild().getSelfMember()){
            e.getChannel().sendMessage("https://tenor.com/view/hit-nope-fall-gif-13373229").queue();
            return;
        }
        if(member == e.getMember()){
            e.getChannel().sendMessage("Życie jest jeszcze długie :sob:").queue();
            return;
        }
        if(!e.getMember().canInteract(member)){
            e.getChannel().sendMessage("https://tenor.com/view/lotr-you-have-no-power-here-the-two-towers-king-theoden-gif-4882470").queue();
            return;
        }
        String reason = "";
        for(int i = 2; i<msg.length;i++){
            reason = reason + msg[i] + " ";
        }
        Guild guild = e.getGuild();
        Role zwierze, mieso;
        TextChannel wpisy, dziennik;
        if(guild.getId().equals(OFFICIAL_GUILD_ID)){
            zwierze = guild.getRoleById(ZWIERZE_ID);
            mieso = guild.getRoleById(MIESO_ID);
            wpisy = guild.getTextChannelById(WPISY_ID);
            dziennik = guild.getTextChannelById(DZIENNIK_KAR_ID);
        } else{
            mieso = guild.getRoleById(TEST_MIESO_ID);
            zwierze = guild.getRoleById(TEST_ZWIERZE_ID);
            wpisy = guild.getTextChannelById(TEST_WPISY_ID);
            dziennik = guild.getTextChannelById(TEST_DZIENNIK_KAR_ID);
        }
        if(member.getRoles().contains(zwierze)){
            e.getChannel().sendMessage("Użytkownik jest już zwierzęciem. Chcesz, żeby był zwierzęciem, które jest zwierzęciem czy co? :thinking:").queue();
            return;
        }
        guild.addRoleToMember(member, mieso).queue();
        guild.addRoleToMember(member, zwierze).queue();
        if(guild.getId().equals(OFFICIAL_GUILD_ID)){
            rolesToRemove.stream().forEach(role -> guild.removeRoleFromMember(member, role).queue());
        }
        EmbedBuilder dz = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setThumbnail(member.getUser().getAvatarUrl())
                .setAuthor(e.getAuthor().getAsTag(), null, e.getAuthor().getAvatarUrl())
                .setDescription(null)
                .setColor(Color.red)
                .addField("Karany", member.getAsMention() + "\n(`" + member.getUser().getAsTag() + "`)", true)
                .addBlankField(true)
                .addField("Typ kary", "`" + zwierze.getName() + "`", true)
                .addField("Moderator", e.getMember().getAsMention() + "\n(`" + e.getMember().getUser().getAsTag() + "`)", false)
                .addField("Powód", reason, false);
        dziennik.sendMessage(dz.build()).queue();
        member.getUser().openPrivateChannel().queue(channel -> {
            dz.setTitle("Zostałeś ukarany!");
            channel.sendMessage(dz.build()).queue();
            channel.close().queue();
        });
        e.getChannel().sendMessage("Bilet w jedną stronę do klatki poleciał do **" + member.getUser().getAsTag() + "**! :)").queue();
        EmbedBuilder wp = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setThumbnail(member.getUser().getAvatarUrl())
                .setAuthor(e.getAuthor().getAsTag(), null, e.getAuthor().getAvatarUrl())
                .setTitle("Wpis wymaga zatwierdzenia!")
                .setColor(Color.yellow)
                .setDescription("Użyj komendy `!!czas " + member.getId() + " <czas>` aby ustawić czas odsiadki.")
                .addField("Karany", member.getAsMention() + "\n(`" + member.getUser().getAsTag() + "`)", true)
                .addField("Typ kary", "`" + zwierze.getName() + "`", true);
        Entry.EntryBuilder builder = Entry.builder();
        wpisy.sendMessage(e.getAuthor().getAsMention()).queue(ping -> ping.delete().queueAfter(30, TimeUnit.SECONDS));
        builder.entryMessage(wpisy.sendMessage(wp.build()).complete());
        builder.isActive(true)
                .isAccepted(false)
                .punishedAt(System.currentTimeMillis())
                .reason(reason)
                .executorId(e.getAuthor().getIdLong())
                .punishedId(member.getIdLong());
        try {
            DatabaseSaver.saveNewEntry(builder.build());
        } catch (SQLException ex){
            e.getChannel().sendMessage("Wystąpił wewnętrzny błąd: \n" + ex.getLocalizedMessage()).queue();
        }
    }
}
