package me.nikox.zwierzoinator.modules.purge;

public enum PurgeLevel {

    UNLIMITED(new long[]{510485268080492545L, 493012781944143874L, 493013306554974219L, 493037813160476674L, 808015582770888785L}),
    MP(new long[]{676493466485784596L}),
    PA(new long[]{585183155649118208L});

    long[] allowedRanks;

    PurgeLevel(long[] allowedRanks) {
        this.allowedRanks = allowedRanks;
    }

    public long[] getAllowedRanks() {
        return allowedRanks;
    }

}
