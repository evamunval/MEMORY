package edu.fje.memorygame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBGameBuilder extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Game.db";
    private static final int DATABASE_VERSION = 2;

    public DBGameBuilder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBStructure.DataEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + DBStructure.DataEntry.TABLE_NAME + " (" +
                        DBStructure.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DBStructure.DataEntry.COLUMN_SCORE + " INTEGER, " +
                        DBStructure.DataEntry.COLUMN_TIMESTAMP + " TEXT DEFAULT CURRENT_TIMESTAMP " +
                        DBStructure.DataEntry.COLUMN_PLAY_TIME + " TEXT )";
        db.execSQL(CREATE_TABLE); //crea la base de datos
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES); //borra la base de datos
        onCreate(db);
    }
}
