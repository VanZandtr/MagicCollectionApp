package com.example.rlv220.mtg_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rlv220.mtg_project.CardDescription;
import com.example.rlv220.mtg_project.CardList;
import com.example.rlv220.mtg_project.Cards;
import com.example.rlv220.mtg_project.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CollectionAdapter extends RecyclerView.Adapter<com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder> {

    static String append = "";
    HashMap<JSONObject, Integer> data;

    public CollectionAdapter(HashMap<JSONObject, Integer> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout, viewGroup, false);

        com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder vh = new com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder(v, viewGroup.getContext());

        return vh;
    }

    //MyViewHolder: the viewholder
    //i is the position of the item in your datastore
    @Override
    public void onBindViewHolder(@NonNull com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder myViewHolder, int i) {
        try {
            //put card in textview
            myViewHolder.tv.setText("" + data.get(data.keySet().toArray()[i]).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //create the view holder MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //data fields are the textview and a layout
        public TextView tv;
        public View layout;
        public Context c;
        Button plus;
        Button minus;


        public MyViewHolder(@NonNull View view, Context c) {
            super(view);
            layout = view;
            tv = view.findViewById(R.id.textView);
            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            this.c = c;

            //attach the listener
            tv.setOnClickListener(this);
            plus.setOnClickListener(this);
            minus.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //get the position of the item
            int pos = getAdapterPosition();

            //make sure that the position is valid
            if(pos != RecyclerView.NO_POSITION){
                Log.i("Success:", "it works");
                switch(view.getId()){
                    case R.id.textView:
                        Toast.makeText(c, "" + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        String name = tv.getText().toString();
                        Intent intent = new Intent(view.getContext(), CardDescription.class);
                        intent.putExtra("name", name);
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.plus:
                        Toast.makeText(c, "Plus " + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.minus:
                        Toast.makeText(c, "Minus " + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }
}