package in.mumbaitravellers.leaders.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import in.mumbaitravellers.leaders.DBHelper.DatabaseHelper;
import in.mumbaitravellers.leaders.R;
import in.mumbaitravellers.leaders.adapters.TourAdapter;
import in.mumbaitravellers.leaders.model.Expense;
import in.mumbaitravellers.leaders.model.Tour;

import static android.R.string.cancel;

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
        ListView tourList = (ListView) findViewById(R.id.tour_listview);

        tourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tour tour = new Tour();
                Expense expense = new Expense();
                int s = db.getEventID(position);
                expense.setEventId(s);
                Intent intent = new Intent(TourActivity.this, ExpenseActivity.class);
                intent.putExtra("EventID", db.getEventID(position));
                intent.putExtra("EventName", db.getTourName(position + 1));
                intent.putExtra("EventStart", db.getStartDate(position + 1));
                intent.putExtra("EventEnd", db.getEndDate(position + 1));
                intent.putExtra("EventLeader", db.getLeader(position + 1));
                intent.putExtra("EventCash", db.getCashCarried(position + 1));
                intent.putExtra("EventOnTour", db.getOnTour(position + 1));

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
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (editEvent.getText().toString().trim().length() < 1 ||
                                                editStartDate.getText().toString().trim().length() < 1 ||
                                                editEndDate.getText().toString().trim().length() < 1 ||
                                                editLeader.getText().toString().trim().length() < 1 ||
                                                editCashCarried.getText().toString().trim().length() < 1 ||
                                                editOnTour.getText().toString().trim().length() < 1) {
                                            Snackbar.make(findViewById(android.R.id.content),
                                                    "Please fill all the details.", Snackbar.LENGTH_LONG)
                                                    .setActionTextColor(Color.RED)
                                                    .show();
                                        } else {

                                            Tour tour = new Tour();

                                            tour.setEventId((int) System.currentTimeMillis());
                                            tour.setEventName(editEvent.getText().toString());
                                            tour.setEventStartDate(editStartDate.getText().toString());
                                            tour.setEventEndDate(editEndDate.getText().toString());
                                            tour.setLeaders(editLeader.getText().toString());
                                            tour.setCashCarried(editCashCarried.getText().toString());
                                            tour.setOnTourCollection(editOnTour.getText().toString());

                                            long t = db.createTour(tour, new long[0]);

                                            Snackbar.make(findViewById(android.R.id.content),
                                                    "Event Added Successfully.", Snackbar.LENGTH_LONG)
                                                    .setActionTextColor(Color.RED)
                                                    .show();

                                            displayList();
                                        }

                                    }
                                })
                        .setNegativeButton(cancel, new DialogInterface.OnClickListener() {

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
        ListView tourListView = (ListView) findViewById(R.id.tour_listview);

        TourAdapter tourAdapter = new TourAdapter(
                this,
                R.layout.tour_list,
                db.getAllTours()
        );

        tourListView.setAdapter(tourAdapter);
    }
}
