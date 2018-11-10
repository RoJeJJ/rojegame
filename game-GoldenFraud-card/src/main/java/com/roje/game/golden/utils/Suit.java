package com.roje.game.golden.utils;

public enum Suit {
    ex235(7),
    high(1),
    pair(2),
    straight(3),
    flash(4),
    straight_flash(5),
    three_kind(6);

    private int type;

    private int next;

    Suit(int i) {
        type = i;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNext() {
        return next;
    }
}
