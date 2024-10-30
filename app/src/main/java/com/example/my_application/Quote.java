package com.example.my_application;

public class Quote {
    private String text;
    private String owner;

    public Quote(String text, String owner) {
        this.text = text;
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public String getOwner() {
        return owner;
    }
}
