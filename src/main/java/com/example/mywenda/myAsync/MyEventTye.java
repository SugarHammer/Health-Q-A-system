package com.example.mywenda.myAsync;

public enum MyEventTye {

    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    MyEventTye(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
