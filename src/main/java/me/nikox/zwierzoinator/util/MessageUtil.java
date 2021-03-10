package me.nikox.zwierzoinator.util;

import me.nikox.zwierzoinator.boot.FileInitializer;
import me.nikox.zwierzoinator.objects.Variable;
import net.dv8tion.jda.api.entities.MessageChannel;

public class MessageUtil {


    public static String getTextArgument(String[] args, int begin) {
        StringBuilder arg = new StringBuilder();
        for(int i = begin;i<args.length;i++) {
            arg.append(args[i]).append(" ");
        }
        return arg.toString();
    }

    public static void sendMessage(String message, MessageChannel channel, Variable... variables) {
        for (Variable variable : variables) {
            message = message.replace(variable.getVariable(), variable.getReplacement());
        }
        message = message.replace("{@prefix}", FileInitializer.getConfig().getProperty("prefix"));
        channel.sendMessage(message).queue();
    }

    public static String[] getArgs(String msg) {
        String[] args = msg.split(" ");
        int i = 0;
        for(String s : args){
            if(s.isEmpty()){
                if(i < args.length){
                    args[i] = args[i + 1];
                }
            }
            i++;
        }
        return args;
    }
}
