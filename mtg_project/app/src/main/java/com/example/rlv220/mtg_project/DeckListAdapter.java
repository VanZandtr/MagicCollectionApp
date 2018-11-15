package com.example.rlv220.mtg_project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.MyViewHolder> {
    List<String> data;

    public DeckListAdapter(List<String> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public DeckListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout3, viewGroup, false);

        DeckListAdapter.MyViewHolder vh = new DeckListAdapter.MyViewHolder(v, viewGroup.getContext());

        return vh;
    }

    //MyViewHolder: the viewholder
    //i is the position of the item in your datastore
    @Override
    public void onBindViewHolder(@NonNull DeckListAdapter.MyViewHolder myViewHolder, int i) {
        try {
            //put card in textview
            myViewHolder.tv.setText("" + data.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(data != null) {
            return data.size();
        }
        return 0;
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
            if (pos != RecyclerView.NO_POSITION) {
                int add = 1;
                int minus = 1;
                switch (view.getId()) {
                    case R.id.textView:
                        Toast.makeText(c, "" + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(c.getApplicationContext(), ShowDecklist.class);
                        intent.putExtra("deckName",tv.getText().toString());
                        c.startActivity(intent);
                        break;

                }
            }
        }
    }
}
