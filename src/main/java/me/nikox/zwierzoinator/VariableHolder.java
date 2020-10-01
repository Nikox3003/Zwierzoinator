package me.nikox.zwierzoinator;

import me.nikox.zwierzoinator.commands.ValueCommand;
import me.nikox.zwierzoinator.objects.Command;
import me.nikox.zwierzoinator.objects.Entry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VariableHolder {

    // hard coded
    public static final String ZWIERZE_ID = "493098512637100065";
    public static final String DZIENNIK_KAR_ID = "493430628625285133";
    public static final String WPISY_ID = "494174064240492544";
    public static final String WYPISY_ID = "603339935457738752";
    public static final String OFFICIAL_GUILD_ID = "493007787622662146";
    public static final String MIESO_ID = "493036892737241090";

    public static final String TEST_ZWIERZE_ID = "740609358211055726";
    public static final String TEST_GUILD_ID = "740602942804984249";
    public static final String TEST_DZIENNIK_KAR_ID = "740610382241988790";
    public static final String TEST_WPISY_ID = "740610399572983922";
    public static final String TEST_WYPISY_ID = "740610415607939174";
    public static final String TEST_MIESO_ID = "741308843174395955";


    public static HashMap<String, String> autoResponseList = new HashMap<>();
    public static List<Command> commandList = new ArrayList<>();
    public static List<Member> igrzyskaMembers = new ArrayList<>();
    public static List<Entry> entryList = new ArrayList<>();

    public static boolean szobMode = false;
    public static int registeredEscapeAttemps;

    public static ValueCommand.CasinoItem[] casinoItems = {
            new ValueCommand.CasinoItem(676460027397275649L, 10000),
            new ValueCommand.CasinoItem(551834608010919943L, 50000),
            new ValueCommand.CasinoItem(601688544972636189L, 125000),
            new ValueCommand.CasinoItem(595623026834276381L, 150000),
            new ValueCommand.CasinoItem(595623196733079555L, 175000),
            new ValueCommand.CasinoItem(551834783198609418L, 200000),
            new ValueCommand.CasinoItem(675690359065673749L, 225000),
            new ValueCommand.CasinoItem(551834786117713925L, 250000),
            new ValueCommand.CasinoItem(675723627357929473L, 275000),
            new ValueCommand.CasinoItem(551834792509833240L, 300000),
            new ValueCommand.CasinoItem(551834789607636992L, 350000),
            new ValueCommand.CasinoItem(701549423095709786L, 500000),
            new ValueCommand.CasinoItem(551834795706023948L, 700000),
            new ValueCommand.CasinoItem(676454279485325342L, 2000000),
            new ValueCommand.CasinoItem(551837817727221761L, 3000000),
            new ValueCommand.CasinoItem(675728950194077707L, 7500000)
    };

    public static long[] levelIds = {
            518067306752901130L,
            518066714047545344L,
            517408597588836377L,
            518065823315787778L,
            493125283314925592L,
            493124963117563915L,
            493125187068493845L,
            493106007304830976L,
            543218757317820417L,
    };

    public static long[] colorIds = {
            708024626168463472L,
            708025056412041258L,
            708025652967899266L,
            708025659552956487L,
            708026007608885248L,
            708026070892544033L,
            708026353487970305L,
            708026490687848509L,
            708026587760820296L,
            708026534769852456L,
    };

    public static String[] genericShipResponses = {
            "Ej stary, popatrz na górę. Widzisz to? Ten ship? Kto takie chore rzeczy wymyśla?",
            "Stary, takiego cringu nigdy nie widziałem.",

    };

    public static List<Role> rolesToRemove = new ArrayList<>();
}
