package com.wells.speech.dto;

public class LoadResponse {

    String userId;
    String firstName;
    String lastName;
    boolean customSoundInd;
    byte[] nameSound;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isCustomSoundInd() {
        return customSoundInd;
    }

    public void setCustomSoundInd(boolean customSoundInd) {
        this.customSoundInd = customSoundInd;
    }

    public byte[] getNameSound() {
        return nameSound;
    }

    public void setNameSound(byte[] nameSound) {
        this.nameSound = nameSound;
    }
}
