package com.example.studentsapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mdatabaseref;
    RecyclerView mRecyclerView;
    ArrayList<Information> list;
    Adapter adapter;
    java.util.Date date=Calendar.getInstance().getTime();
    DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

    String strDate=dateFormat.format(date);
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.myRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Information>();

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");


        mdatabaseref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();


                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Information inf = dataSnapshot1.getValue(Information.class);
                    list.add(0,inf);

                }
                adapter = new Adapter(MainActivity.this,list);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_LONG).show();

            }

        });

        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("global").orderByChild("date").equalTo(strDate);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }
}
