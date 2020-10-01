package me.nikox.zwierzoinator.commands;

import me.nikox.zwierzoinator.VariableHolder;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.util.CommandUtil;
import me.nikox.zwierzoinator.util.MessageUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import sun.awt.SunHints;

import java.util.List;

public class ValueCommand extends Command {

    public ValueCommand() {
        super(ValueCommand.class, "value", null, "value", "wartosc");
    }

    public static class CasinoItem {

        public long getRoleID() {
            return roleID;
        }

        public void setRoleID(long roleID) {
            this.roleID = roleID;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public CasinoItem(long roleID, int value) {
            this.roleID = roleID;
            this.value = value;
        }

        private long roleID;
        private int value;

    }


    @Override
    public void onCommand(GuildMessageReceivedEvent e) {
        StringBuilder builder = new StringBuilder("");
        String[] args = MessageUtil.getArgs(e.getMessage().getContentRaw());
        Member member;
        if(args.length < 2) {
            member = e.getMember();
        } else {
            member = CommandUtil.getArgMember(e, 1);
        }
        List<Role> roles = member.getRoles();
        int value = 0;
        for (CasinoItem casinoItem : VariableHolder.casinoItems) {
            if(roles.contains(e.getJDA().getRoleById(casinoItem.getRoleID()))) {
                value = value + casinoItem.getValue();
                builder.append("`").append(e.getJDA().getRoleById(casinoItem.getRoleID()).getName()).append("`\n");
            }
        }
        e.getChannel().sendMessage(builder.toString() + "\n----------------\nWartość rang: `" + value + "`\nWartość rang po odjeciu 25%: `" + (value * 0.75) + "`").queue();
    }
}
