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
import java.util.List;

public class MyCollectionList extends AppCompatActivity {
    static public HashMap<String, Integer> myCollection = new HashMap<String, Integer>(); //Card ----> quantity
    static RecyclerView rView;
    DBHelper mydb;

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

        final ArrayList<String> cardsToBeAdded = (ArrayList<String>) getIntent().getStringArrayListExtra("ADD");
        Log.d("Add List", cardsToBeAdded.toString());

        final ArrayList<String> cardsToBeRemoved = (ArrayList<String>) getIntent().getStringArrayListExtra("REMOVE");
        Log.d("REMOVE List", cardsToBeAdded.toString());

        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int size = CollectionAdapter.data.size();
                Log.d("Size", size + "");
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        CollectionAdapter.data.remove(i);
                        Log.d("data", CollectionAdapter.data.toString() + "");
                    }
                    rView.getAdapter().notifyItemRangeChanged(0,size);
                    mydb.deleteAllRows();
                    myCollection.clear();
                    cardsToBeAdded.clear();
                    cardsToBeRemoved.clear();
                    Log.d("Table: ", mydb.getCollection().toString() + "");
                }
                finish();
                startActivity(getIntent());
            }
        });


        //load sql cards into myCollection List
        Cursor rs;
        mydb = new DBHelper(this);
        for (int i = 0; i < mydb.numberOfRows(); i++) {
            rs = mydb.getData(i + 1);
            if (rs != null && rs.moveToFirst()) {
                Log.d("NAME (" + (i + 1) + "): ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_NAME)));
                Log.d("QUANTITY (" + (i + 1) + "): ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY)));
                if (Integer.parseInt(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY))) > 0) {
                    myCollection.put(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_NAME)), Integer.parseInt(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY))));
                }
                rs.close();
            }
        }

            //add cards that need to be added
            for (int i = 0; i < cardsToBeAdded.size(); i++) {
                try {
                    //JSONObject newObj = new JSONObject(cardsToBeAdded.get(i));
                    Log.d("Hashmap: ", "" + myCollection.keySet().toString());
                    Log.d("In the the hashmap?", "" + (myCollection.get(cardsToBeAdded.get(i))));
                    if (myCollection.get(cardsToBeAdded.get(i)) instanceof Integer) {
                        Log.d("Duplicated Card Added", "" + cardsToBeAdded.get(i));
                        myCollection.put(cardsToBeAdded.get(i), myCollection.get(cardsToBeAdded.get(i)) + 1);
                    } else {
                        Log.d("New Card Added", "" + cardsToBeAdded.get(i));
                        myCollection.put(cardsToBeAdded.get(i), 1);
                    }

                    try {
                        Cursor cursor = mydb.findDuplicateRow(cardsToBeAdded.get(i));
                        cursor.moveToFirst();
                        int id = cursor.getInt(0);
                        mydb.updateRow(id, cardsToBeAdded.get(i), myCollection.get(cardsToBeAdded.get(i)));
                        Log.d("Duplicate Added to SQL", "" + cardsToBeAdded.get(i));
                    } catch (Exception e) {
                        mydb.insertRow(cardsToBeAdded.get(i), 1);
                        Log.d("Card Added to SQL", "" + cardsToBeAdded.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CardList.collectionList.clear();
            }

            //remove cards that need to be removed
            for (int i = 0; i < cardsToBeRemoved.size(); i++) {
                try {
                    //JSONObject newObj = new JSONObject(cardsToBeAdded.get(i));
                    Log.d("Hashmap: ", "" + myCollection.keySet().toString());
                    Log.d("In the the hashmap?", "" + (myCollection.get(cardsToBeRemoved.get(i))));
                    if (myCollection.get(cardsToBeRemoved.get(i)) instanceof Integer && myCollection.get(cardsToBeRemoved.get(i)) > 0) {
                        Log.d("Removing Card", "" + cardsToBeRemoved.get(i));
                        myCollection.put(cardsToBeRemoved.get(i), myCollection.get(cardsToBeRemoved.get(i)) - 1);

                        try {
                            Cursor cursor = mydb.findDuplicateRow(cardsToBeRemoved.get(i));
                            cursor.moveToFirst();
                            int id = cursor.getInt(0);
                            mydb.updateRow(id, cardsToBeRemoved.get(i), myCollection.get(cardsToBeRemoved.get(i)));
                            Log.d("Card Removed from SQL", "" + cardsToBeRemoved.get(i));
                        } catch (Exception e) {
                            Log.d("No card found", "");
                        }
                    } else {
                        Log.d("No cards left", "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CardList.removedCollectionList.clear();
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
