package ca.lawtonspelliscy.todoapplication.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.lawtonspelliscy.todoapplication.R;


public class ItemDialog extends DialogFragment {

    public static String ITEM_DESCRIPTION = "Description";
    public static String ITEM_SUBJECT = "Subject";
    public static String ITEM_POSITION  = "Position";
    public static String ITEM_ROWID = "rowid";
    public static int RESULT_CODE = 2;
    public static int RESULT_CODE_DELETE = 3;

    private EditText mSubjectText;
    private EditText mDescriptionText;

    private String mSubject;
    private String mDescription;
    private int mPosition;
    private int mRowid;


    public ItemDialog() {

    }

    /**
     * Creates a new instance of ItemDialog with arguments set.
     * @param subject the subject of the item. If the dialog is editing an existing item pass the subject - otherwise
     *                use an empty string
     * @param description the description of the item. If the dialog is editing an existing item pass the
     *                    description field text otherwise use an empty string.
     * @param position the position of the item in the listview. If the item is new pass -1.
     * @return a new instance of ItemDialog with its arguments set.
     */
    public static ItemDialog newInstance(String subject, String description, int position, int rowid) {
        ItemDialog fragment = new ItemDialog();

        //Create bundle for arguments and pass them to the new fragment
        Bundle args = new Bundle();
        args.putString(ITEM_SUBJECT, subject);
        args.putString(ITEM_DESCRIPTION, description);
        args.putInt(ITEM_POSITION, position);
        args.putInt(ITEM_ROWID, rowid);
        fragment.setArguments(args);

        //return new instance of ItemDialog
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mSubject = args.getString(ITEM_SUBJECT);
        mDescription = args.getString(ITEM_DESCRIPTION);
        mPosition = args.getInt(ITEM_POSITION, -1);
        mRowid = args.getInt(ITEM_ROWID, -1);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ItemDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        //Make sure that the dialog is fullscreen
        if(d !=null) {
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView saveText = (TextView)view.findViewById(R.id.item_save);
        TextView deleteText = (TextView)view.findViewById(R.id.item_delete);

        //Get the subject editText box and set the text if subject was passed
        mSubjectText = (EditText)view.findViewById(R.id.item_subject);
        if(mSubject!= null){
            mSubjectText.setText(mSubject);
        }
        //Get the description editText box and set the text if the description was passed
        mDescriptionText = (EditText)view.findViewById(R.id.item_description);
        if(mDescription!=null) {
            mDescriptionText.setText(mDescription);
        }

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
        return view;
    }

    private void sendData() {
        if(mSubjectText.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Subject cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = createIntent();
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE, intent);
            this.dismiss();

        }

    }

    private void deleteItem() {
        Intent intent = createIntent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_DELETE, intent);
        this.dismiss();
    }

    private Intent createIntent() {
        Intent intent = new Intent();
        intent.putExtra(ITEM_DESCRIPTION, mDescriptionText.getText().toString());
        intent.putExtra(ITEM_SUBJECT, mSubjectText.getText().toString());
        intent.putExtra(ITEM_POSITION, mPosition);
        intent.putExtra(ITEM_ROWID,mRowid);
        return intent;
    }

}
