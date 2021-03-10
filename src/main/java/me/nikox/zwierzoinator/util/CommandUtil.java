package me.nikox.zwierzoinator.util;

import me.nikox.zwierzoinator.boot.Bootstrap;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.List;

public class CommandUtil {

    public static Emote getArgEmote(GuildMessageReceivedEvent e, int argNumber) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        Emote emote;
        if (!message.getEmotes().isEmpty()) {
            return message.getEmotes().get(0);
        }
        try {
            emote = e.getGuild().getEmoteById(msg[argNumber]);
            return emote;
        } catch (NullPointerException | ErrorResponseException npe) {
            return null;
        }
    }

    public static Member getArgMember(GuildMessageReceivedEvent e, int argNumber) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        Member argMember;
        if (!message.getMentionedMembers().isEmpty()) {
            argMember = message.getMentionedMembers().get(0);
            return argMember;
        }
        try {
            argMember = e.getGuild().getMemberById(msg[argNumber]);
            return argMember;
        } catch (NullPointerException | ErrorResponseException npe) {
            return null;
        } catch (NumberFormatException n) {
            try {
                java.util.List<Member> memberList = e.getGuild().getMembersByName(msg[argNumber], true);
                argMember = memberList.get(0);
                return argMember;
            } catch (NullPointerException | IndexOutOfBoundsException npe) {
                return null;
            }
        }
    }

    public static Member getArgMember(GuildMessageReceivedEvent e, int argNumber, int listPos) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        Member argMember;
        if (!message.getMentionedMembers().isEmpty()) {
            try {
                argMember = message.getMentionedMembers().get(listPos);
            } catch (Exception ex) {
                if(message.getMentionedMembers().get(0) == e.getMember()) {
                    return e.getMember();
                }
                return null;
            }
            return argMember;
        }
        try {
            argMember = e.getGuild().getMemberById(msg[argNumber]);
            return argMember;
        } catch (NullPointerException | ErrorResponseException npe) {
            return null;
        } catch (NumberFormatException n) {
            try {
                java.util.List<Member> memberList = e.getGuild().getMembersByName(msg[argNumber], true);
                argMember = memberList.get(0);
                return argMember;
            } catch (NullPointerException | IndexOutOfBoundsException npe) {
                return null;
            }
        }
    }

    /**
     * Wydobywanie obiektu User z argumentu komendy.
     *
     * @param e         - GuildMessageReceivedEvent
     * @param argNumber - Numer argumentu
     * @return User
     */
    public static User getArgUser(GuildMessageReceivedEvent e, int argNumber) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        User argUser;
        if (!message.getMentionedMembers().isEmpty()) {
            argUser = message.getMentionedUsers().get(0);
            return argUser;
        }
        try {
            argUser = Bootstrap.getJDA().retrieveUserById(msg[argNumber]).complete();
            return argUser;
        } catch (NullPointerException | ErrorResponseException npe) {
            return null;
        } catch (NumberFormatException n) {
            try {
                java.util.List<User> userList = Bootstrap.getJDA().getUsersByName(msg[argNumber], true);
                argUser = userList.get(0);
                return argUser;
            } catch (NullPointerException | IndexOutOfBoundsException npe) {
                return null;
            }
        }
    }

    /**
     * Wydobywanie obiektu TextChannel z argumentu komendy.
     *
     * @param e         - GuildMessageReceivedEvent
     * @param argNumber - Numer argumentu
     * @return TextChannel
     */
    public static TextChannel getArgChannel(GuildMessageReceivedEvent e, int argNumber) {
        TextChannel argChannel;
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        if (!message.getMentionedChannels().isEmpty()) {
            argChannel = message.getMentionedChannels().get(0);
            return argChannel;
        }
        try {
            argChannel = e.getGuild().getTextChannelById(msg[argNumber]);
            return argChannel;
        } catch (NumberFormatException nfe) {
            try {
                java.util.List<TextChannel> channelList = e.getGuild().getTextChannelsByName(msg[argNumber], true);
                argChannel = channelList.get(0);
                return argChannel;
            } catch (NullPointerException | IndexOutOfBoundsException npe) {
                return null;
            }
        }
    }

    /**
     * Wydobywanie obiektu Role z argumentu komendy.
     *
     * @param e         - GuildMessageReceivedEvent
     * @param argNumber - Numer argumentu
     * @return Role
     */
    public static Role getArgRole(GuildMessageReceivedEvent e, int argNumber) {
        Role argRole;
        String[] msg = e.getMessage().getContentRaw().split(" ");
        Message message = e.getMessage();
        if (!message.getMentionedRoles().isEmpty()) {
            argRole = message.getMentionedRoles().get(0);
            return argRole;
        }
        try {
            argRole = e.getGuild().getRoleById(msg[argNumber]);
            return argRole;
        } catch (NumberFormatException nfe) {
            try {
                List<Role> roleList = e.getGuild().getRolesByName(msg[argNumber], true);
                argRole = roleList.get(0);
                return argRole;
            } catch (NullPointerException | IndexOutOfBoundsException npe) {
                return null;
            }
        }
    }
}
