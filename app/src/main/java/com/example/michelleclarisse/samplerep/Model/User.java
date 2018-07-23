package com.example.michelleclarisse.samplerep.Model;

public class User {
    private String FName;
    private String LName;
    private String MName;
    private String Password;
    private String Email;
    private String Phone;

    public User() {
    }

    public User(String email, String fname, String lname,  String mname, String password, String phone) {
        setEmail(email);
        setFName(fname);
        setLName(lname);
        setMName(mname);
        setPassword(password);
        setPhone(phone);
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String fullName(){
        String message = FName+" "+MName.substring(0,1)+". "+LName;
        return message;
    }
}
