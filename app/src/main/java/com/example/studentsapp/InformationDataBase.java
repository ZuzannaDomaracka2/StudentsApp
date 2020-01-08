package com.example.studentsapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("MoviesDatabase", "populating with data...");
                                   // new PopulateDbAsync(INSTANCE).execute();
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
