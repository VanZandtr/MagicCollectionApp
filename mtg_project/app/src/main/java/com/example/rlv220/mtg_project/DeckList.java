package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.database.Cursor;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class DeckList extends AppCompatActivity {
    public static ArrayList<String> decklists;
    public static LinkedHashMap<String, Integer> finalList;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);

        final ArrayList<String> cardsToBeAdded = (ArrayList<String>) getIntent().getStringArrayListExtra("ADD");
        final ArrayList<String> cardsToBeRemoved = (ArrayList<String>) getIntent().getStringArrayListExtra("REMOVE");

        for (int i = 0; i < cardsToBeAdded.size(); i++) {
            for (int j = 0; j < cardsToBeRemoved.size(); j++) {
                if (cardsToBeAdded.get(i).contains(cardsToBeRemoved.get(j))) {
                    cardsToBeRemoved.remove(j);
                    break;
                } else if(finalList.containsKey(cardsToBeAdded.get(i))) {
                    finalList.put(cardsToBeAdded.get(i), finalList.get(cardsToBeAdded.get(i)) + 1);
                }
                else {
                    finalList.put(cardsToBeAdded.get(i),1);
                }
            }
        }

        Cursor rs;
        mydb = new DBHelper(this);
        for (int i = 0; i < mydb.numberOfDecks(); i++) {
            rs = mydb.getAllDecks(i + 1);
            if (rs != null && rs.moveToFirst()) {
                decklists.add(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_TABLE_NAME2)));
                rs.close();
            }
        }

            FloatingActionButton addToDeck = findViewById(R.id.fab4);
            addToDeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DeckList.this, CreateNewDeck.class);
                    startActivity(intent);
                }
            });

            FloatingActionButton fab2 = findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(DeckList.this, MyCollectionList.class);
                    startActivity(intent);
                }
            });

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DeckList.this, MainActivity.class);
                    startActivity(intent);
                }
            });


            RecyclerView rView = findViewById(R.id.decklists);
            //create layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false);
            rView.setLayoutManager(layoutManager);

            //create the adapter
            DeckListAdapter adapter = new DeckListAdapter(decklists);
            //attach the adapter
            rView.setAdapter(adapter);
        }
    }


