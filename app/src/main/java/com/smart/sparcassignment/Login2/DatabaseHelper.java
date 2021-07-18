package com.smart.sparcassignment.Login2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.smart.sparcassignment.AddActivity;
import com.smart.sparcassignment.Login2.Data;

import static android.content.Context.MODE_PRIVATE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SANJU = "DatabaseHelper";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public DatabaseHelper(@NonNull Context context){
        super(context, "name.db", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists User (id integer primary key autoincrement, email text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        onCreate(db);
    }

    public boolean registerUser(Data data, @NonNull Context context) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Check if name exists
        Cursor cursor =  sqLiteDatabase.rawQuery( "SELECT * FROM User WHERE email = ? ", new String[]{ data.getEmail() } );

        // If name doesn't exist -> add
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", data.getEmail());
            contentValues.put("password", data.getPassword());
            long user = sqLiteDatabase.insert("User", null, contentValues);
            if (user != -1) {
                Log.e(SANJU, "registerUser: User Registration Successfully.");
                Toast.makeText(context, "User Registration Successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("MESSAGE","success");
                context.startActivity(intent);
            }
            else {
                Log.e(SANJU, "registerUser: Error in Registering.");
                Toast.makeText(context, "Error in Registering.", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        else {
            Log.e(SANJU, "registerUser: Email already exist.");
            Toast.makeText(context, "Email already exist.", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void loginUser(Data data, @NonNull Context context){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from User",null,null);

        while (cursor.moveToNext()){
            String email = cursor.getString(1);
            String password = cursor.getString(2);

            if (email.equals(data.getEmail()) && password.equals(data.getPassword())) {
                Log.e(SANJU, "loginUser: User Login Successfully.");
                Toast.makeText(context, "User Login Successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("MESSAGE","success");
                context.startActivity(intent);
            }
            else {
                Log.e(SANJU, "loginUser: User Not Found. Please Register user self.");
                Toast.makeText(context, "User Not Found!!! Please register user self first.", Toast.LENGTH_LONG).show();
            }
        }

    }

}
