package com.brennaswitzer.foodinger.model.plan;

public enum ItemType {
    PLAN(),
    SECTION(),
    TO_MAKE('M'),
    TO_BUY('B'),
    AD_HOC();

    private char initial;

    public char getInitial() {
        return initial;
    }

    ItemType() {
        this.initial = name().charAt(0);
    }

    ItemType(char initial) {
        this.initial = initial;
    }
}
