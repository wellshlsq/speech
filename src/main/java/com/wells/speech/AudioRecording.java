package com.wells.speech;

public class AudioRecording {
    private String name;
    private String audioBlob;
    private byte[] audioInByte;

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

    public byte[] getAudioInByte() {
        return audioInByte;
    }

    public void setAudioInByte(byte[] audioInByte) {
        this.audioInByte = audioInByte;
    }
}
