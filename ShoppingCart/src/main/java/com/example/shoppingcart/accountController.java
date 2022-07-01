package com.example.shoppingcart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;


public class accountController  {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label lbl23;
    @FXML
    private TextField fname;
    @FXML
    private TextField mname;
    @FXML
    private TextField lname;
    @FXML
    private TextField username;
    @FXML
    private TextField pass;
    @FXML
    private TextField passv;
    @FXML
    private TextField blnc;
    @FXML
    private TextField textField;
    @FXML
    private TextField uname;
    @FXML
    private TextField password;


    @FXML
    private VBox itemBox;
    @FXML
    private AnchorPane Spane;

    @FXML
    private ScrollPane Sspane;

    @FXML
    private Label label3;

    public accountController() {
    }


//    @FXML
//    void btnSend_Handler(){
//
//        // sending json
//        SocketClient socketClient = SocketClient.getInstance();
//        JSONObject json = new JSONObject();
//        json.put("data", textField.getText());
//        String dist = "test-data";
//        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
//        //  textArea.appendText("Client: " + textField.getText() + "\n");
//        //  textArea.appendText("Server: " + serverResponse.get("data") + "\n");
//    }

    @FXML
    void btnLogin_Handler(javafx.event.ActionEvent event) throws IOException{

        // sending json
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        json.put("user", uname.getText());
        json.put("pass", password.getText());
        String dist = "login";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        HomeController.username = uname.getText();
        if(HomeController.username.equals("admin")){
            HomeController.isAdmin = true;

        }
        if(serverResponse.get("access").equals("Granted")){
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Marketplace System");
            stage.setScene(scene);
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid Username Or Password");
            alert.showAndWait();
            //  textArea.appendText("Wrong credentials" + "\n");
        }
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

    public boolean containsNumeric(String str){
        if (str.matches(".*[0-9].*")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Names Cannot Have Numbers");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    @FXML
    void btnCreateAcc_Handler(){
        if(fname.getText().isBlank() || mname.getText().isBlank() || lname.getText().isBlank() || username.getText().isBlank() || pass.getText().isBlank() || passv.getText().isBlank() || blnc.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Fields Cannot Be NULL");
            alert.showAndWait();
            return;
        }
        if(!isNumeric(blnc.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Balance must be a number");
            alert.showAndWait();
            blnc.setText("");
            return;
        }

        if(Float.parseFloat(blnc.getText()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Balance Cannot Be Negative");
            alert.showAndWait();
            blnc.setText("");
            return;
        }

        if( containsNumeric(fname.getText())) return;
        if( containsNumeric(mname.getText())) return;
        if( containsNumeric(lname.getText())) return;

        if( isNumeric(String.valueOf(username.getText().charAt(0)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Username Cannot Start With A Number");
            alert.showAndWait();
            username.setText("");
            return;
        }

        // sending json
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        json.put("fname", fname.getText());
        json.put("mname", mname.getText());
        json.put("lname", lname.getText());
        json.put("username", username.getText());
        json.put("pass", pass.getText());
        json.put("passv", passv.getText());
        json.put("blnc", blnc.getText());
        String dist = "sign-up";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        if(serverResponse.get("create").equals("Ok")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Your account is created successfully!");
            alert.showAndWait();
        }else if(serverResponse.get("create").equals("Choose Another")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Username is already in use!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Verification password does not match the entered one!");
            alert.showAndWait();
        }
    }

    @FXML
    void switchToSignUpPage(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Sign-Up Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToLoginPage(javafx.event.ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
}
