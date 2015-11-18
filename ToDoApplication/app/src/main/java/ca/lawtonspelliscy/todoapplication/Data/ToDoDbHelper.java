package ca.lawtonspelliscy.todoapplication.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawton on 15-11-17.
 */
public class ToDoDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "ToDoList.db";


    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
