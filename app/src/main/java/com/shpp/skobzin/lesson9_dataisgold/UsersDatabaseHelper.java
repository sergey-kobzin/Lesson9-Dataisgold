package com.shpp.skobzin.lesson9_dataisgold;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsersDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usersDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";

    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_LOGIN = "login";
    private static final String KEY_USER_PASSWORD = "password";

    private static UsersDatabaseHelper singletonInstance;

    private UsersDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized UsersDatabaseHelper getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new UsersDatabaseHelper(context.getApplicationContext());
        }
        return singletonInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_USERS,
                KEY_USER_ID,
                KEY_USER_LOGIN,
                KEY_USER_PASSWORD);
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String command = String.format("DROP TABLE IF EXISTS %s",
                    TABLE_USERS);
            db.execSQL(command);
            onCreate(db);
        }
    }

    public boolean isLoginPresented(String login) {
        boolean result;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_USER_LOGIN},
                KEY_USER_LOGIN + " = ?",
                new String[]{login},
                null, null, null);
        result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    public User findUser(String login, String password) {
        User result = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_USER_LOGIN, KEY_USER_PASSWORD},
                KEY_USER_LOGIN + " = ? and " + KEY_USER_PASSWORD + " = ?",
                new String[]{login, password},
                null, null, null);
        if (cursor.moveToFirst()) {
            result = new User(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_LOGIN, user.getLogin());
        cv.put(KEY_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, cv);
        db.close();
    }
}
