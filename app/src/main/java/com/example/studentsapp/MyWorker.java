package com.example.studentsapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        //display(getApplicationContext());
        return Result.success();
    }



}

