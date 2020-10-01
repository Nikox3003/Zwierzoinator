package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.scheduler.Scheduler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IgrzyskaCommand extends Command {

    public static boolean isRunning = false;
    public static List<Member> topka = new ArrayList<>();

    public IgrzyskaCommand() {
        super(IgrzyskaCommand.class, "igrzyska", null, "Zapis na igrzyska");
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        String[] msg = e.getMessage().getContentRaw().split(" ");
        TextChannel channel = e.getChannel();
        Guild guild = e.getJDA().getGuildById(VariableHolder.TEST_GUILD_ID);
        Role zwierze = guild.getRoleById(VariableHolder.TEST_ZWIERZE_ID);
        if(msg.length > 1 && msg[1].equalsIgnoreCase("start") && e.getMember().hasPermission(Permission.ADMINISTRATOR)){
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    Random r = new Random();
                    int number = r.nextInt(VariableHolder.igrzyskaMembers.size());
                    Member member = VariableHolder.igrzyskaMembers.get(number);
                    VariableHolder.igrzyskaMembers.remove(member);
                    channel.sendMessage("**" + member.getEffectiveName() + "** wypada z teleturnieju! Pozostalo " + VariableHolder.igrzyskaMembers.size() + " zawodnikow!" ).queue();
                    if(VariableHolder.igrzyskaMembers.size() == 1){
                        e.getChannel().sendMessage(VariableHolder.igrzyskaMembers.get(0).getEffectiveName() + " wygrywa!").queue();
                        VariableHolder.igrzyskaMembers.clear();
                        isRunning = false;
                        Scheduler.cancelAllTasks();
                    }
                }
            };
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    channel.sendMessage("Igrzyska rozpoczęte :)").queue();
                    isRunning = true;
                }
            };
            Scheduler.scheduleRepeatingTask(run, 70, 10, TimeUnit.SECONDS);
            Scheduler.scheduleTask(r, 60, TimeUnit.SECONDS);
            e.getChannel().sendMessage("Zapisy rozpoczęte! Wpisz `!!igrzyska` aby dołączyć!").queue();
            return;
        }
        if(isRunning){
            e.getChannel().sendMessage("Nie mozesz juz dolaczyc :C").queue();
            return;
        }
        if(VariableHolder.igrzyskaMembers.contains(e.getMember())){
            e.getChannel().sendMessage("Jesteś już zapisany!").queue();
            return;
        }
        VariableHolder.igrzyskaMembers.add(e.getMember());
        e.getChannel().sendMessage("Zostałeś zapisany!").queue();
    }
}
