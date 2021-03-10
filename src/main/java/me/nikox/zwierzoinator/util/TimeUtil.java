package me.nikox.zwierzoinator.util;

public class TimeUtil {

    public enum Unit {

        MILLIS("ms", 0),
        SECONDS("s", 1000L),
        MINUTES("m", 60000L),
        HOURS("h", 3600000L),
        DAYS("d", 86400000L),
        WEEKS("w", 604800000L),
        MONTHS("mo", 2419200000L),
        YEARS("y", 31536000000L),
        PERMANENT("perm", -1);

        private String prefix;
        private long multiplier;

        Unit(String prefix, long multiplier){
            this.prefix = prefix;
            this.multiplier = multiplier;
        }

        public String getPrefix(){
            return this.prefix;
        }

        public long getMultiplier(){
            return this.multiplier;
        }
    }

    public static long getTimeMillis(String arg) {
        arg = arg.toLowerCase();
        int time = Integer.parseInt(arg.replaceAll("\\D+",""));
        String prefix = arg.replaceAll("([0-9])+", "");
        Unit unit = null;
        for(Unit u : Unit.values()){
            if(u.getPrefix().equalsIgnoreCase(prefix)){
                unit = u;
                break;
            }
        }
        if(unit == Unit.PERMANENT){
            return unit.getMultiplier();
        } else {
            return System.currentTimeMillis() + (time * unit.getMultiplier());
        }

    }


}
