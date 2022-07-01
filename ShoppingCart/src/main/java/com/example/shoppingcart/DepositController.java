package com.example.shoppingcart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class DepositController {
    @FXML
    private TextField depositText;

    @FXML
    void btnDeposit_Handler(ActionEvent event) throws IOException {
        if(!isNumeric(depositText.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Balance must be a number");
            alert.showAndWait();
            depositText.setText("");
            return;
        }

        if(Float.parseFloat(depositText.getText()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Balance Cannot Be Negative");
            alert.showAndWait();
            depositText.setText("");
            return;
        }
        // sending json
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        json.put("amount", depositText.getText());
        json.put("user", HomeController.username);
        System.out.println(HomeController.username + " " + depositText.getText());
        String dist = "deposit";
        socketClient.socketSendReceiveJSON(json, dist);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Amount added successfully!");
        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage window = (Stage) depositText.getScene().getWindow();
        window.setTitle("Marketplace System");
        window.setScene(new Scene(root, 800, 500));
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage window = (Stage) depositText.getScene().getWindow();
        window.setTitle("Marketplace System");
        window.setScene(new Scene(root, 800, 500));
    }
}
