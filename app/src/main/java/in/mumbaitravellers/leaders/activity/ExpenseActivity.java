package in.mumbaitravellers.leaders.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import in.mumbaitravellers.leaders.DBHelper.DatabaseHelper;
import in.mumbaitravellers.leaders.R;
import in.mumbaitravellers.leaders.adapters.ExpenseAdapter;
import in.mumbaitravellers.leaders.model.Expense;

public class ExpenseActivity extends AppCompatActivity {


    public static int eventID;
    static String eventName;
    static String eventStart;
    static String eventEnd;
    static String eventLeader;
    static String eventCash;
    static String eventOnTour;

    static int accommodation;
    static int food;
    static int guide;
    static int parking;
    static int personal;
    static int tips;
    static int tolls;
    static int transport;
    static int totalCash;
    static int totalExpense;
    static int cashReturn;
    DatabaseHelper db;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());

        Expense expense = new Expense();
        eventID = getIntent().getExtras().getInt("EventID");
        eventName = getIntent().getExtras().getString("EventName");
        eventStart = getIntent().getExtras().getString("EventStart");
        eventEnd = getIntent().getExtras().getString("EventEnd");
        eventLeader = getIntent().getExtras().getString("EventLeader");
        eventCash = getIntent().getExtras().getString("EventCash");
        eventOnTour = getIntent().getExtras().getString("EventOnTour");

        expense.setEventId(eventID);

       /* accommodation = db.addAccommodation(eventID);
        food = db.addFood(eventID);
        guide = db.addGuide(eventID);
        parking = db.addParking(eventID);
        personal = db.addPersonal(eventID);
        tips = db.addTips(eventID);
        tolls = db.addTolls(eventID);
        transport = db.addTransport(eventID);

        totalCash = Integer.parseInt(eventCash) + Integer.parseInt(eventOnTour);
        totalExpense = accommodation + food + guide + personal + parking
                + tips + tolls + transport;
        cashReturn = totalCash - totalExpense;*/

        displayList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = ExpenseActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.activity_add_new_expense, null);
                final EditText editExpense = (EditText) content.findViewById(R.id.edtxt_expense);
                final EditText editDescription = (EditText) content.findViewById(R.id.edtxt_description);
                final Spinner spinnerType = (Spinner) content.findViewById(R.id.spinner_type);

                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseActivity.this);
                builder.setView(content)
                        .setTitle("Add Expense")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Expense expense = new Expense();
                                expense.setEventId(eventID);
                                expense.setAmount(Integer.parseInt(editExpense.getText().toString()));
                                expense.setType(spinnerType.getSelectedItem().toString());
                                expense.setDescription(editDescription.getText().toString());

                                long t = db.createExpense(expense);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                sendEmail();
                break;
        }
        return true;
    }

    protected void sendEmail() {

        accommodation = db.addAccommodation(eventID);
        food = db.addFood(eventID);
        guide = db.addGuide(eventID);
        parking = db.addParking(eventID);
        personal = db.addPersonal(eventID);
        tips = db.addTips(eventID);
        tolls = db.addTolls(eventID);
        transport = db.addTransport(eventID);

        totalCash = Integer.parseInt(eventCash) + Integer.parseInt(eventOnTour);
        totalExpense = accommodation + food + guide + personal + parking
                + tips + tolls + transport;
        cashReturn = totalCash - totalExpense;

        List<Expense> allExpense = db.getAllExpenses(eventID);

        String s = "\n";
        for (int i = 0; i < allExpense.size(); i++) {
            s += "₹" + allExpense.get(i).getAmount() + "       " /*
                    + allExpense.get(i).getType() + "\t" */
                    + allExpense.get(i).getDescription() + "\n";
        }

        String[] TO = {"jogi444u@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        SpannableString styledString = new SpannableString(eventName + "\n\n"
                + eventStart + " to " + eventEnd
                + "\nLeader: " + eventLeader
                + "\nCash Carried: " + eventCash
                + "\nOn Tour Collection: " + eventOnTour
                + "\n\nTotal Cash: " + totalCash
                + "\n\n\n\nExpenses:"
                + "\n\nAccommodation: " + accommodation
                + "\nFood: " + food
                + "\nGuide: " + guide
                + "\nPersonal: " + personal
                + "\nParking: " + parking
                + "\nTips: " + tips
                + "\nTolls: " + tolls
                + "\nTransport: " + transport
                + "\n\nTotal Expense: " + totalExpense
                + "\nCash to be Return: " + cashReturn
                + "\n\n\nDetail Expenses:"
                + "\n\n" + s
        );


        styledString.setSpan(new RelativeSizeSpan(2f), 0, eventName.length(), 0);
        // make text bold
        styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, eventName.length(), 0);
        /*// underline text
        styledString.setSpan(new UnderlineSpan(), 13, 23, 0);
        // make text italic
        styledString.setSpan(new StyleSpan(Typeface.ITALIC), 25, 31, 0);
        // change text color
        styledString.setSpan(new ForegroundColorSpan(Color.GREEN), 48, 55, 0);
        // highlight text
        styledString.setSpan(new BackgroundColorSpan(Color.CYAN), 57, 68, 0);
        // make the subscript text smaller
        styledString.setSpan(new RelativeSizeSpan(0.5f), 87, 96, 0);*/


        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Sheet of " + eventName + " on " + eventStart);
        emailIntent.putExtra(Intent.EXTRA_TEXT, styledString);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ExpenseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayList() {
        ListView expenseListView = (ListView) findViewById(R.id.expense_listview);

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(
                this,
                R.layout.expense_list,
                db.getAllExpenses(eventID));

        expenseListView.setAdapter(expenseAdapter);
    }

}
