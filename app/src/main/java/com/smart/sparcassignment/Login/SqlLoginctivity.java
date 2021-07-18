package com.smart.sparcassignment.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.smart.sparcassignment.R;

public class SqlLoginctivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;
    String getEmail, getPassword;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_loginctivity);

        databaseHelper = new DatabaseHelper(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        registerText = findViewById(R.id.registerText);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SqlLoginctivity.this, SqlRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmail = edtEmail.getText().toString();
                getPassword = edtPassword.getText().toString();

                databaseHelper.loginUser(new Data(getEmail,getPassword), getApplicationContext());
            }
        });
    }
}