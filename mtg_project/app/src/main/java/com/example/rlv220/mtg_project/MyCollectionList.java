package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCollectionList extends AppCompatActivity {
    HashMap<JSONObject, Integer> myCollection = new HashMap<JSONObject, Integer>(); //Card ----> quantity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection_list);

        ArrayList<String> cardsToBeAdded = (ArrayList<String>)getIntent().getStringArrayListExtra("LIST");
        Log.d("List", cardsToBeAdded.toString());

        //TODO -----------------------------------
        //load myCollection from SQLite
        //myCollection = ....

        for(int i = 0; i < cardsToBeAdded.size(); i ++){
            try {
                JSONObject newObj = new JSONObject(cardsToBeAdded.get(i));
                if(myCollection.containsKey(newObj)){
                    myCollection.put(newObj, myCollection.get(newObj) + 1);
                }
                else{
                    myCollection.put(newObj,1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        RecyclerView rView = findViewById(R.id.theView);
        //create layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(layoutManager);

        //create the adapter
        CollectionAdapter adapter = new CollectionAdapter(myCollection);
        //attach the adapter
        rView.setAdapter(adapter);

    }
}
