package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.init.FileInitializer;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.objects.Variable;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.nikox.zwierzoinator.VariableHolder.commandList;

public class CommandExecutor extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
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
                if (!e.getMember().hasPermission(cmd.getPermission())) {
                    MessageUtil.sendMessage("Nie posiadasz uprawnień: `{@perm}`", e.getChannel(),
                            new Variable("{@perm}", cmd.getPermission().getName()), new Variable("{@member}", e.getAuthor().getAsTag()));
                    return;
                }
            }
            cmd.execute(e);
            return;
        }
    }
}
