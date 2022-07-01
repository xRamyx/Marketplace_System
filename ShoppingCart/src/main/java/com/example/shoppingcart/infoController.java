package com.example.shoppingcart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class infoController {
    @FXML
    private Label balancelbl;

    @FXML
    private Label firstlbl;

    @FXML
    private Label lastlbl;

    @FXML
    private Label midlbl;

    @FXML
    private Label userlbl;

    @FXML
    private Button back;

    @FXML
    public void display(String first,String mid,String last, String user, String balance) {
        firstlbl.setText(first);
        midlbl.setText(mid);
        lastlbl.setText(last);
        userlbl.setText(user);
        balancelbl.setText(balance);

    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage window = (Stage) back.getScene().getWindow();
        window.setTitle("Marketplace System");
        window.setScene(new Scene(root, 800, 500));
    }

}