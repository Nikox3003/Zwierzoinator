package me.nikox.zwierzoinator.init;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.BotInstance;
import me.nikox.zwierzoinator.scheduler.Scheduler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import static me.nikox.zwierzoinator.VariableHolder.*;

public class Bootstrap {

    private static JDA jda;
    private static BotInstance botInstance;

    public static void start(){
        Scheduler.setup();
        try {
            FileInitializer.load();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        Properties config = FileInitializer.getConfig();
        botInstance = new BotInstance(config.getProperty("bootstrap.token"));
        long time = System.currentTimeMillis();
        jda = botInstance.getJDA();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            DatabaseInitializer.load();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.getPresence().setActivity(Activity.watching("Zwierzęta w ZOO"));
        EventInitializer.load();
        CommandInitializer.load();
        for(long id : colorIds){
            rolesToRemove.add(jda.getRoleById(id));
        }
        for(long id : levelIds){
            rolesToRemove.add(jda.getRoleById(id));
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Bot loaded! [" + time + "ms]");
    }

    public static void reload(){
        try {
            FileInitializer.load();
            DatabaseInitializer.load();
        } catch (Exception exception) {
            System.exit(1);
        }
    }

    public static JDA getJDA(){
        return botInstance.getJDA();
    }

    public static BotInstance getBotInstance(){
        return botInstance;
    }
}
