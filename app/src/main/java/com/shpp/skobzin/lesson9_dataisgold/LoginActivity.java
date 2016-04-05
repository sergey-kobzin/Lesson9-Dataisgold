package com.shpp.skobzin.lesson9_dataisgold;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = (EditText) findViewById(R.id.login_etLogin);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(Constants.LOGIN)) {
            String login = sharedPreferences.getString(Constants.LOGIN, "");
            if (!login.equals("")) {
                Intent startHelloActivity = new Intent(this, HelloActivity.class);
                startHelloActivity.putExtra(Constants.LOGIN, login);
                startActivity(startHelloActivity);
                finish();
            }
        }
    }

    public void onClick(View v) {
        String login = etLogin.getText().toString();
        switch (v.getId()) {
            case R.id.login_btnSignIn:
                String password = etPassword.getText().toString();
                UsersDatabaseHelper dbHelper = UsersDatabaseHelper.getInstance(this);
                User user = dbHelper.findUser(login, password);
                if (user != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.LOGIN, login);
                    editor.apply();
                    Intent startHelloActivity = new Intent(this, HelloActivity.class);
                    startHelloActivity.putExtra(Constants.LOGIN, login);
                    startActivity(startHelloActivity);
                    finish();
                } else {
                    Toast.makeText(this, R.string.user_is_not_found, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.login_btnRegister:
                Intent startRegisterActivity = new Intent(this, RegisterActivity.class);
                startRegisterActivity.putExtra(Constants.LOGIN, login);
                startActivity(startRegisterActivity);
                break;
        }
    }
}
