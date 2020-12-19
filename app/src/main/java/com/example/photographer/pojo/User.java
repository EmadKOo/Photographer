package com.example.photographer.pojo;

public class User {
    String fid; // firebaseID
    String fName;
    String lName;
    String mail;
    String password;
    String userType; // normal, photographer, admin
    String categories; // 1,3, 5   tokinizer or split using ','
    String userImagePath;

    public User() {
    }

    public User(String fid, String fName, String lName, String mail, String password, String userType, String categories, String userImagePath) {
        this.fid = fid;
        this.fName = fName;
        this.lName = lName;
        this.mail = mail;
        this.password = password;
        this.userType = userType;
        this.categories = categories;
        this.userImagePath = userImagePath;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMail() {
        return mail;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}