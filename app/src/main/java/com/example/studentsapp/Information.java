package com.example.studentsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class Information {
    String text;
    String name;
    @NonNull
    @PrimaryKey
    String key;



    public Information(String text, String name,@NonNull String key) {
        this.text = text;
        this.name = name;
        this.key =key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
