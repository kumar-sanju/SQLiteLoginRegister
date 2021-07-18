package com.smart.sparcassignment.Login1;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.smart.sparcassignment.R;
import com.smart.sparcassignment.Login1.db.TaskContract;
import com.smart.sparcassignment.Login1.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "MainActivity";//TAG constant with the name of the class for logging
    private TaskDbHelper mHelper; // private instance of TaskDbHelper
    private ListView mTaskListView; // list view to display the todos created by user
    private ArrayAdapter<String> mAdapter; // this array adapter will help to poulate the list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo); //listview to id bata reference gareko

        updateUI(); // update UI with the tasks on start
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, new String[]{TaskContract.TaskEntry._ID,TaskContract.TaskEntry.COL_TASK_TITLE},null,null,null,null,null);
        while(cursor.moveToNext()){
            int idx= cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo, // what view to use for the items
                    R.id.task_title, // where to put the strings of data
                    taskList); // where to get all the data

            mTaskListView.setAdapter(mAdapter); //set it as the adapter for the ListView instance
        }else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // to create the menu
        mHelper = new TaskDbHelper(this); // initialising the mHelper instance of TaskDbHelper
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //when the add a task button is selected
        switch(item.getItemId()){
            case R.id.action_add_task:
                //Log.d(TAG, "Add a new task");
                //return true;
                final EditText taskEditText = new EditText(this); // EditText to add new task text
                AlertDialog dialog = new AlertDialog.Builder(this) // adding a new task functionality in AlertDialog
                        .setTitle("Add a new Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() { //Add button daabdaa
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG,"Task to add: "+task); // This code was for testing purpose with Logcat
                                SQLiteDatabase db = mHelper.getWritableDatabase(); // mHelper vaneko TaskDbHelper ko instance ho, tyaha bata sqlite database lyayeko
                                ContentValues values= new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE,task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel",null) // Cancel daabdaa
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View view) { //for delete functionality, that is when the DONE Button is clicked
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

}
