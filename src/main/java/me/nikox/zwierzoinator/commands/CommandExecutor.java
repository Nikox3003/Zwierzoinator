package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.boot.FileInitializer;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.objects.Variable;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.nikox.zwierzoinator.VariableHolder.commandList;

public class CommandExecutor extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            if (e.getAuthor() != e.getJDA().getSelfUser()) {
                return;
            }
        }
        String command = e.getMessage().getContentRaw().split(" ")[0];
        for (Command cmd : commandList) {
            List<String> executables = new ArrayList<>(cmd.getAliases());
            executables.add(cmd.getName());
            List<String> matches;
            matches = executables.stream().filter(s -> (FileInitializer.getConfig().getProperty("prefix") + s).equalsIgnoreCase(command)).collect(Collectors.toList());
            if (matches.isEmpty()) {
                continue;
            }
            if (cmd.getPermission() != null) {
                if (!cmd.getName().equalsIgnoreCase("zwierze") && !cmd.getName().equalsIgnoreCase("czas")
                        && !cmd.getName().equalsIgnoreCase("wypusc") && !cmd.getName().equalsIgnoreCase("lista-zwierzat")) {
                    if (!e.getMember().hasPermission(cmd.getPermission())) {
                        if (e.getMember().getIdLong() != 303921320561868802L) {
                            MessageUtil.sendMessage("Nie posiadasz uprawnień: `{@perm}`", e.getChannel(),
                                    new Variable("{@perm}", cmd.getPermission().getName()), new Variable("{@member}", e.getAuthor().getAsTag()));
                            return;
                        }
                    }
                } else {
                    Role role = e.getJDA().getRoleById(808015582770888785L);
                    if (e.getMember().getRoles().contains(role)) {
                        cmd.execute(e);
                        return;
                    }
                    if (!e.getMember().hasPermission(cmd.getPermission())) {
                        if (e.getMember().getIdLong() != 303921320561868802L) {
                            MessageUtil.sendMessage("Nie posiadasz uprawnień: `{@perm}`", e.getChannel(),
                                    new Variable("{@perm}", cmd.getPermission().getName()), new Variable("{@member}", e.getAuthor().getAsTag()));
                            return;
                        }
                    }
                }
            }
            cmd.execute(e);
            return;
        }
    }
}
