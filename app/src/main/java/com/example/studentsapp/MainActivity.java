package com.example.studentsapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    DatabaseReference mdatabaseref;
    DatabaseReference ref;

    RecyclerView mRecyclerView;
    ArrayList<Information> list;
    Adapter adapter;
    Spinner mSpinner;
    ArrayList<String> lecturers;

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
        //PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(MyWorker.class,100, TimeUnit.SECONDS).build();
       // WorkManager.getInstance().enqueue(periodicWorkRequest);
        informationDao=InformationDataBase.getDatabase(getApplicationContext()).informationDao();

        Intent startIntent = new Intent(getApplicationContext(), NotificationService.class);
       // startService(startIntent);
        setAlarm1();

        mRecyclerView = findViewById(R.id.myRecyclerview);
        textView=findViewById(R.id.textView3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        lecturers=new ArrayList<>();
        lecturers = getLecturersFromInformation(list);
        mSpinner=findViewById(R.id.mySpinner);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lecturers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setPrompt("Wybierz z listy ");
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(lecturers.get(position).equals("Wszystkie ogłoszenia "))
                {
                    adapter.replaceInformation(list);
                }
                else {
                    adapter.replaceInformation(getInformationfromLecturer(lecturers.get(position), list));
                    Toast.makeText(MainActivity.this, "Wybrano", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");
        ref=FirebaseDatabase.getInstance().getReference();

        adapter = new Adapter(MainActivity.this,list);
        mRecyclerView.setAdapter(adapter);


        mdatabaseref.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




               list.clear();




                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       String text = (String)dataSnapshot1.child("body").getValue();
                       String name = (String)dataSnapshot1.child("lecturerName").getValue();
                       String key = dataSnapshot1.getKey();
                       Information information= new Information(text,name,key);
                       textView.setVisibility(View.GONE);
                       mRecyclerView.setVisibility(View.VISIBLE);

                       list.add(0, information);




                   }

                if(!lecturers.get(mSpinner.getSelectedItemPosition()).equals("Wszystkie ogłoszenia "))
                {
                    adapter.replaceInformation(getInformationfromLecturer(lecturers.get(mSpinner.getSelectedItemPosition()), list));


                }

                adapter.notifyDataSetChanged();



                   lecturers = getLecturersFromInformation(list);
                   arrayAdapter.clear();
                   arrayAdapter.addAll(lecturers);




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

    private ArrayList<String> getLecturersFromInformation(List<Information> listInformation) {
        ArrayList<String> lecturers = new ArrayList<>();
        lecturers.add("Wszystkie ogłoszenia ");
        for(Information i : listInformation){


           lecturers.add(i.getName());




        }
        Set<String> s = new LinkedHashSet<String>(lecturers);
        ArrayList<String> listToReturn = new ArrayList<>(s);
        return listToReturn;

    }

private ArrayList<Information> getInformationfromLecturer(String lecturerName,ArrayList<Information> information){

        ArrayList listToReturn=new ArrayList();
        for( Information i : information){
            if(i.getName().equals(lecturerName))
                listToReturn.add(i);

        }
        return listToReturn;
}
private  void setAlarm1()
{


    PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(MyWorker.class,15, TimeUnit.MINUTES).build();
    WorkManager.getInstance().enqueue(periodicWorkRequest);
    Toast.makeText(this," Work manager dziala ",Toast.LENGTH_SHORT).show();
}
/*
    private void setAlarm() {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        try {
            am.cancel(pi);
        }
        catch(Exception ignored){}


        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 15000, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
    */

}

