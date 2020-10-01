package me.nikox.zwierzoinator.objects;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class BotInstance {

    private String token;
    private JDA jda;

    /**
     * Constructor instancji bota.
     *
     * @param token - token bota
     */

    public BotInstance(String token) {
        this.token = token;
        load();
    }

    /**
     * Ładowanie bota oraz przypisanie flag.
     */
    public void load() {
        try {
            this.jda = JDABuilder.createDefault(token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .build();
        } catch (LoginException e) {
            System.out.println("Token " + token + " jest nieprawidlowy!");
        }
    }

    /**
     * Zwracanie JDA dla instancji bota
     *
     * @return Bot JDA
     */
    public JDA getJDA() {
        return this.jda;
    }
}
