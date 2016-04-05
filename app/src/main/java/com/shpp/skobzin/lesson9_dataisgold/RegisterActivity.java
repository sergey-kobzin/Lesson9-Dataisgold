package com.shpp.skobzin.lesson9_dataisgold;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etLogin;
    EditText etPassword;
    EditText etRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etLogin = (EditText) findViewById(R.id.register_etLogin);
        etLogin.setText(getIntent().getStringExtra(Constants.LOGIN));
        etPassword = (EditText) findViewById(R.id.register_etPassword);
        etRetypePassword = (EditText) findViewById(R.id.register_etRetypePassword);
    }

    public void onClick(View v) {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String retypedPassword = etRetypePassword.getText().toString();
        if (login.equals("") || password.equals("")) {
            Toast.makeText(this, R.string.login_password_can_not_be_empty, Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(retypedPassword)) {
            Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show();
            return;
        }
        UsersDatabaseHelper dbHelper = UsersDatabaseHelper.getInstance(this);
        if (dbHelper.isLoginPresented(login)) {
            Toast.makeText(this, R.string.user_is_already_presented, Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User(login, password);
        dbHelper.addUser(user);
        Toast.makeText(this, R.string.registration_successful, Toast.LENGTH_LONG).show();
        finish();
    }
}
