package com.example.studentsapp;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

@Database(entities={Information.class},version = 1)
public abstract class InformationDataBase extends RoomDatabase {
    private static InformationDataBase INSTANCE;
    private static final String DB_NAME = "Info.db";


    public static InformationDataBase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (InformationDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InformationDataBase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                }
                            })
                            .build();
                }
            }
        }

     return INSTANCE;
    }
    public abstract InformationDao informationDao();


}
