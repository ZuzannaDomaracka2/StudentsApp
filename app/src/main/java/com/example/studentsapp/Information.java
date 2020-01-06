package com.example.studentsapp;

public class Information {
    String text;
    String name;

    public Information() {
    }

    public Information(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
