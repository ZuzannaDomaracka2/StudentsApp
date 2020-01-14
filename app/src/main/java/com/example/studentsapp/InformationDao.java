
package com.example.studentsapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InformationDao {
    @Query("Select * FROM information")
    List<Information> getInformation();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Information information);

    @Query("DELETE FROM information WHERE `key` = :informarmationKey")
    abstract void deletebykey(String informarmationKey);


}

