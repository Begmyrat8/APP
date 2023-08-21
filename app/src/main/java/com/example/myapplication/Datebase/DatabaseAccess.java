package com.example.myapplication.Datebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Models.Word;
import com.example.myapplication.Models.Words;
import com.example.myapplication.WordDict;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instances;
    Cursor c = null;

    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstances(Context context) {
        if (instances == null) {
            instances = new DatabaseAccess(context);
        }
        return instances;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<WordDict> getAllData() {
        c = database.rawQuery("select * from words", null);
        List<WordDict> stringArrayList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String lang = c.getString(2);

            stringArrayList.add(new WordDict(id, title, lang));
        }
        return stringArrayList;
    }

    public List<Words> getAllBookmark() {
        c = database.rawQuery("select * from bookmark Order by id Desc", null);
        List<Words> stringArrayList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String lang = c.getString(2);


            stringArrayList.add(new Words(id, title, lang));
        }
        return stringArrayList;
    }

    public List<Words> getAllHistory() {
        c = database.rawQuery("select * from history  Order by id Desc", null);
        List<Words> stringArrayList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String lang = c.getString(2);


            stringArrayList.add(new Words(id, title, lang));
        }
        return stringArrayList;
    }

    @SuppressLint("Range")
    public Word getWordFromBookmark(String title) {
        c = database.rawQuery("Select * from bookmark Where upper([title]) = upper(?)", new String[]{title});
        Word words = null;
        while (c.moveToNext()) {
            words = new Word();
            words.title = c.getString(c.getColumnIndex("title"));
        }

        return words;
    }

    public List<Words> getAllGermanCategoryData(String categoryNumber, String catLang) {
        c = database.rawQuery("select * from words where category = '" + categoryNumber + "' and lang = '" + catLang + "';", null);
        List<Words> stringArrayList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String lang = c.getString(2);


            stringArrayList.add(new Words(id, title, lang));
        }
        return stringArrayList;
    }

}

