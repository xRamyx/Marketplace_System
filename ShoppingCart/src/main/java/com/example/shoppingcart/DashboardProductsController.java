package com.example.shoppingcart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.ImageView;

public class DashboardProductsController {

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<Product, String> col_id;

    @FXML
    private TableColumn<Product, String> col_name;

    @FXML
    private TableColumn<Product, String> col_cat;

    @FXML
    private TableColumn<Product, Integer> col_qty;

    @FXML
    private TableColumn<Product, Float> col_price;

    @FXML
    private TableColumn<Product, ImageView> col_img;

    @FXML
    private TableView<Product> table;

    @FXML
    public void initialize() {
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        String dist = "dashboard-products";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray ids = (JSONArray) serverResponse.get("id");
        JSONArray names = (JSONArray) serverResponse.get("name");
        JSONArray cats = (JSONArray) serverResponse.get("category");
        JSONArray qtys = (JSONArray) serverResponse.get("qty");
        JSONArray prices = (JSONArray) serverResponse.get("price");
        JSONArray imgs = (JSONArray) serverResponse.get("img");

        col_id.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        col_cat.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        col_qty.setCellValueFactory(new PropertyValueFactory<Product, Integer>("qty"));
        col_img.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("imgView"));


        ObservableList<Product> products = FXCollections.observableArrayList();
        for(int i = 0; i < ids.size(); i++){
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("images/" + imgs.get(i));
            Image image = new Image(input);
            products.add(new Product((String)ids.get(i), (String) names.get(i),Float.parseFloat((String)prices.get(i)), (String) cats.get(i), Integer.parseInt((String)qtys.get(i)), (String)imgs.get(i), new ImageView(image)));
        }
        table.setItems(products);
    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Admin Dashboard");
        window.setScene(new Scene(root, 800, 500));
    }
}
