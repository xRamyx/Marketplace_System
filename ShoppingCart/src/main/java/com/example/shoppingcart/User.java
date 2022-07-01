package com.example.shoppingcart;

public class User {
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String username;
    private String balance;

    public User(String id, String fname, String mname, String lname, String username, String balance) {
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.username = username;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
