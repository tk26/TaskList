package com.tejaskumthekar.tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tk on 10/8/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TaskDB";

    // Books table name
    private static final String TABLE_TASKS = "tasks";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRIORITY = "priority";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_PRIORITY};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TASK_TABLE = "CREATE TABLE tasks ( " +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "name TEXT, "+
        "priority INTEGER )";

        // create books table
        db.execSQL(CREATE_TASK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // create fresh books table
        this.onCreate(db);

    }

    public void addTask(Task task){
        //for logging
        Log.d("addTask", task.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName()); // get title
        values.put(KEY_PRIORITY, task.getPriority()); // get author

        // 3. insert
        db.insert(TABLE_TASKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Task getTask(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TASKS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Task t = new Task();
        t.setId(Integer.parseInt(cursor.getString(0)));
        t.setName(cursor.getString(1));
        t.setPriority(Integer.parseInt(cursor.getString(2)));

        //log
        Log.d("getTask(" + id + ")", t.toString());

        // 5. return book
        return t;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<Task>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TASKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Task t = null;
        if (cursor.moveToFirst()) {
            do {
                t = new Task();
                t.setId(Integer.parseInt(cursor.getString(0)));
                t.setName(cursor.getString(1));
                t.setPriority(Integer.parseInt(cursor.getString(2)));

                // Add book to books
                tasks.add(t);
            } while (cursor.moveToNext());
        }

        Log.d("getAllTasks()", tasks.toString());

        // return books
        return tasks;
    }

    public int updateTask(Task task) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", task.getName()); // get title
        values.put("priority", task.getPriority()); // get author

        // 3. updating row
        int i = db.update(TABLE_TASKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(task.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }


    public void deleteTask(Task task) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_TASKS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(task.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteTask", task.toString());

    }

}
