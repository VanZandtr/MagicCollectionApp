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

    public ArrayList<JSONObject> getAllCardsWithField(JSONObject obj, String name, String expansion, String format, String colors, String types, String cmc, String power, String toughness, String artist){
        Log.d("Cards", "In getCardAllCardswithField");
        ArrayList<JSONObject> cardList = new ArrayList<JSONObject>();
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        if(name != ""){
            options.add("name");
            values.add(name);
            Log.d("Cards", name);
        }
        if(expansion != ""){
            options.add("printings");
            values.add(expansion);
            Log.d("Cards", expansion);
        }
        if(format != ""){
            options.add("legalities");
            values.add(format);
            Log.d("Cards", format);
        }
        if(colors != ""){
            options.add("colors");
            values.add(colors);
            Log.d("Cards", colors);
        }
        if(types != ""){
            options.add("types");
            values.add(types);
            Log.d("Cards", types);
        }
        if(cmc != ""){
            options.add("cmc");
            values.add(cmc);
            Log.d("Cards", cmc);
        }
        if(power != ""){
            options.add("power");
            values.add(power);
            Log.d("Cards", power);
        }
        if(toughness != ""){
            options.add("toughness");
            values.add(toughness);
            Log.d("Cards", toughness);
        }
        if(artist != ""){
            options.add("artist");
            values.add(artist);
            Log.d("Cards", artist);
        }
        Log.d("Options", "" + options.toString());
        Log.d("Values", "" + values.toString());
        try {
            ArrayList<JSONObject> cards = getAllCards(obj);
            for(int i = 0; i<cards.size(); i++){
                //Log.d("Card", "" + cards.get(i).toString());
                for(int j = 0; j<options.size(); j++){
                    try {
                        if (cards.get(i).get(options.get(j)).equals(values.get(j))) {
                            //Log.d("Card0", "" + cards.get(i));
                            cardList.add(cards.get(i));
                        }
                        else if(cards.get(i).get(options.get(j)) instanceof Integer){
                            if(Integer.parseInt(values.get(j)) == Integer.parseInt(cards.get(i).get(options.get(j)).toString())){
                                cardList.add(cards.get(i));
                            }
                        }
                        else{
                            ArrayList<String> multipleValuesList = new ArrayList<String>();
                            //Log.d("This should be True", "" + options.get(j) + cards.get(i).has(options.get(j)));
                            if(cards.get(i).has(options.get(j))){
                                JSONArray jArray = (JSONArray)cards.get(i).get(options.get(j));
                                //Log.d("jArray", "" + jArray.toString());
                                if (jArray != null) {
                                    if(values.get(j).contains(",")) {
                                        String[] split = values.get(j).split(",");
                                        for (int x = 0; x < split.length; x++) {
                                            multipleValuesList.add(split[x].replaceAll("\\s+","").toString());
                                        }
                                    }
                                    else{
                                        multipleValuesList.add(values.get(j).replaceAll("\\s+",""));
                                    }
                                    for (int k=0;k<jArray.length();k++){
                                            try {
                                                if (options.get(j).equals("legalities")) {
                                                    if (multipleValuesList.size() > 1) {
                                                        for (int x = 0; x < multipleValuesList.size(); x++) {
                                                            if (jArray.getJSONObject(k).toString().contains(multipleValuesList.get(x)) && !(jArray.getJSONObject(k).toString().contains("not_legal"))) {
                                                               // Log.d("Card3", "" + cards.get(i));

                                                                cardList.add(cards.get(i));
                                                            }
                                                        }
                                                    }
                                                    else if (jArray.getJSONObject(k).toString().contains(values.get(j)) && !(jArray.getJSONObject(k).toString().contains("not_legal"))) {
                                                        //Log.d("Card1", "" + cards.get(i));
                                                        //Log.d("jArray (temp)", " " + jArray.toString());
                                                        //Log.d("k", " " + k);
                                                        cardList.add(cards.get(i));
                                                    }
                                                    //Log.d("jArray (k)", " " + jArray.toString());
                                                }
                                                else if (jArray.get(k).equals(values.get(j)) || multipleValuesList.contains(jArray.get(k).toString())) {
                                                   // Log.d("Card4", "" + cards.get(i));

                                                    cardList.add(cards.get(i));
                                                }
                                            }catch (Exception x){
                                            // x.printStackTrace();
                                            }
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                       // e.printStackTrace();
                    }
                }
            }
        }catch(Exception e) {
            //e.printStackTrace();
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
            //ex.printStackTrace();
            return null;
        }
        return json;
    }
}
