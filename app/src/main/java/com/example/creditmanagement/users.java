package com.example.creditmanagement;

public class users {
    private final String name;
    private final String email;
    private final int points;
    private int uid;
    public users(String nm,String mail,int pts){
        name=nm;
        email=mail;
        points=pts;
    }

    public String getName() {
        return name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }

}
