package com.zetaxmage.chagrapp.Controller;

import android.content.Context;

import com.zetaxmage.chagrapp.Model.DAO.DAOAudio;
import com.zetaxmage.chagrapp.Model.DAO.DAOSettings;
import com.zetaxmage.chagrapp.Model.POJO.Audio;

import java.util.List;

/**
 * Created by ZetaxMage on 14/10/2017.
 */

public class ControllerDB {
    private Context context;

    public ControllerDB(Context context) {
        this.context = context;
    }

    // Metodos de carga de datos que se ejecutan solo 1 vez
    public void firstTimeSettingsSetUp() {
        DAOSettings daoSettings = new DAOSettings(context);
        daoSettings.firstTimeSettingsSetUp();
    }

    public void firstTimeAudiosSetUp() {
        DAOAudio daoAudio = new DAOAudio(context);
        daoAudio.firstTimeAudiosSetUp();

    }

    // Settings
    public Boolean updateAudioFormatSetting (Boolean choice){
        DAOSettings daoSettings = new DAOSettings(context);
        return daoSettings.updateAudioFormatSetting(choice);
    }

    public Boolean getAudioFormatSetting () {
        DAOSettings daoSettings = new DAOSettings(context);
        return daoSettings.getAudioFormatSetting();
    }

    // Audio
    public List<Audio> getAudioList () {
        DAOAudio daoAudio = new DAOAudio(context);
        return daoAudio.getAudioList();
    }

    public String getAudioRawName (Integer audioId) {
        DAOAudio daoAudio = new DAOAudio(context);
        return daoAudio.getAudioRawName(audioId);
    }
}
