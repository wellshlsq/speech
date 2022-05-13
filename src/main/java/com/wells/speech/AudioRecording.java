package com.wells.speech;

public class AudioRecording {
    private String name;
    private String audioBlob;

    public String getName() {
        return name;
    }

    public String getAudioBlob() {
        return audioBlob;
    }

    public void setAudioBlob(String audioBlob) {
        this.audioBlob = audioBlob;
    }

    public void setName(String name) {
        this.name = name;
    }
}
