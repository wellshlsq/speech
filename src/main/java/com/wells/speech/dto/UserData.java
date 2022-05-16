package com.wells.speech.dto;

public class UserData {

    Long id;
    String name;
    boolean customPronuncitionInd;
    String username;
    String userrole;

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

    public boolean isCustomPronuncitionInd() {
        return customPronuncitionInd;
    }

    public void setCustomPronuncitionInd(boolean customPronuncitionInd) {
        this.customPronuncitionInd = customPronuncitionInd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}
