package com.shpp.skobzin.lesson9_dataisgold;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        TextView tvHello = (TextView) findViewById(R.id.hello_tvHello);
        String login = getIntent().getStringExtra(Constants.LOGIN);
        tvHello.setText("Hello " + login + ".");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);;
    }

    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.LOGIN);
        editor.apply();
        Intent startLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(startLoginActivity);
        finish();
    }
}
