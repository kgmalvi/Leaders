package in.mumbaitravellers.leaders.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import in.mumbaitravellers.leaders.DBHelper.DatabaseHelper;
import in.mumbaitravellers.leaders.R;
import in.mumbaitravellers.leaders.model.Expense;

public class ExpenseActivity extends AppCompatActivity {


    public static int eventID;
    DatabaseHelper db;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(getApplicationContext());

        displayList();

        Expense expense = new Expense();
        eventID = getIntent().getExtras().getInt("EventID");
        expense.setEventId(eventID);
        /*Log.e("int.eventID:", String.valueOf(eventID));
        Log.e("expense.getEventID:", String.valueOf(expense.getEventId()));*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = ExpenseActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.activity_add_new_expense, null);
                final EditText editExpense = (EditText) content.findViewById(R.id.edtxt_expense);
                final EditText editDescription = (EditText) content.findViewById(R.id.edtxt_description);

                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseActivity.this);
                builder.setView(content)
                        .setTitle("Add Expense")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Expense expense = new Expense();
                                expense.setEventId(eventID);
                                expense.setAmount(editExpense.getText().toString());
                                expense.setDescription(editDescription.getText().toString());

                                /*Log.e("ID: ", String.valueOf(expense.getEventId()));
                                Log.e("Amount: ", expense.getAmount());
                                Log.e("Des: ", expense.getDescription());*/

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

        Button btnSend = (Button) findViewById(R.id.btn_SendMail);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"jogi444u@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Sheet");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Detail expense sheeet will appear here.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished ", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ExpenseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayList() {
        List<String> expenseList = db.getExpense(eventID);
        ListView expenseListView = (ListView) findViewById(R.id.expense_listview);

        expenseListView.setAdapter(new ArrayAdapter<String>(
                ExpenseActivity.this,
                R.layout.expense_list,
                R.id.text_amount,
                expenseList));
    }

}
