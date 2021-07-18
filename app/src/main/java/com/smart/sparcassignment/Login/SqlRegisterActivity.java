package com.smart.sparcassignment.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smart.sparcassignment.AddActivity;
import com.smart.sparcassignment.R;

import static com.smart.sparcassignment.Key.LOGIN;
import static com.smart.sparcassignment.Key.PREFERENCE_NAME;

public class SqlRegisterActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper databaseHelper;
    String getEmail, getPassword, success;
    SharedPreferences sharedPreferences;
    TextView registerText;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        success = sharedPreferences.getString("login",LOGIN);
        Log.d("sanju", success);

        if (success.equals("success")) {
            Intent intent = new Intent(SqlRegisterActivity.this, AddActivity.class);
            intent.putExtra("MESSAGE","success");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_register);

        databaseHelper = new DatabaseHelper(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        registerText = findViewById(R.id.registerText);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SqlRegisterActivity.this, SqlLoginctivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmail = edtEmail.getText().toString();
                getPassword = edtPassword.getText().toString();

                databaseHelper.registerUser(new Data(getEmail,getPassword), getApplicationContext());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}