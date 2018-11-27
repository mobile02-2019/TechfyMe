package com.me.techfy.techfyme.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class PreferenciaDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Preferencia.db";

    public static class Preferencia implements BaseColumns {
        public static final String TABLE_NAME =  "preferencia";
        public static final String COLUMN_NAME_USERID = "userId";
        public static final String COLUMN_NAME_CHECADOS = "checados";
    }

    private static final String SQL_CREATE_PREFERENCES = "CREATE TABLE " + Preferencia.TABLE_NAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Preferencia.COLUMN_NAME_USERID + " TEXT, " +
            Preferencia.COLUMN_NAME_CHECADOS + " TEXT)";

    private static final String SQL_DELETE_PREFERENCES = "DROP TABLE IF EXISTS " + Preferencia.TABLE_NAME;

    public PreferenciaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PREFERENCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PREFERENCES);
        onCreate(db);
    }

    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}