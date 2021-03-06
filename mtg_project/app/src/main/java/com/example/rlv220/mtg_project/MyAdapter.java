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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static String append = "";
    List<JSONObject> data;

    public MyAdapter(List<JSONObject> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v, viewGroup.getContext());

        return vh;
    }

    //MyViewHolder: the viewholder
    //i is the position of the item in your datastore
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try {
            //put card in textview
            myViewHolder.tv.setText(data.get(i).getString("name").toString());
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
        public EditText editText;
        public Context c;
        Button plus;
        Button minus;


        public MyViewHolder(@NonNull View view, Context c) {
            super(view);
            layout = view;
            tv = view.findViewById(R.id.textView);
            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            editText = view.findViewById(R.id.EditView);
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
                int add = 1;
                int minus = 1;
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
                        Cards currCardtoAdd = new Cards();
                        if(editText.getText().toString() != "" && Integer.parseInt(editText.getText().toString().replaceAll("[^0-9]","")) >= 0){
                            add = Integer.parseInt(editText.getText().toString().replaceAll("[^0-9]",""));
                            editText.setText("");
                        }
                        try {
                            for(int i = 0; i < add; i++) {
                                CardList.collectionList.add(currCardtoAdd.getCard(CardList.obj, tv.getText().toString()).getString("name"));
                            }
                        }catch (Exception e){}
                        Log.d("Card Added: ","" + currCardtoAdd.getCard(CardList.obj,tv.getText().toString()));
                        Log.d("List: ","" + CardList.collectionList.toString());
                        break;

                    case R.id.minus:
                        Toast.makeText(c, "Minus " + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        Cards currCardtoRemove = new Cards();
                        if(editText.getText().toString() != "" && Integer.parseInt(editText.getText().toString().replaceAll("[^0-9]","")) >= 0){
                            minus = Integer.parseInt(editText.getText().toString().replaceAll("[^0-9]",""));
                            editText.setText("");
                        }
                        try {
                            for(int i = 0; i < minus; i++) {
                                CardList.removedCollectionList.add(currCardtoRemove.getCard(CardList.obj, tv.getText().toString()).getString("name"));
                            }
                        }catch (Exception e){}
                        Log.d("Card to  be Removed: ","" + currCardtoRemove.getCard(CardList.obj,tv.getText().toString()));
                        Log.d("List: ","" + CardList.collectionList.toString());
                        break;
                }
            }
        }
    }
}