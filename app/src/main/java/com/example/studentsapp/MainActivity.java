package com.example.studentsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    DatabaseReference mdatabaseref;
    DatabaseReference ref;

    RecyclerView mRecyclerView;
    ArrayList<Information> list;
    Adapter adapter;
    java.util.Date date=Calendar.getInstance().getTime();
    DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

    String strDate=dateFormat.format(date);

    public static boolean isVisible=false;
    InformationDao informationDao;
    TextView textView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        informationDao=InformationDataBase.getDatabase(getApplicationContext()).informationDao();

        Intent startIntent = new Intent(getApplicationContext(), NotificationService.class);
        startService(startIntent);

        mRecyclerView = findViewById(R.id.myRecyclerview);
        textView=findViewById(R.id.textView3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Information>();

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");
        ref=FirebaseDatabase.getInstance().getReference();

        adapter = new Adapter(MainActivity.this,list);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        mdatabaseref.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




               list.clear();




                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       String text = (String)dataSnapshot1.child("text").getValue();
                       String name = (String)dataSnapshot1.child("name").getValue();
                       String key = dataSnapshot1.getKey();
                       Information information= new Information(text,name,key);
                       textView.setVisibility(View.GONE);
                       mRecyclerView.setVisibility(View.VISIBLE);

                       list.add(0, information);



                   }
                adapter.notifyDataSetChanged();




                   if(list.isEmpty())
                   {
                       mRecyclerView.setVisibility(View.GONE);
                       textView.setVisibility(View.VISIBLE);
                   }

                Query query = ref.child("global").orderByChild("date").equalTo(strDate);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            informationDao.deletebykey(dataSnapshot.getRef().getKey());

                            dataSnapshot1.getRef().removeValue();



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this,databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_LONG).show();

            }

        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible=false;
    }
}