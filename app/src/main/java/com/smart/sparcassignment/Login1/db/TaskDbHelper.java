package com.smart.sparcassignment.Login1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper{

    public TaskDbHelper(Context context){ //constructor for the class
        super(context,TaskContract.DB_NAME,null,TaskContract.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) { //SQLiteDatabase db code here creates an instance of the SQLiteDatabase class via which database methods are going to be implemented
        //on creation of the SQLite database execute following
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);"; // this is SQL command to create the table and is stored in the string createTable
        /*the equivalent sql query of the above would be CREATE TABLE tasks (
        _id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL
        );*/

        db.execSQL(createTable); // the createTable string is passed to the db.execSQL instead of the long SQL command
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
