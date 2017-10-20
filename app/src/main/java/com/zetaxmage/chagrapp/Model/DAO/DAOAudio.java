package com.zetaxmage.chagrapp.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.zetaxmage.chagrapp.Model.POJO.Audio;
import com.zetaxmage.chagrapp.Model.POJO.AudioContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZetaxMage on 14/10/2017.
 */

public class DAOAudio extends DataBaseHelper{

    public static final String TABLE_NAME_AUDIO = "Audios";
    public static final String ID_AUDIO = "audioId";
    public static final String DISPLAY_NAME_AUDIO = "audioDisplayName";
    public static final String DISPLAY_AUTHOR_NAME = "audioDisplayAuthor";
    public static final String RAW_NAME_AUDIO = "audioRawName";
    public static final String AUDIO_SHOW = "audioShow";

    private Context context;

    public DAOAudio(Context context) {
        super(context);
        this.context = context;
    }

    public String getAudioRawName (Integer audioId) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT *" + " FROM " + TABLE_NAME_AUDIO + " WHERE " +
                ID_AUDIO + " = " + audioId;

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String value = cursor.getString(cursor.getColumnIndex(RAW_NAME_AUDIO));

        cursor.close();
        db.close();

        return value;
    }

    public List<Audio> getAudioList () {
        List<Audio> audioList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_AUDIO;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(ID_AUDIO));
            String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME_AUDIO));
            String author = cursor.getString(cursor.getColumnIndex(DISPLAY_AUTHOR_NAME));
            String raw = cursor.getString(cursor.getColumnIndex(RAW_NAME_AUDIO));
            Integer show = cursor.getInt(cursor.getColumnIndex(AUDIO_SHOW));
            if (show == 1) {
                audioList.add(new Audio(id, name, author, raw, show));
            }
        }
        cursor.close();
        db.close();
        return audioList;
    }

    // Inserta a la DB la info de los audios desde el Json
    public void firstTimeAudiosSetUp () {
        List<Audio> audioList = null;

        // Parsea del Json los audios y los mete a la lista
        try {
            AssetManager assetManager = context.getAssets();
            InputStream audioJson = assetManager.open("audios.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(audioJson));
            Gson gson = new Gson();
            AudioContainer audioContainer = gson.fromJson(bufferedReader,AudioContainer.class);
            audioList = audioContainer.getAudios();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Carga la lista de audios a la db
        if (audioList != null) {
            ContentValues contentValues;
            SQLiteDatabase db = getWritableDatabase();

            for (Audio audio : audioList) {
                contentValues = new ContentValues();
                contentValues.put(ID_AUDIO,audio.getAudioId());
                contentValues.put(DISPLAY_NAME_AUDIO,audio.getAudioDisplayName());
                contentValues.put(DISPLAY_AUTHOR_NAME,audio.getAudioDisplayAuthor());
                contentValues.put(RAW_NAME_AUDIO,audio.getAudioRawName());
                contentValues.put(AUDIO_SHOW,audio.getAudioShow());
                db.insert(TABLE_NAME_AUDIO,null,contentValues);
            }

            db.close();
        }
    }
}
