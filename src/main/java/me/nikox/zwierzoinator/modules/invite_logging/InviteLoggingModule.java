package me.nikox.zwierzoinator.modules.invite_logging;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.boot.Bootstrap;
import me.nikox.zwierzoinator.boot.EventInitializer;
import me.nikox.zwierzoinator.modules.DiscordModule;
import net.dv8tion.jda.api.JDA;

public class InviteLoggingModule implements DiscordModule {

    @Override
    public void initialize() {
        JDA jda = Bootstrap.getJDA();
        jda.getGuilds().forEach(guild -> guild.retrieveInvites().queue(list -> {
            list.forEach(i -> VariableHolder.inviteList.put(i.getCode(), i.getUses()));
        }));
        EventInitializer.registerEvent(new InviteLoggingEvent());

    }
}
