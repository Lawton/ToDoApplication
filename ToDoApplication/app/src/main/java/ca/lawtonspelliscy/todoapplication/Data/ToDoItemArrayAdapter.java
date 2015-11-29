package ca.lawtonspelliscy.todoapplication.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.lawtonspelliscy.todoapplication.R;

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
        //sortValues();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.task_row, parent, false);
        final ToDoItem item = mValues.get(position);


        TextView subjectField = (TextView)rowView.findViewById(R.id.row_subject);
        TextView descriptionField = (TextView)rowView.findViewById(R.id.row_description);
        subjectField.setText(item.getSubject());
        descriptionField.setText(item.getDescription());

        final ImageView checkboxImageView = (ImageView)rowView.findViewById(R.id.row_complete);
        checkboxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check box selectable here set to complete or uncomplete
                if(mValues.get(position).isComplete()) {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_off_background);
                    item.setComplete(false);
                } else {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_on_background);
                    item.setComplete(true);
                }
                new ToDoDbHelper(rowView.getContext()).updateItemComplete(item.getComplete(), item.getRowid());
                notifyDataSetChanged();
            }
        });
        if(item.isComplete()) {
            checkboxImageView.setImageResource(android.R.drawable.checkbox_on_background);
        }else {
            checkboxImageView.setImageResource(android.R.drawable.checkbox_off_background);
        }

        return rowView;
    }

    @Override
    public void notifyDataSetChanged() {
        sortValues();
        super.notifyDataSetChanged();
    }

    private void sortValues() {
        Collections.sort(mValues, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem item1, ToDoItem item2) {
                return item1.getComplete() - item2.getComplete();
            }
        });
        //this.notifyDataSetChanged();
    }

}
