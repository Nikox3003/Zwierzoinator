package me.nikox.zwierzoinator.modules.invite_logging;

import me.nikox.zwierzoinator.VariableHolder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class InviteLoggingEvent extends ListenerAdapter {

    public void onGuildInviteCreate(GuildInviteCreateEvent e) {
        VariableHolder.inviteList.put(e.getInvite().getCode(), e.getInvite().getUses());
    }

    public void onGuildInviteDelete(GuildInviteDeleteEvent e) {
        VariableHolder.inviteList.remove(e.getCode());
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        e.getGuild().retrieveInvites().queue(list -> list.forEach(invite -> VariableHolder.inviteList.forEach((code, uses) -> {
            if(code.equals(invite.getCode()) && invite.getUses() - 1 == uses){
                User inviter = invite.getInviter();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTimestamp(Instant.now());
                eb.setTitle("Nowy użytkownik!");
                eb.setColor(Color.decode("#0ac743"));
                eb.setDescription(null);
                eb.addField("Użytkownik", e.getMember().getAsMention() + "\n(`" + e.getMember().getUser().getAsTag() + "`)", true);
                eb.addField("Zapraszający", inviter.getAsMention() + "\n(`" + inviter.getAsTag() + "`)", true);
                eb.addField("Data dołączenia", Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), false);
                eb.addField("Kod zaproszenia", code, true);
                eb.addField("Użycia", String.valueOf(invite.getUses()), true);
                e.getGuild().getTextChannelById("761986826104864788").sendMessage(eb.build()).queue();
                VariableHolder.inviteList.replace(code, uses + 1);
            }
        })));
    }
}
