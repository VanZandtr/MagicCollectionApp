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

        ArrayList<String> cardsToBeAdded = (ArrayList<String>)getIntent().getStringArrayListExtra("LIST");
        Log.d("List", cardsToBeAdded.toString());
        

        Cursor rs;
        mydb = new DBHelper(this);
        mydb.deleteAllRows();
        mydb.insertRow("Test", 1);
        mydb.insertRow("Test2", 10);
        mydb.insertRow("Test3", 100);
        mydb.insertRow("Test4", 1000);
        ArrayList currentCollection = mydb.getCollection();
        for(int i = 0; i < mydb.numberOfRows(); i++) {
            Log.d("DB num rows", "" + mydb.numberOfRows());
            Log.d("DB row (" + i + "): ", "" + currentCollection.get(i).toString());

            rs = mydb.getData(i + 1);

            if( rs != null && rs.moveToFirst() ){
                Log.d("NAME (" + (i + 1) + "): ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_NAME)));
                Log.d("QUANTITY (" +  (i + 1) + "): ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY)));
                myCollection.put(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_NAME)), Integer.parseInt(rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY))));
                rs.close();
            }

        }

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

                if(mydb.findDuplicateRow(cardsToBeAdded.get(i)) == true){
                    //TODO ---- have this method return the id location ---- update row
                   // mydb.updateRow()
                }
                else{
                    //insert the card
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            CardList.collectionList.clear();
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
