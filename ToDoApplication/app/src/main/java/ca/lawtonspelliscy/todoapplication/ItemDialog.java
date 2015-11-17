package ca.lawtonspelliscy.todoapplication;

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


public class ItemDialog extends DialogFragment {

    public static String ITEM_DESCRIPTION = "Description";
    public static String ITEM_SUBJECT = "Subject";
    public static String ITEM_POSITION  = "Position";
    public static int RESULT_CODE = 2;

    private EditText mSubjectText;
    private EditText mDescriptionText;

    private String mSubject;
    private String mDescription;
    private int mPosition;


    public ItemDialog() {

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mSubject = args.getString(ITEM_SUBJECT);
        mDescription = args.getString(ITEM_DESCRIPTION);
        mPosition = args.getInt(ITEM_POSITION, -1);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ItemDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if(d !=null) {
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView saveText = (TextView)view.findViewById(R.id.item_save);

        mSubjectText = (EditText)view.findViewById(R.id.item_subject);
        if(mSubject!= null){
            mSubjectText.setText(mSubject);
        }
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
        return view;
    }

    private void sendData() {
        Intent intent = new Intent();
        if(mSubjectText.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Subject cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra(ITEM_DESCRIPTION, mDescriptionText.getText().toString());
            intent.putExtra(ITEM_SUBJECT, mSubjectText.getText().toString());
            intent.putExtra(ITEM_POSITION, mPosition);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE, intent);
            this.dismiss();

        }

    }

}
