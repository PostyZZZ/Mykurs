package com.example.mykurs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TranslationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "translations.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TRANSLATIONS = "translations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUERY = "query";
    public static final String COLUMN_TRANSLATION = "translation";

    // SQL-запрос для создания таблицы
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TRANSLATIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_QUERY
            + " text not null, " + COLUMN_TRANSLATION
            + " text not null);";

    public TranslationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSLATIONS);
        onCreate(db);
    }
}
