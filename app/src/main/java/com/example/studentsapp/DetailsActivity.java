package com.example.studentsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    DatabaseReference mdatabaseref;
    TextView mtextViewTitle;
    TextView mtextViewText;
    TextView mTextViewName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();
        mtextViewTitle = findViewById(R.id.textViewTitle);
        mtextViewText = findViewById(R.id.textViewText);
        mTextViewName = findViewById(R.id.textViewName);
        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");
        mdatabaseref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String text = (String) dataSnapshot1.child("body").getValue();
                    String name = (String) dataSnapshot1.child("lecturerName").getValue();
                    String date = (String) dataSnapshot1.child("date").getValue();
                    String key = dataSnapshot1.getKey();
                    Information information = new Information(name, text, key);
                    mTextViewName.setText(information.getName());
                    mtextViewText.setText(information.getText());
                }







            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}



