package me.nikox.zwierzoinator.boot;

import me.nikox.zwierzoinator.modules.DiscordModule;
import me.nikox.zwierzoinator.modules.account_manager.AccountManagerModule;
import me.nikox.zwierzoinator.modules.alt_detector_module.AltDetectorModule;
import me.nikox.zwierzoinator.modules.emote_vote.EmoteVoteModule;
import me.nikox.zwierzoinator.modules.escape_logging.EscapeLoggingModule;
import me.nikox.zwierzoinator.modules.invite_logging.InviteLoggingModule;
import me.nikox.zwierzoinator.modules.purge.PurgeModule;
import me.nikox.zwierzoinator.modules.szob_mode.SzobModule;

public class ModuleInitializer {

    public static void load() {
        DiscordModule inviteLogging = new InviteLoggingModule();
        inviteLogging.initialize();
        DiscordModule purge = new PurgeModule();
        purge.initialize();
        DiscordModule escape = new EscapeLoggingModule();
        escape.initialize();
        DiscordModule emoteVote = new EmoteVoteModule();
        emoteVote.initialize();
        DiscordModule szob = new SzobModule();
        szob.initialize();
        DiscordModule altDetector = new AltDetectorModule();
        altDetector.initialize();
        DiscordModule accountManager = new AccountManagerModule();
        accountManager.initialize();
    }
}
