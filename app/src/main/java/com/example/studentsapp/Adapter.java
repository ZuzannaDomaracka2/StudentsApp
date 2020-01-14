package com.example.studentsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {



       Context context;
       ArrayList<Information> information;
       public Adapter(Context c, ArrayList<Information> inf)
       {
           context = c;
           information = inf;
       }

       public void replaceInformation(ArrayList<Information> arrayList){
           this.information=arrayList;


       }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


           myViewHolder.text.setText(information.get(i).getText());
           myViewHolder.name.setText(information.get(i).getName());



    }

    @Override
    public int getItemCount() {
        return information.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

            TextView text, name;

            public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            text=itemView.findViewById(R.id.text);
            name=itemView.findViewById(R.id.name);
        }


    }





}
