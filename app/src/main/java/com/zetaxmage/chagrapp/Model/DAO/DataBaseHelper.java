package com.zetaxmage.chagrapp.Model.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZetaxMage on 14/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ChaGrappDB";
    public static final Integer DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Se crea la tabla de settings
        sqLiteDatabase.execSQL("CREATE TABLE " + DAOSettings.TABLE_NAME_SETTINGS + "(" +
                DAOSettings.NAME_SETTING + " TEXT PRIMARY KEY," +
                DAOSettings.VALUE_SETTING + " INTEGER NOT NULL);");

        // Se crea la tabla de audios
        sqLiteDatabase.execSQL("CREATE TABLE " + DAOAudio.TABLE_NAME_AUDIO + "(" +
                DAOAudio.ID_AUDIO + " INTEGER PRIMARY KEY," +
                DAOAudio.DISPLAY_NAME_AUDIO + " TEXT," +
                DAOAudio.DISPLAY_AUTHOR_NAME + " TEXT," +
                DAOAudio.RAW_NAME_AUDIO + " TEXT NOT NULL," +
                DAOAudio.AUDIO_SHOW + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
