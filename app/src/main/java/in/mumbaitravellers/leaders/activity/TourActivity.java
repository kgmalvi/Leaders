package in.mumbaitravellers.leaders.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

import in.mumbaitravellers.leaders.DBHelper.DatabaseHelper;
import in.mumbaitravellers.leaders.R;
import in.mumbaitravellers.leaders.model.Expense;
import in.mumbaitravellers.leaders.model.Tour;

public class TourActivity extends AppCompatActivity {

    DatabaseHelper db;
    private SimpleCursorAdapter simpleCursorAdapter;
    private LayoutInflater inflater;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        db = new DatabaseHelper(getApplicationContext());

        displayList();

        List<String> cs = db.getTours();
        ListView tourList = (ListView) findViewById(R.id.tour_listview);

        tourList.setAdapter(new ArrayAdapter<String>(
                TourActivity.this,
                R.layout.tour_list,
                R.id.tv_eventName,
                cs));

        tourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tour tour = new Tour();
                Expense expense = new Expense();
                int s = db.getEventID(position);
                //Log.e("EventID", "EVENT ID: " + s);
                expense.setEventId(s);
                Intent intent = new Intent(TourActivity.this, ExpenseActivity.class);
                intent.putExtra("EventID", s);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = TourActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.activity_add_new_trip, null);
                final EditText editEvent = (EditText) content.findViewById(R.id.edTxt_EventName);
                final EditText editStartDate = (EditText) content.findViewById(R.id.edTxt_EventSDate);
                final EditText editEndDate = (EditText) content.findViewById(R.id.edTxt_EventEDate);
                final EditText editLeader = (EditText) content.findViewById(R.id.edTxt_Leader);
                final EditText editCashCarried = (EditText) content.findViewById(R.id.edTxt_CashCarried);
                final EditText editOnTour = (EditText) content.findViewById(R.id.edTxt_TourCollection);

                AlertDialog.Builder builder = new AlertDialog.Builder(TourActivity.this);
                builder.setView(content)
                        .setTitle("Add Event")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Tour tour = new Tour();

                                tour.setEventId((int) System.currentTimeMillis());
                                tour.setEventName(editEvent.getText().toString());
                                tour.setEventStartDate(editStartDate.getText().toString());
                                tour.setEventEndDate(editEndDate.getText().toString());
                                tour.setLeaders(editLeader.getText().toString());
                                tour.setCashCarried(editCashCarried.getText().toString());
                                tour.setOnTourCollection(editOnTour.getText().toString());

                                long t = db.createTour(tour, new long[0]);

                                displayList();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void displayList() {
        List<String> cs = db.getTours();
        ListView ls = (ListView) findViewById(R.id.tour_listview);

        ls.setAdapter(new ArrayAdapter<String>(
                TourActivity.this,
                R.layout.tour_list,
                R.id.tv_eventName,
                cs));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tour, menu);
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
}
