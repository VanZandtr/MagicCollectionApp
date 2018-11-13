package com.example.rlv220.mtg_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String COLLECTION_TABLE_NAME = "collection";
    public static final String COLLECTION_COLUMN_QUANTITY = "quantity";
    public static final String COLLECTION_COLUMN_NAME = "name";

    public static final String COLLECTION_TABLE_NAME2 = "decks";
    public static final String COLLECTION_COLUMN_QUANTITY2= "cardQuantity";
    public static final String COLLECTION_COLUMN_NAME2 = "cardName";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table collection" + "(id integer primary key autoincrement, name text,quantity integer)"
        );

        db.execSQL(
                "create table decks" + "(id integer primary key autoincrement, deckName text, cardNames text, quantities text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS collection");
        db.execSQL("DROP TABLE IF EXISTS decks");
        onCreate(db);
    }

    public boolean insertRow (String name, Integer quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("quantity", quantity);
        db.insert("collection", null, contentValues);
        return true;
    }

    public boolean insertDeck (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.putNull("quantities");
        contentValues.putNull("cardNames");
        db.insert("decks", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from collection where id="+id+"", null );
        return res;
    }

    public Cursor getDeck(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from decks where name="+name+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, COLLECTION_TABLE_NAME);
        return numRows;
    }

    public int numberOfDecks(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, COLLECTION_TABLE_NAME2);
        return numRows;
    }

    public boolean updateRow (Integer id, String name, Integer quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("quantity", quantity);
        db.update("collection", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean insertIntoDeck (String deckName, String cardList, String quantityList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cardNames", cardList);
        contentValues.put("quantities", quantityList);
        db.update("decks", contentValues, "name = ? ", new String[] { deckName } );
        return true;
    }

    public Integer deleteRow (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("collection",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void deleteAllRows () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM collection");
    }


    public Cursor findDuplicateRow (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "SELECT id FROM collection WHERE name='"+name+"'", null );
        return res;
    }

    public Cursor getAllDecks (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM decks WHERE id='"+id+"'", null );
        return res;
    }

    public ArrayList<String> getCollection() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from collection", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLLECTION_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
