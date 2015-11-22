package ca.lawtonspelliscy.todoapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawton on 15-11-17.
 */
public class ToDoDbHelper extends SQLiteOpenHelper{


    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "ToDoList.db";

    public static final String TABLE_ITEM = "TODOITEMS";

    public static final String COLUMN_SUBJECT = "SUBJECT";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_DATE = "EVENT_DATE";
    public static final String COLUMN_COMPLETE = "COMPLETE";



    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ITEM
                + " ( " + COLUMN_SUBJECT + " TEXT PRIMARY KEY NOT NULL, "
                + COLUMN_COMPLETE + " INT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + ")");
    }

    public boolean insertItem(ToDoItem item) {
        boolean insertSuccessful = false;

        ContentValues values = new ContentValues();

        values.put(COLUMN_COMPLETE, item.getComplete());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_SUBJECT, item.getSubject());

        SQLiteDatabase db = this.getWritableDatabase();

        insertSuccessful = db.insert(TABLE_ITEM, null, values) > 0;
        db.close();

        return insertSuccessful;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
