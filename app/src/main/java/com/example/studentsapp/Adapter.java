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
    OnListener mOnListener;

    public Adapter(Context c, ArrayList<Information> inf,OnListener onListener)
    {
        context = c;
        information = inf;
        this.mOnListener = onListener;
    }

    public void replaceInformation(ArrayList<Information> arrayList){
        this.information=arrayList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.row,viewGroup,false);
        return  new MyViewHolder(view,mOnListener);

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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView text, name;
        OnListener onListener;

        public MyViewHolder(@NonNull View itemView,OnListener onListener) {

            super(itemView);
            text=itemView.findViewById(R.id.text);
            name=itemView.findViewById(R.id.name);
            this.onListener=onListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onListener.onClick(getAdapterPosition());


        }
    }
    public  interface OnListener {
        void onClick(int position);
    }





}
