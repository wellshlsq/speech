package com.wells.speech.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Recording {
    @Id
    private Long id;

    private String name;

    private byte[] audioblob;

    private String userName;

    private String userrole;

    private boolean custompronunciation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAudioblob() {
        return audioblob;
    }

    public void setAudioblob(byte[] audioblob) {
        this.audioblob = audioblob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public boolean isCustompronunciation() {
        return custompronunciation;
    }

    public void setCustompronunciation(boolean custompronunciation) {
        this.custompronunciation = custompronunciation;
    }
}
