package com.example.lostandfound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.util.ArrayList;

public class adapter<myViewHolder> extends RecyclerView.Adapter<adapter.myViewHolder> {

    Context context;

    ArrayList<User> list;


    public adapter(Context context,ArrayList<User> list){
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.singlerow,parent,false);
       return new adapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.myViewHolder holder, int position) {

        User user = list.get(position);
        holder.name.setText(user.getName_lost());
        holder.roll.setText(user.getRoll_number_lost());
        holder.phone.setText(user.getPhone_number_lost());
        holder.item.setText(user.getItem_lost());
        holder.place.setText(user.getPlace_lost());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,roll,phone,item,place;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            roll = itemView.findViewById(R.id.roll_text);
            phone = itemView.findViewById(R.id.phone_text);
            item = itemView.findViewById(R.id.item_text);
            place = itemView.findViewById(R.id.place_text);

        }
    }

}
