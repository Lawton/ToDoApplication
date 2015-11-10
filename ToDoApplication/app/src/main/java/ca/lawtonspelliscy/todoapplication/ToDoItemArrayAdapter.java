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

    public ToDoItemArrayAdapter(Context context, ArrayList<ToDoItem> values) {
        super(context, R.layout.task_row, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //TODO implement getview
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_row, parent, false);
        //TextView subjectField = (TextView)rowView.findViewById(R.id.row_subject);
        //TextView descriptionField = (TextView)rowView.findViewById(R.id.row_description);

        final ImageView checkboxImageView = (ImageView)rowView.findViewById(R.id.row_complete);
        checkboxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mValues.get(position).isComplete()) {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_off_background);
                    mValues.get(position).setComplete(false);
                } else {
                    checkboxImageView.setImageResource(android.R.drawable.checkbox_on_background);
                    mValues.get(position).setComplete(true);
                }
            }
        });

        //Set fields --currently set for testing but should eventually use actual values
        //TODO actually
        //subjectField.setText("Great Job");
        //descriptionField.setText("My App does something!");

        return rowView;
    }

}
