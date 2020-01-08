package com.example.studentsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityEmpty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        TextView mTextview;
        mTextview = findViewById(R.id.Textview_empty);
        mTextview.setText("brak danych ");

    }
}
