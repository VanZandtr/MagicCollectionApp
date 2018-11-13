package com.example.rlv220.mtg_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rlv220.mtg_project.CardDescription;
import com.example.rlv220.mtg_project.CardList;
import com.example.rlv220.mtg_project.Cards;
import com.example.rlv220.mtg_project.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CollectionAdapter extends RecyclerView.Adapter<com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder> {
    Context context;
    DBHelper mydb;

    static String append = "";
    public static HashMap<String, Integer> data;

    public CollectionAdapter(HashMap<String, Integer> data) {
        this.data = data;

    }


    @NonNull
    @Override
    public com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout2, viewGroup, false);

        com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder vh = new com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder(v, viewGroup.getContext());

        context = viewGroup.getContext();

        return vh;
    }

    //MyViewHolder: the viewholder
    //i is the position of the item in your datastore
    @Override
    public void onBindViewHolder(@NonNull final com.example.rlv220.mtg_project.CollectionAdapter.MyViewHolder myViewHolder, final int i) {
        try {
            myViewHolder.tv.setText("" + data.keySet().toArray()[i]);
            myViewHolder.tv2.setText("" + data.get(data.keySet().toArray()[i]).toString());
            myViewHolder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Removing a " + (String)myViewHolder.tv.getText().toString(), Toast.LENGTH_LONG).show();
                    mydb = new DBHelper(context);
                    int minus = 1;
                    if(myViewHolder.et.getText().toString() != "" && Integer.parseInt(myViewHolder.et.getText().toString().replaceAll("[^0-9]","")) >= 0){
                        minus = Integer.parseInt(myViewHolder.et.getText().toString().replaceAll("[^0-9]",""));
                    }
                    try{
                        Cursor cursor = mydb.findDuplicateRow((String)myViewHolder.tv.getText());
                        cursor.moveToFirst();
                        int id = cursor.getInt(0);
                        mydb.updateRow(id, (String)myViewHolder.tv.getText(), MyCollectionList.myCollection.get((String)myViewHolder.tv.getText()) - minus);
                        Log.d("Card Removed from SQL", "");
                    }catch(Exception e){
                        Log.d("No card found", "");
                    }

                    if(MyCollectionList.myCollection.get(((String)myViewHolder.tv.getText())) == 1 || minus >= MyCollectionList.myCollection.get(((String)myViewHolder.tv.getText()))){
                        Log.d("Removing Last Card", "");
                        MyCollectionList.myCollection.remove((String)myViewHolder.tv.getText().toString());
                        data.remove((String)myViewHolder.tv.getText().toString());
                        removeItem(i);


                    }
                    else{//MyCollectionList.myCollection.get(tv.getText().toString()) > 1)
                        Log.d("Removing a Card", "");
                        MyCollectionList.myCollection.put((String)myViewHolder.tv.getText().toString(), MyCollectionList.myCollection.get((String)myViewHolder.tv.getText().toString()) - minus);
                        myViewHolder.tv2.setText("" + MyCollectionList.myCollection.get((String)myViewHolder.tv.getText().toString()));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removeItem(int rowNum) {
        notifyItemRemoved(rowNum);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //create the view holder MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //data fields are the textview and a layout
        public TextView tv;
        public TextView tv2;
        public EditText et;
        public View layout;
        public Context c;
        Button plus;
        Button minus;


        public MyViewHolder(@NonNull View view, Context c) {
            super(view);
            layout = view;
            tv = view.findViewById(R.id.textView);
            tv2 = view.findViewById(R.id.textView11);
            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            et = view.findViewById(R.id.EditView);
            this.c = c;

            //attach the listener
            tv.setOnClickListener(this);
            plus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CollectionAdapter adapter = new CollectionAdapter(data);
            //get the position of the item
            int pos = getAdapterPosition();

            //make sure that the position is valid
            if(pos != RecyclerView.NO_POSITION){
                int add = 1;
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
                        Toast.makeText(c, "Adding another " + tv.getText().toString(), Toast.LENGTH_LONG).show();
                        if(et.getText().toString() != "" && Integer.parseInt(et.getText().toString().replaceAll("[^0-9]","")) >= 0){
                            add = Integer.parseInt(et.getText().toString().replaceAll("[^0-9]",""));
                            et.setText("");
                        }

                        if(MyCollectionList.myCollection.get(tv.getText().toString()) instanceof Integer){
                            Log.d("Adding Card", "");
                            MyCollectionList.myCollection.put(tv.getText().toString(), MyCollectionList.myCollection.get(tv.getText().toString()) + add);
                            tv2.setText("" + MyCollectionList.myCollection.get(tv.getText().toString()));
                        }
                        break;
                }
            }
        }
    }
}