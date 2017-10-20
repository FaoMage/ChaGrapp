package com.zetaxmage.chagrapp.Model.POJO;

/**
 * Created by zetaxmage on 15/10/17.
 */

public class Audio {

    private Integer audioId;
    private String audioDisplayName;
    private String audioDisplayAuthor;
    private String audioRawName;
    private Integer audioShow;

    public Audio(Integer audioId, String audioDisplayName, String audioDisplayAuthor, String audioRawName, Integer audioShow) {
        this.audioId = audioId;
        this.audioDisplayName = audioDisplayName;
        this.audioDisplayAuthor = audioDisplayAuthor;
        this.audioRawName = audioRawName;
        this.audioShow = audioShow;
    }

    public Integer getAudioId() {
        return audioId;
    }

    public String getAudioDisplayName() {
        return audioDisplayName;
    }

    public String getAudioDisplayAuthor() {
        return audioDisplayAuthor;
    }

    public String getAudioRawName() {
        return audioRawName;
    }

    public Integer getAudioShow() {
        return audioShow;
    }
}
