package me.nikox.zwierzoinator.ship_module;

import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class ShipCommand extends Command {

    public ShipCommand() {
        super(ShipCommand.class, "ship", null, "Ship");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        if(args.length < 3) {
            e.getChannel().sendMessage("Musisz podać co najmniej 2 osoby!").queue();
            return;
        }
        Member ship = CommandUtil.getArgMember(e, 1, 0);
        Member ship2 = CommandUtil.getArgMember(e, 2, 1);
        if(ship == null || ship2 == null) {
            e.getChannel().sendMessage("Musisz podać co najmniej 2 osoby!").queue();
            return;
        }
        Random r = new Random();
        e.getChannel().sendMessage(r.nextInt(100) + "/100").queue();
    }
}
