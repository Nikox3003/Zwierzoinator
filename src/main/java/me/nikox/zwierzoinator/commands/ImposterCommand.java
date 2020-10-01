package me.nikox.zwierzoinator.commands;

import lombok.SneakyThrows;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ImposterCommand extends Command {

    public ImposterCommand() {
        super(ImposterCommand.class, "imposter", Permission.ADMINISTRATOR, "Ukryj", "ukryj");
    }

    private void downloadImage(String search, String path) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            URL url = new URL(search);
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", USER_AGENT);
            inputStream = con.getInputStream();
            outputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        outputStream.close();
        inputStream.close();
    }

    @SneakyThrows
    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        Icon icon = null;
        if(args[1].equalsIgnoreCase("off")) {
            try {
                icon = Icon.from(new File(System.getProperty("user.dir"), "monkey.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.getGuild().getSelfMember().modifyNickname("Zwierzoinator [ \uD83D\uDC35 ]").queue();
            for (Role role : e.getGuild().getSelfMember().getRoles()) {
                if (role.getId().equals("740606076335947837")) {
                    continue;
                }
                e.getGuild().removeRoleFromMember(e.getGuild().getSelfMember(), role).queue();
            }
            e.getGuild().addRoleToMember(e.getGuild().getSelfMember(), e.getGuild().getRoleById("493018217304948736")).queue();
            e.getJDA().getSelfUser().getManager().setAvatar(icon).queue();
            e.getChannel().sendMessage(":see_no_evil: :monkey_face: ").queue();
            return;
        }
        User user = CommandUtil.getArgUser(e, 1);
        Member member = CommandUtil.getArgMember(e, 1);
        try {
            downloadImage(user.getAvatarUrl(), System.getProperty("user.dir") + "/avatar_temp.png");
            icon = Icon.from(new File(System.getProperty("user.dir"), "avatar_temp.png"));
            e.getJDA().getSelfUser().getManager().setAvatar(icon).queue();
            String nick = member.getNickname();
            if (nick == null) {
                nick = member.getEffectiveName();
            }
            e.getGuild().getSelfMember().modifyNickname(nick).queue();
            List<Role> memberRoles = member.getRoles();
            for (Role role : e.getGuild().getSelfMember().getRoles()) {
                if (role.getId().equals("740606076335947837")) {
                    continue;
                }
                e.getGuild().removeRoleFromMember(e.getGuild().getSelfMember(), role).queue();
            }
            for (Role role : memberRoles) {
                e.getGuild().addRoleToMember(e.getGuild().getSelfMember(), role).queue();
            }
            e.getChannel().sendMessage(":spy: Ukryto jako " + member.getAsMention()).queue();
        } catch (Exception ex) {
            e.getChannel().sendMessage("Wystąpił wewnętrzny błąd: " + ex.getLocalizedMessage()).queue();
        }
    }
}
