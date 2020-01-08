package com.example.studentsapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {
    DatabaseReference mdatabaseref;
    List<Information> local_list;
    List<Information> remote_list;
    InformationDao informationDao;
    private final String CHANNEL_ID="personal_notification";
    private final int NOTIFICATION_ID=001;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        local_list = new ArrayList<Information>();
        remote_list=new ArrayList<Information>();
        informationDao=InformationDataBase.getDatabase(getApplicationContext()).informationDao();
        local_list=informationDao.getInformation();
        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("global");




        mdatabaseref.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                remote_list.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    //Information inf = dataSnapshot1.getValue(Information.class);
                    String text = (String)dataSnapshot1.child("text").getValue();
                    String name = (String)dataSnapshot1.child("name").getValue();
                    String key = dataSnapshot1.getKey();
                    Information information= new Information(text,name,key);
                    remote_list.add(0, information);
                    if(!exist_in_list(information,local_list))
                    {

                        informationDao.insert(information);
                        if(!MainActivity.isVisible){
                            display();


                        Toast.makeText(NotificationService.this.getApplicationContext(),"Database changed",Toast.LENGTH_LONG).show();
                    }}



                }








            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NotificationService.this.getApplicationContext(),"Error", Toast.LENGTH_LONG).show();

            }

        });


        super.onCreate();
    }

    boolean exist_in_list(Information information,List<Information> local_list){
        boolean exist=false;
        for(Information i : local_list){
            if(information.getKey().equals(i.getKey())){
                exist=true;
            }
        }
        return  exist;

    }
    public  void display()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("body");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this,MainActivity.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(notificationId, mBuilder.build());
    }

}
