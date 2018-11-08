package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCollectionList extends AppCompatActivity {
    static public HashMap<String, Integer> myCollection = new HashMap<String, Integer>(); //Card ----> quantity
    static RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCollectionList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyCollectionList.this, CardList.class);
                startActivity(intent);
            }
        });

        ArrayList<String> cardsToBeAdded = (ArrayList<String>)getIntent().getStringArrayListExtra("LIST");
        Log.d("List", cardsToBeAdded.toString());

        //TODO -----------------------------------
        //load myCollection from SQLite
        //myCollection = ....

        for(int i = 0; i < cardsToBeAdded.size(); i ++){
            try {
                //JSONObject newObj = new JSONObject(cardsToBeAdded.get(i));
                Log.d("Hashmap: ", "" + myCollection.keySet().toString());
                Log.d("In the the hashmap?", "" + (myCollection.get(cardsToBeAdded.get(i))));
                if(myCollection.get(cardsToBeAdded.get(i)) instanceof Integer){
                    Log.d("Duplicated Card Added", "" + cardsToBeAdded.get(i));
                    myCollection.put(cardsToBeAdded.get(i), myCollection.get(cardsToBeAdded.get(i)) + 1);
                }
                else{
                    Log.d("New Card Added", "" + cardsToBeAdded.get(i));
                    myCollection.put(cardsToBeAdded.get(i),1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        rView = findViewById(R.id.theView);
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
