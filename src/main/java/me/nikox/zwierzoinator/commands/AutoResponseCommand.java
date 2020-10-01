package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

import static me.nikox.zwierzoinator.VariableHolder.autoResponseList;

public class AutoResponseCommand extends Command {

    public AutoResponseCommand() {
        super(AutoResponseCommand.class, "autoresponse", Permission.MANAGE_SERVER, "Dodaj automatyczną odpowiedź.", "auto-response");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        if(msg.length < 3){
            e.getChannel().sendMessage("Użycie: `autoresponse add/remove <wiadomość> :: <odpowiedź>`").queue();
            return;
        }
        if(msg[1].equalsIgnoreCase("add")){
            StringBuilder args = new StringBuilder();
            for(int i = 2;i<msg.length;i++){
                args.append(msg[i]).append(" ");
            }
            String message = args.substring(0, args.indexOf("::") - 1);
            String response = args.substring(args.indexOf("::") + 3);
            autoResponseList.put(message, response);
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(Color.green)
                    .setFooter(e.getAuthor().getAsTag(), e.getAuthor().getAvatarUrl())
                    .setTimestamp(Instant.now())
                    .setTitle("Sukces!")
                    .setDescription("Poprawnie automatyczną odpowiedź.")
                    .addField("Wiadomość", message, true)
                    .addField("Odpowiedź", response, true);
            e.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
