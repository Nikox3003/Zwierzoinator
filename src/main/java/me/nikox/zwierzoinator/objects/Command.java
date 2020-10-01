package me.nikox.zwierzoinator.objects;

import me.nikox.zwierzoinator.VariableHolder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static me.nikox.zwierzoinator.VariableHolder.commandList;

public abstract class Command {

    private Class<? extends Command> aClass;
    private String name;
    private Permission permission;
    private String usage;
    private List<String> aliases;

    public Command(Class<? extends Command> invokerClass, String name, Permission permission, String usage, String... aliases) {
        this.aClass = invokerClass;
        this.name = name;
        this.permission = permission;
        this.usage = usage;
        this.aliases = Arrays.asList(aliases);
    }

    public void execute(GuildMessageReceivedEvent e)  {
        Constructor<?> constructor;
        Object object = null;
        try {
            constructor = this.aClass.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        Method method = null;
        try {
            method = aClass.getDeclaredMethod("onCommand", GuildMessageReceivedEvent.class);
            method.invoke(object, e);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public Class<? extends Command> getaClass() {
        return aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public abstract void onCommand(GuildMessageReceivedEvent e);
}
