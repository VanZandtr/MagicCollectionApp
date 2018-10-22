package com.example.rlv220.mtg_project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ArrayList<JSONObject> cards = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.test);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            //Log.d("Num cards", "" + obj.length());

            String card = "Snapcaster Mage";
         //   getCard(obj, card);

            String field = "cmc";
            String tag = "2";
            getCardAllCardsWithField(obj, field, tag);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<JSONObject> getAllCards(JSONObject obj) {
        JSONArray array = obj.names();
        //Log.d("Array","" + array);
        JSONObject newobj = new JSONObject();
        for (int i = 0; i < array.length(); i++) {
            try {
                newobj = obj.getJSONObject(array.get(i).toString());
                //Log.d("card", "" + newobj + "(" + i + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
            cards.add(newobj);
        }
        return cards;
    }

    public void getCard(JSONObject obj, String cardName){
            try {
                if(obj.get(cardName) != null) {
                    JSONObject newobj = obj.getJSONObject(cardName);
                    Log.d("Card", "" + newobj);

                    String name = newobj.getString("name");
                    Log.d("name","" + name);

                    String manaCost = newobj.getString("manaCost");
                    Log.d("manaCost","" + manaCost);

                    String cmc = newobj.getString("cmc");
                    Log.d("cmc","" + cmc);

                    JSONArray subtypes = newobj.getJSONArray("subtypes");
                    Log.d("subtypes length", "" + subtypes.length());
                    for(int i = 0;i < subtypes.length(); i++){
                        Log.d("subtype","" + subtypes.get(i));
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
    }

    public void getCardAllCardsWithField(JSONObject obj, String type, String tag){
        try {
            ArrayList<JSONObject> cards = getAllCards(obj);
            for(int i = 0; i<cards.size(); i++){
                if (cards.get(i).get(type).equals(Integer.parseInt(tag))){
                    Log.d("test", "" + cards.get(i));
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("AllCards-x.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
