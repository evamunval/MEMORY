package edu.fje.memorygame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game_scores.db";
    private static final int DATABASE_VERSION = 1;

    // Crear tabla
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE puntuacion (" +
                    "_id INTEGER PRIMARY KEY," +
                    "fecha TEXT," +
                    "tiempo TEXT," +
                    "puntuacion INTEGER);";

    // Eliminar tabla
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS puntuacion";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
