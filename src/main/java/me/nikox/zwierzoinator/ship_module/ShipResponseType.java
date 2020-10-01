package me.nikox.zwierzoinator.ship_module;

public enum ShipResponseType {

    GENERIC_LOW(0, 40),
    GENERIC_MEDIUM(41, 75),
    GENERIC_HIGH(76, 100),
    SPECIFIC_LOW(0, 40),
    SPECIFIC_MEDIUM(41, 75),
    SPECIFIC_HIGH(76, 100);

    private int minPercentage, maxPercentage;

    ShipResponseType(int minPercentage, int maxPercentage) {
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
    }

    public int getMinPercentage() {
        return this.minPercentage;
    }

    public int getMaxPercentage() {
        return this.maxPercentage;
    }

}
