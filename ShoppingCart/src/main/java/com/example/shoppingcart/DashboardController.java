package com.example.shoppingcart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    @FXML
    private Button btnBack;

    @FXML
    private Label users_num;

    @FXML
    private Label products_num;

    @FXML
    private Label transactions_num;

    @FXML
    private Label income;

    @FXML
    public void initialize() {
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        String dist = "dashboard-users";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray users = new JSONArray();
        users = (JSONArray) serverResponse.get("user_id");
        if(users == null)
            users_num.setText("0");
        else
            users_num.setText(users.size() + "");

        dist = "dashboard-products";
        serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray products = new JSONArray();
        products = (JSONArray) serverResponse.get("id");
        if(products == null)
            products_num.setText("0");
        else
            products_num.setText(products.size() + "");


        dist = "dashboard-transactions";
        serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray transactions = (JSONArray) serverResponse.get("transactions");
        if(transactions == null)
        {
            transactions_num.setText("0");
            income.setText("0");
        }
        else
        {
            transactions_num.setText(transactions.size() + "");
            int sum = 0;

            for (int i = 0; i < transactions.size(); i++)
            {
                sum += (double)(((JSONObject) transactions.get(i)).get("balance_before"))
                        - (double)(((JSONObject) transactions.get(i)).get("balance_after"));
            }
            income.setText(sum + "");
        }

    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Marketplace System");
        window.setScene(new Scene(root, 800, 500));
    }

    @FXML
    void showUsers_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard-users.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Users");
        window.setScene(new Scene(root, 800, 500));
    }

    @FXML
    void showProducts_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard-products.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Products");
        window.setScene(new Scene(root, 800, 500));
    }

    @FXML
    void showTransactions_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard-transactions.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Transactions");
        window.setScene(new Scene(root, 800, 500));
    }

}
