package com.zetaxmage.chagrapp.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZetaxMage on 14/10/2017.
 */

public class DAOSettings extends DataBaseHelper{

    public static final String TABLE_NAME_SETTINGS = "Settings";
    public static final String NAME_SETTING = "SettingName";
    public static final String VALUE_SETTING = "SettingValue";

    public static final String AUDIO_FORMAT_SETTING = "audio_format_setting";

    private long result = -1;

    public DAOSettings(Context context) {
        super(context);
    }

    public Boolean updateAudioFormatSetting (Boolean choice) {
        Integer toSave = parseBoolToInt(choice);
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(VALUE_SETTING,toSave);

        db.update(TABLE_NAME_SETTINGS,contentValues,NAME_SETTING+" = '"+AUDIO_FORMAT_SETTING+"'",null);

        return result > -1;
    }

    private Integer parseBoolToInt (Boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }

    public Boolean getAudioFormatSetting (){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT *" + " FROM " + TABLE_NAME_SETTINGS + " WHERE " +
                NAME_SETTING + " = '" + AUDIO_FORMAT_SETTING + "'";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        Integer value = cursor.getInt(cursor.getColumnIndex(VALUE_SETTING));

        cursor.close();
        db.close();

        return value == 1;
    }

    // Inserta a la db los settings iniciales
    public void firstTimeSettingsSetUp() {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DAOSettings.NAME_SETTING,DAOSettings.AUDIO_FORMAT_SETTING);
        contentValues.put(DAOSettings.VALUE_SETTING,1);
        db.insert(DAOSettings.TABLE_NAME_SETTINGS,null,contentValues);

        db.close();
    }
}
