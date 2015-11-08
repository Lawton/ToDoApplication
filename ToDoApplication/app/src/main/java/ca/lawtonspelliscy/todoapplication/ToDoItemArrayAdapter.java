package ca.lawtonspelliscy.todoapplication;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by lawton on 15-11-07.
 * This is the array adapter used to create each row of to-do items on the day view
 *
 */
public class ToDoItemArrayAdapter extends ArrayAdapter{

    public ToDoItemArrayAdapter(Context context, String[] values) {
        super(context, R.layout.task_row, values);
    }

}
