package me.nikox.zwierzoinator.modules.emote_vote;

import me.nikox.zwierzoinator.boot.CommandInitializer;
import me.nikox.zwierzoinator.modules.DiscordModule;

public class EmoteVoteModule implements DiscordModule {

    @Override
    public void initialize() {
        CommandInitializer.registerCommand(new EmoteVoteCommand());
    }
}
