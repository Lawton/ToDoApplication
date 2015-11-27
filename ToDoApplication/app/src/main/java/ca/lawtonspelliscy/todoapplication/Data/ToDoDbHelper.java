package ca.lawtonspelliscy.todoapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is used to connect to SQLite DB on phone. Will create DB if necessarry and read and
 * write to the DB.
 * Created by lawton on 15-11-17.
 */
public class ToDoDbHelper extends SQLiteOpenHelper{


    public static final int DATABASE_VERSION =2;
    public static final String DATABASE_NAME = "ToDoList.db";

    public static final String TABLE_ITEM = "TODOITEMS";

    public static final String COLUMN_SUBJECT = "SUBJECT";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_DATE = "EVENT_DATE";
    public static final String COLUMN_COMPLETE = "COMPLETE";
    public static final String COLUMN_ROWID = "rowid";

    private static final String GET_ROWID = "SELECT last_insert_rowid()";



    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ITEM
                + " ( " + COLUMN_SUBJECT + " TEXT NOT NULL, "
                + COLUMN_COMPLETE + " INT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT "
                + ")");
    }

    /**
     * Insert item into the database to store all to-do items in the application. Once inserted
     * the items rowid will be updated.
     * @param item the item that is to be added to the database
     * @return whether the insert was successful or not
     */
    public boolean insertItem(ToDoItem item) {
        boolean insertSuccessful = false;
        int rowid=0;
        //put values into content values
        ContentValues values = new ContentValues();

        values.put(COLUMN_COMPLETE, item.getComplete());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_SUBJECT, item.getSubject());

        SQLiteDatabase db = this.getWritableDatabase();


        //attempt to insert column and return whether successful
        insertSuccessful = db.insert(TABLE_ITEM, null, values) > 0;
        //set the row id on the item
        item.setRowid(getRowID(db));

        db.close();

        return insertSuccessful;
    }

    private int getRowID(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        int rowid = cursor.getInt(0);
        cursor.close();
        return rowid;
    }

    /**
     * get Items saved on phone in database
     * @return array of to-do items
     */
    public ToDoItem[] readItems() {
        boolean successful = false;

        //Columns to be used by query
        String [] columns = {
                COLUMN_ROWID,
                COLUMN_COMPLETE,
                COLUMN_DESCRIPTION,
                COLUMN_SUBJECT
        };

        SQLiteDatabase db = this.getReadableDatabase();

        //currently no where claus on the query just grab all the items
        Cursor cursor = db.query(TABLE_ITEM,
                columns,
                null,
                null,
                null,
                null,
                null);


        //convert cursor to array and close cursor
        ToDoItem[] items = new ToDoItem[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0; !cursor.isAfterLast(); i++) {
            ToDoItem item = new ToDoItem(null, null);
            item.setRowid(cursor.getInt(cursor.getColumnIndex(COLUMN_ROWID)));
            item.setSubject(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT)));
            item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            item.setComplete(cursor.getInt(cursor.getColumnIndex(COLUMN_COMPLETE))==1);
            items[i] = item;
            cursor.moveToNext();
        }

        //We are done reading from the cursor so closing (leave this at the bottom of the class)
        cursor.close();
        db.close();
        return items;

    }

    public boolean deleteItem(int rowid) {
        boolean successful = false;
        String whereClaus = COLUMN_ROWID + "=" + rowid;
        SQLiteDatabase db = this.getWritableDatabase();

        successful = db.delete(TABLE_ITEM, whereClaus, null)>0;
        db.close();

        return successful;
    }

    /**
     * Update whether the item is complete or not in the db
     * @param complete state of item 1 for complete 0 for uncomplete
     * @param rowid the rowid of the item to be updates
     * @return whether any rows were updated successfully
     */
    public boolean updateItemComplete(int complete, int rowid) {
        boolean successful = false;
        ContentValues args = new ContentValues();

        //arguments to be updated
        args.put(COLUMN_COMPLETE, complete);

        successful = updateItem(args, rowid);

        return successful;
    }

    /**
     * Update description and subject field from item in db
     * @param item the item to be updated
     * @return whether any columns were updated
     */
    public boolean updateItemFull (ToDoItem item) {
        boolean successful = false;
        ContentValues args = new ContentValues();

        //arguments to be updated
        args.put(COLUMN_DESCRIPTION, item.getDescription());
        args.put(COLUMN_SUBJECT, item.getSubject());

        successful = updateItem(args, item.getRowid());

        return successful;
    }

    /**
     * does the extra few steps for updating an item taking contentvalue arguments and the rowid
     * to filter on
     * @param args arguments that are being passed to update statment to be updated
     * @param rowid the row id that will be used to filter
     * @return whether the update statement updated any rows
     */
    private boolean updateItem(ContentValues args, int rowid) {
        boolean successful = false;
        String strFilter = "rowid=" + rowid; //filter we use rowid is unique for each row
        SQLiteDatabase db = this.getWritableDatabase();

        successful = db.update(TABLE_ITEM, args, strFilter, null)>0; //if less than 0 nothing updated
        db.close();

        return successful;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }
}
