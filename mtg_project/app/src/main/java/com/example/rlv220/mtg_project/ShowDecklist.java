package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShowDecklist extends AppCompatActivity {
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_decklist);

        Intent intent = getIntent();
        String name = intent.getStringExtra("deckName");
        Log.d("deck name", name);mydb = new DBHelper(this);

        Cursor rs;
        mydb = new DBHelper(this);
        rs = mydb.getDeck(name);
        if (rs != null && rs.moveToFirst()) {
            Log.d("CARDS: ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_NAME2)));
            Log.d("QUANTITIES: ", "" + rs.getString(rs.getColumnIndex(DBHelper.COLLECTION_COLUMN_QUANTITY2)));
            rs.close();
        }
    }
}
