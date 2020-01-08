
package com.example.studentsapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface InformationDao {
    @Query("Select * FROM information")
    List<Information> getInformation();

    @Insert
    void insert(Information information);

    @Query("DELETE FROM information WHERE `key` = :informarmationKey")
    abstract void deletebykey(String informarmationKey);


}

