package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CreateNewDeck extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_deck);
    }

    EditText newDeck = findViewById(R.id.editText);
    Button btn = findViewById(R.id.button2);


    public void onClick(View view){
        mydb = new DBHelper(this);
        mydb.insertDeck(newDeck.getText().toString());
        LinkedHashMap<String, Integer> cardsToAdd = DeckList.finalList;
        String names = cardsToAdd.keySet().toArray().toString();
        ArrayList<Integer> numbers = new ArrayList<Integer>(cardsToAdd.values());
        mydb.insertIntoDeck(newDeck.getText().toString(), names, numbers.toString());
        Intent intent = new Intent(this, DeckList.class);
        startActivity(intent);
    }
}
