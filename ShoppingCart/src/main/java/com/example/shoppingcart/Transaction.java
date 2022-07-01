package com.example.shoppingcart;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;

public class Transaction {
    private String id;
    private double balanceBefore;
    private double balanceAfter;
    private String userId;
    private String userName;
//    private int quantity;
//    private TableView<Product> products;
    Transaction(){

    }

    public Transaction(String id, double balanceBefore, double balanceAfter, String userId, String userName) {
        this.id = id;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.userId = userId;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
