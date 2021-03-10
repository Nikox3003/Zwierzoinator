package me.nikox.zwierzoinator.modules.emote_vote;

import me.nikox.zwierzoinator.objects.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class EmoteVoteCommand extends Command {

    public EmoteVoteCommand() {
        super(EmoteVoteCommand.class, "emote-vote", Permission.ADMINISTRATOR, "", "emotevote", "vote-emote");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        EmbedBuilder[] eb = EmoteVoteUtil.constructVoteEmbed();
        int current = 1;
        for (EmbedBuilder embedBuilder : eb) {
            int finalCurrent = current;
            e.getChannel().sendMessage(embedBuilder.build()).queue(msg -> {
                if(finalCurrent != 1) {
                    msg.suppressEmbeds(true).queue();
                }
                for(int i = (finalCurrent-1)*20; i < finalCurrent *20; i++) {
                    msg.addReaction(e.getGuild().getEmotes().get(i)).queue();
                }
            });
            current++;
        }
    }
}
