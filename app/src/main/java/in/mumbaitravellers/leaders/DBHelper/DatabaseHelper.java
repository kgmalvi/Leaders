package in.mumbaitravellers.leaders.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.mumbaitravellers.leaders.model.Expense;
import in.mumbaitravellers.leaders.model.Tour;

/**
 * Created by Administrator on 24-10-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_EVENTNAME = "eventName";
    public static final String KEY_LEADERS = "leaders";

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 16;

    // Database Name
    private static final String DATABASE_NAME = "ExpenseSheet";

    // Table Names
    private static final String TABLE_TOUR = "tours";
    private static final String TABLE_EXPENSE = "expenses";
    private static final String TABLE_TOUR_EXPENSE = "tours_expense";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    // TOURS Table - column names
    private static final String KEY_EVENTID = "eventId";
    private static final String KEY_EVENTSTARTDATE = "eventStartDate";
    private static final String KEY_EVENTENDDATE = "eventEndDate";
    private static final String KEY_CASHCARRIED = "cashCarried";
    private static final String KEY_ONTOURCOLLECTION = "onTourCollection";

    // EXPENSES Table - column names
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DESCRIPTION = "description";

    // NOTE_TAGS Table - column names
    private static final String KEY_TOUR_ID = "tour_id";
    private static final String KEY_EXPENSE_ID = "expense_id";

    // Table Create Statements
    // Tour table create statement
    private static final String CREATE_TABLE_TOUR = "CREATE TABLE "
            + TABLE_TOUR + "(" + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_EVENTID + " INTEGER," + KEY_EVENTNAME
            + " TEXT," + KEY_EVENTSTARTDATE + " TEXT," + KEY_EVENTENDDATE + " TEXT," + KEY_LEADERS + " TEXT,"
            + KEY_CASHCARRIED + " TEXT," + KEY_ONTOURCOLLECTION + " TEXT,"
            + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";

    // Expense table create statement
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EVENTID + " INTEGER,"
            + KEY_AMOUNT + " INTEGER," + KEY_TYPE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
            + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";

    // tour_expense table create statement
    private static final String CREATE_TABLE_TOUR_EXPENSE = "CREATE TABLE "
            + TABLE_TOUR_EXPENSE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TOUR_ID + " INTEGER," + KEY_EXPENSE_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME, " + KEY_UPDATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // ------------------------ Creating New Tables ----------------//

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating all required tables.
        db.execSQL(CREATE_TABLE_TOUR);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_TOUR_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // OnUpgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOUR_EXPENSE);

        // Create new tables
        onCreate(db);
    }

    // ------------------------ "tour" table methods ----------------//

    /*
    * Creating a Tour
    */
    public long createTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENTID, tour.getEventId());
        values.put(KEY_EVENTNAME, tour.getEventName());
        values.put(KEY_EVENTSTARTDATE, tour.getEventStartDate());
        values.put(KEY_EVENTENDDATE, tour.getEventEndDate());
        values.put(KEY_LEADERS, tour.getLeaders());
        values.put(KEY_CASHCARRIED, tour.getCashCarried());
        values.put(KEY_ONTOURCOLLECTION, tour.getOnTourCollection());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());

        // insert row
        long tour_id = db.insert(TABLE_TOUR, null, values);

        Log.e("Tour", String.valueOf(tour_id));

        return tour_id;
    }

    /*
     * get single tour
     */
    public Tour getTour(long tour_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TOUR + " WHERE "
                + KEY_ID + " = " + tour_id;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Tour td = new Tour();
        td.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
        td.setEventName(c.getString(c.getColumnIndex(KEY_EVENTNAME)));
        td.setEventStartDate(c.getString(c.getColumnIndex(KEY_EVENTSTARTDATE)));
        td.setEventEndDate(c.getString(c.getColumnIndex(KEY_EVENTENDDATE)));
        td.setLeaders(c.getString(c.getColumnIndex(KEY_LEADERS)));
        td.setCashCarried(c.getString(c.getColumnIndex(KEY_CASHCARRIED)));
        td.setOnTourCollection(c.getString(c.getColumnIndex(KEY_ONTOURCOLLECTION)));

        return td;
    }

    /*
    * getting all tours
    * */
    public List<Tour> getAllTours() {
        List<Tour> tours = new ArrayList<Tour>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOUR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tour td = new Tour();
                td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
                td.setEventName(c.getString(c.getColumnIndex(KEY_EVENTNAME)));
                td.setEventStartDate(c.getString(c.getColumnIndex(KEY_EVENTSTARTDATE)));
                td.setEventEndDate(c.getString(c.getColumnIndex(KEY_EVENTENDDATE)));
                td.setLeaders(c.getString(c.getColumnIndex(KEY_LEADERS)));
                td.setCashCarried(c.getString(c.getColumnIndex(KEY_CASHCARRIED)));
                td.setOnTourCollection(c.getString(c.getColumnIndex(KEY_ONTOURCOLLECTION)));

                // adding to tour list
                tours.add(td);
            } while (c.moveToNext());
        }

        return tours;
    }

    public int getEventID(int pos) {
        String query = "SELECT * FROM " + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        int s = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Tour t = new Tour();
                t.setEventId(cursor.getInt(cursor.getColumnIndex(KEY_EVENTID)));
                s = cursor.getInt(cursor.getColumnIndex(KEY_EVENTID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return s;
    }

    public List<String> getTours() {
        String[] columns = new String[]{KEY_EVENTNAME};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_TOUR, columns, null, null, null, null,
                null);
        String results = "";
        List<String> result = new ArrayList<String>();
        int iCM = c.getColumnIndex(KEY_EVENTNAME);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result.add(c.getString(iCM));
        }
        return result;
    }

    /*
    * getting tour count
    */
    public int getTourCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TOUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public String getTourName(int pos) {
        String countQuery = "SELECT " + KEY_EVENTNAME + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_EVENTNAME));
        }
        cursor.close();

        return count;
    }

    public String getStartDate(int pos) {
        String countQuery = "SELECT " + KEY_EVENTSTARTDATE + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_EVENTSTARTDATE));
        }
        cursor.close();

        return count;
    }

    public String getEndDate(int pos) {
        String countQuery = "SELECT " + KEY_EVENTENDDATE + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_EVENTENDDATE));
        }
        cursor.close();

        return count;
    }

    public String getLeader(int pos) {
        String countQuery = "SELECT " + KEY_LEADERS + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_LEADERS));
        }
        cursor.close();

        return count;
    }

    public String getCashCarried(int pos) {
        String countQuery = "SELECT " + KEY_CASHCARRIED + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_CASHCARRIED));
        }
        cursor.close();

        return count;
    }

    public String getOnTour(int pos) {
        String countQuery = "SELECT " + KEY_ONTOURCOLLECTION + " FROM "
                + TABLE_TOUR + " WHERE " + KEY_ID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String count = null;

        if (cursor.moveToNext()) {
            count = cursor.getString(cursor.getColumnIndex(KEY_ONTOURCOLLECTION));
        }
        cursor.close();

        return count;
    }

    /*
     * Updating a tour
     */
    public int updateTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENTNAME, tour.getEventName());
        values.put(KEY_EVENTSTARTDATE, tour.getEventStartDate());
        values.put(KEY_EVENTENDDATE, tour.getEventEndDate());
        values.put(KEY_LEADERS, tour.getLeaders());
        values.put(KEY_CASHCARRIED, tour.getCashCarried());
        values.put(KEY_ONTOURCOLLECTION, tour.getOnTourCollection());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());

        Log.e("query", KEY_ID + " = ?" + "   " +
                new String[]{String.valueOf(tour.getId())});

        // updating row
        return db.update(TABLE_TOUR, values, KEY_ID + " = 1",
                new String[]{String.valueOf(tour.getId())});
    }

    public int updatesTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENTNAME, tour.getEventName());
        values.put(KEY_EVENTSTARTDATE, tour.getEventStartDate());
        values.put(KEY_EVENTENDDATE, tour.getEventEndDate());
        values.put(KEY_LEADERS, tour.getLeaders());
        values.put(KEY_CASHCARRIED, tour.getCashCarried());
        values.put(KEY_ONTOURCOLLECTION, tour.getOnTourCollection());
        values.put(KEY_UPDATED_AT, getDateTime());

        int i = db.update(TABLE_TOUR, values, KEY_ID + "=?", new String[]{String.valueOf(tour.getId() + 1)});
        Log.e("I=", String.valueOf(i));
        return i;
    }

    /*
    * Deleting a tour
    */
    public void deleteTour(long tour_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOUR, KEY_ID + " = ?",
                new String[]{String.valueOf(tour_id)});
    }


    // ------------------------ "expense" table methods ----------------//

    /*
     * Creating expense
	 */
    public long createExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENTID, expense.getEventId());
        values.put(KEY_AMOUNT, expense.getAmount());
        values.put(KEY_TYPE, expense.getType());
        values.put(KEY_DESCRIPTION, expense.getDescription());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long expense_id = db.insert(TABLE_EXPENSE, null, values);

        return expense_id;
    }

    /**
     * getting all expense
     */
    public List<Expense> getAllExpenses(int id) {
        List<Expense> expense = new ArrayList<Expense>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE + " WHERE " + KEY_EVENTID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Expense e = new Expense();
                e.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                e.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
                e.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
                e.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                e.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));

                // adding to expense list
                expense.add(e);
            } while (c.moveToNext());
        }
        return expense;
    }

    public List<String> getAmount(int id) {
        String[] columns = new String[]{KEY_AMOUNT};
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE + " WHERE " + KEY_EVENTID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String results = "";
        List<String> result = new ArrayList<String>();
        int iCM = c.getColumnIndex(KEY_AMOUNT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result.add(c.getString(iCM));
        }
        return result;
    }

    public int addAccommodation(int pos) {
        String s = "Accommodation";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addFood(int pos) {
        String s = "Food";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addGuide(int pos) {
        String s = "Guide";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addParking(int pos) {
        String s = "Parking";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addPersonal(int pos) {
        String s = "Personal";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addTips(int pos) {
        String s = "Tips";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addTolls(int pos) {
        String s = "Toll";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int addTransport(int pos) {
        String s = "Transport";
        String countQuery = "SELECT SUM(" + KEY_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_TYPE + " = '" + s + "' AND " + KEY_EVENTID + " = " + pos;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    /*
     * Updating a expense
     */
    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, expense.getAmount());
        values.put(KEY_DESCRIPTION, expense.getDescription());

        // updating row
        return db.update(TABLE_EXPENSE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
    }

    /*
     * Deleting a expense
     */
    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        // delete the Expense
        db.delete(TABLE_EXPENSE, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
    }


    // ------------------------ "tour_expense" table methods ----------------//

    private long createTourExpense(long tour_id, long expense_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOUR_ID, tour_id);
        values.put(KEY_EXPENSE_ID, expense_id);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_TOUR_EXPENSE, null, values);

        return id;
    }

    /*
     * Updating a tour_expense
	 */
    public int updateTourExpense(long id, long expense_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXPENSE_ID, expense_id);

        // updating row
        return db.update(TABLE_TOUR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /*
     * Deleting a tour_expense
     */
    public void deleteTourExpense(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOUR, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
