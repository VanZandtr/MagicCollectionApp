package com.example.rlv220.mtg_project;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Cards {
    ArrayList<JSONObject> cards = new ArrayList<JSONObject>();

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


    public JSONObject getCard(JSONObject obj, String cardName){
        try {
            if(obj.get(cardName) != null) {
                return (JSONObject)obj.get(cardName);
                /*
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
                */
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<JSONObject> getCardAllCardsWithField(JSONObject obj, String type, String tag){
        ArrayList<JSONObject> cardList = new ArrayList<JSONObject>();
        try {
            ArrayList<JSONObject> cards = getAllCards(obj);
            for(int i = 0; i<cards.size(); i++){
                if (cards.get(i).get(type).equals(Integer.parseInt(tag))){
                    cardList.add(cards.get(i));
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return cardList;
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
