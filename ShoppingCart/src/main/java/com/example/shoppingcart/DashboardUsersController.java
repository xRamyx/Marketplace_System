package com.example.shoppingcart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class DashboardUsersController {

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<User, String> col_id;

    @FXML
    private TableColumn<User, String> col_fname;

    @FXML
    private TableColumn<User, String> col_mname;

    @FXML
    private TableColumn<User, String> col_lname;

    @FXML
    private TableColumn<User, String> col_username;

    @FXML
    private TableColumn<User, String> col_balance;

    @FXML
    private TableView<User> table;

    @FXML
    public void initialize() {
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        String dist = "dashboard-users";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray ids = (JSONArray) serverResponse.get("user_id");
        JSONArray fnames = (JSONArray) serverResponse.get("fname");
        JSONArray mnames = (JSONArray) serverResponse.get("mname");
        JSONArray lnames = (JSONArray) serverResponse.get("lname");
        JSONArray usernames = (JSONArray) serverResponse.get("user_name");
        JSONArray balances = (JSONArray) serverResponse.get("balance");

        col_id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<User, String>("fname"));
        col_mname.setCellValueFactory(new PropertyValueFactory<User, String>("mname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<User, String>("lname"));
        col_username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        col_balance.setCellValueFactory(new PropertyValueFactory<User, String>("balance"));

        ObservableList<User> users = FXCollections.observableArrayList();
        for(int i = 0; i < ids.size(); i++){
            users.add(new User((String)ids.get(i), (String) fnames.get(i),(String) mnames.get(i), (String)lnames.get(i), (String)usernames.get(i), (String)balances.get(i)));
        }



        table.setItems(users);
    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Admin Dashboard");
        window.setScene(new Scene(root, 800, 500));
    }
}
