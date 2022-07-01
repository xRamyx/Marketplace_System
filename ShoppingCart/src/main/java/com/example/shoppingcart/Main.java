package com.example.shoppingcart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        // open socket connection
        SocketClient.initConnection(5000);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        // close socket connection
        System.out.println("Closing connection");
        SocketClient socketClient = SocketClient.getInstance();
        socketClient.closeConnection();

    }

}