package ca.lawtonspelliscy.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by lawton on 15-11-07.
 * This is the array adapter used to create each row of to-do items on the day view
 *
 */
public class ToDoItemArrayAdapter extends ArrayAdapter{
    private final Context mContext;
    private ArrayList<ToDoItem> mValues;

    /**
     * Custom Array adapter shows the subject and description of the to-do item as well as a
     * checkbox whether the item is complete or not.
     * @param context context of the array adapter
     * @param values list of each to-do item currently set for that day
     */
    public ToDoItemArrayAdapter(Context context, ArrayList<ToDoItem> values) {
        super(context, R.layout.task_row, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_row, parent, false);
        //TODO once we have a fragment for entering the description and subject need to properly populate those fields
        //TextView subjectField = (TextView)rowView.findViewById(R.id.row_subject);
        //TextView descriptionField = (TextView)rowView.findViewById(R.id.row_description);

        final ImageView checkboxImageView = (ImageView)rowView.findViewById(R.id.row_complete);
        checkboxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check box selectable here set to copmlete or uncomplete
                if(mValues.get(position).isComplete()) {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_off_background);
                    mValues.get(position).setComplete(false);
                } else {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_on_background);
                    mValues.get(position).setComplete(true);
                }
            }
        });

        return rowView;
    }

}
