package me.nikox.zwierzoinator.ship_module;

public enum ShipRequirement {

    NONE(0),
    ONE_USER(1),
    TWO_USERS(2);

    private int responseNumber;

    ShipRequirement(int response) {
        this.responseNumber = response;
    }

    public int getResponseNumber() {
        return this.responseNumber;
    }
}
