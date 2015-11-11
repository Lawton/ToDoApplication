package ca.lawtonspelliscy.todoapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainScreenActivity extends AppCompatActivity implements DayListFragment.OnFragmentInteractionListener{

    private ListView toDoListView;
    private ToDoItemArrayAdapter dayListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //only add fragment if not recreating activity
        if(savedInstanceState == null) {
            //dynamically add the fragment to the mainscreen
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DayListFragment dayListFragment = new DayListFragment();

            fragmentTransaction.add(R.id.main_fragment_container, dayListFragment);
            fragmentTransaction.commit();
        }


        //TODO Eventually move floating action bar to main activity instead of fragment
        /* For simplicity I moved the FAB to the DayListFragment but I think it would be better to
        have it on the main activity so that it is consitent for calendar and day view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDoItemsArrayList.add(new ToDoItem("example", "example"));
                dayListArrayAdapter.notifyDataSetChanged();

            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
