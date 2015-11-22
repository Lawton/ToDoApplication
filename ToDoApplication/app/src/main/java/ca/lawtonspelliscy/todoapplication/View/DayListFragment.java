package ca.lawtonspelliscy.todoapplication.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import ca.lawtonspelliscy.todoapplication.Data.ToDoDbHelper;
import ca.lawtonspelliscy.todoapplication.Data.ToDoItem;
import ca.lawtonspelliscy.todoapplication.R;
import ca.lawtonspelliscy.todoapplication.Data.ToDoItemArrayAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class DayListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static int REQUEST_CODE = 1;

    private static String LISTKEY = "DayListKey";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<ToDoItem> mToDoItems;

    //To store the date that the daylist represents
    private Calendar mDate;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ToDoItemArrayAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static DayListFragment newInstance(Calendar date) {
        DayListFragment fragment = new DayListFragment();

        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState == null) {
            mToDoItems = getItems();

        } else {
            mToDoItems = savedInstanceState.getParcelableArrayList(LISTKEY);
        }

        mAdapter = new ToDoItemArrayAdapter(this.getActivity().getApplicationContext(), mToDoItems);
    }

    private ArrayList<ToDoItem> getItems() {
        ToDoItem[] arrayItems = new ToDoDbHelper(getActivity().getApplication()).readItems();
        return new ArrayList<>(Arrays.asList(arrayItems));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(LISTKEY, mToDoItems);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daylist, container, false);

        // Set the adapter
        mListView = (ListView)view.findViewById(R.id.day_content_list);

        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //TODO get the empty text to display when list is empty
        this.setEmptyText("Nothing ToDo today!");

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        return view;
    }

    /**
     * Add or edit item in the listview. This will bring up the item Dialog to edit or create a new
     * entry in the listview.
     * @param subject - subject text of the item
     * @param description - the description text of the item
     * @param position - position of the item in the arraylist
     */
    private void addItem(String subject, String description, int position) {
        FragmentManager fragmentManager = getFragmentManager();
        ItemDialog itemDialog = ItemDialog.newInstance(subject, description, position);

        //Pass the request code and open the Item Fragment
        itemDialog.setTargetFragment(this, REQUEST_CODE);
        itemDialog.show(fragmentManager, "Item Dialog");
    }

    /**
     * Add or edit item in the listview. This will bring up the item Dialog to edit or create a new
     * entry in the listview.
     */
    private void addItem() {
        addItem("","",-1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String subject = data.getStringExtra(ItemDialog.ITEM_SUBJECT);
        String description = data.getStringExtra(ItemDialog.ITEM_DESCRIPTION);
        int position = data.getIntExtra(ItemDialog.ITEM_POSITION, -1);
        ToDoItem item = new ToDoItem(subject, description);

        if(position == -1) {
            mToDoItems.add(item);
        } else {
            mToDoItems.set(position, item);
        }


        new ToDoDbHelper(getActivity().getApplication()).insertItem(item);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction("hello");
        }
        */
        String description = mToDoItems.get(position).getDescription();
        String subject = mToDoItems.get(position).getSubject();
        addItem(subject, description, position);

    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
