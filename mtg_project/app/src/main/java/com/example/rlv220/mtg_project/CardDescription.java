package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_description);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        Cards cards = new Cards();
        try {
            JSONObject obj = new JSONObject(cards.loadJSONFromAsset(this));
            JSONObject card = cards.getCard(obj, name);

            TextView textView = findViewById(R.id.test);
            textView.setText("" + card.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


