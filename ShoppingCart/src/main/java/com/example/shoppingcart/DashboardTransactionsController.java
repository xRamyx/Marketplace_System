package com.example.shoppingcart;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DashboardTransactionsController {

    @FXML
    private Button btnBack;

    @FXML
    private VBox vbox;

    @FXML
    public void initialize() {
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        String dist = "dashboard-transactions";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        JSONArray transactions = (JSONArray) serverResponse.get("transactions");

        for(int i = 0; i < transactions.size(); i++){
             TableColumn<Transaction, String> col_id = new TableColumn<>("Transaction Id");
             TableColumn<Transaction, String> col_userId = new TableColumn<>("User Id");
             TableColumn<Transaction, String> col_username = new TableColumn<>("Username");
             TableColumn<Transaction, Double> col_balanceBefore = new TableColumn<>("Balance Before");
             TableColumn<Transaction, Double> col_balanceAfter = new TableColumn<>("Balance After");

            col_id.setCellValueFactory(new PropertyValueFactory<Transaction, String>("id"));
            col_userId.setCellValueFactory(new PropertyValueFactory<Transaction, String>("userId"));
            col_username.setCellValueFactory(new PropertyValueFactory<Transaction, String>("userName"));
            col_balanceBefore.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("balanceBefore"));
            col_balanceAfter.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("balanceAfter"));

            ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();

            JSONObject singleTrans = (JSONObject)transactions.get(i);
            String trans_id = (String) singleTrans.get("id");
            double trans_balanceBefore = (double) singleTrans.get("balance_before");
            double trans_balanceAfter = (double) singleTrans.get("balance_after");
            String trans_userId = (String) singleTrans.get("user_id");
            String trans_userName = (String) singleTrans.get("username");
            JSONArray products = (JSONArray) singleTrans.get("products");

            Transaction transaction = new Transaction(trans_id, trans_balanceBefore, trans_balanceAfter, trans_userId, trans_userName);
            transactionsList.add(transaction);

            TableView<Transaction> transactionTableView = new TableView<>();
            transactionTableView.setItems(transactionsList);
            transactionTableView.setFixedCellSize(25);
            transactionTableView.prefHeightProperty().bind(Bindings.size(transactionTableView.getItems()).multiply(transactionTableView.getFixedCellSize()).add(30));

            transactionTableView.getColumns().add(col_id);
            transactionTableView.getColumns().add(col_userId);
            transactionTableView.getColumns().add(col_username);
            transactionTableView.getColumns().add(col_balanceBefore);
            transactionTableView.getColumns().add(col_balanceAfter);

            TableView<Product> productsTableView = new TableView<>();
            ObservableList<Product> productsList = FXCollections.observableArrayList();

            TableColumn<Product, String> col_prod_id = new TableColumn<>("Product Id");
            TableColumn<Product, String> col_prod_name = new TableColumn<>("Name");
            TableColumn<Product, String> col_prod_cat = new TableColumn<>("Category");
            TableColumn<Product, Double> col_prod_price = new TableColumn<>("Price");
            TableColumn<Product, Integer> col_prod_qty = new TableColumn<>("Quantity");
            TableColumn<Product, ImageView> col_prod_img = new TableColumn<>("Image");

            col_prod_id.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
            col_prod_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            col_prod_price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            col_prod_cat.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
            col_prod_qty.setCellValueFactory(new PropertyValueFactory<Product, Integer>("qty"));
            col_prod_img.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("imgView"));

            for(int j = 0; j < products.size(); j++){
                JSONObject singleProduct = (JSONObject)products.get(j);
                String prod_id = (String) singleProduct.get("id");
                String  prod_name = (String) singleProduct.get("name");
                String prod_cat = (String) singleProduct.get("category");
                double prod_price = (double) singleProduct.get("price");
                long prod_qty = (long) singleProduct.get("qty");
                String prod_img = (String) singleProduct.get("img");

                InputStream input = this.getClass().getClassLoader().getResourceAsStream("images/" + prod_img);
                Image image = new Image(input);

                Product product = new Product(prod_id, prod_name, prod_price, prod_cat, prod_qty, prod_img, new ImageView(image));
                productsList.add(product);
            }

            productsTableView.getColumns().add(col_prod_id);
            productsTableView.getColumns().add(col_prod_name);
            productsTableView.getColumns().add(col_prod_cat);
            productsTableView.getColumns().add(col_prod_price);
            productsTableView.getColumns().add(col_prod_qty);
            productsTableView.getColumns().add(col_prod_img);

            productsTableView.setItems(productsList);
            productsTableView.setFixedCellSize(100);
            productsTableView.prefHeightProperty().bind(Bindings.size(productsTableView.getItems()).multiply(productsTableView.getFixedCellSize()).add(30));

            TitledPane transactionPane = new TitledPane();
            VBox innerVbox = new VBox();
            innerVbox.getChildren().add(transactionTableView);
            innerVbox.getChildren().add(productsTableView);
            productsTableView.setPadding(new Insets(0, 0, 0, 50));
            transactionPane.setContent(innerVbox);

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(transactionPane);
            AnchorPane.setLeftAnchor(transactionPane, 0.0);
            AnchorPane.setRightAnchor(transactionPane, 0.0);
            vbox.getChildren().add(anchorPane);
        }
    }

    @FXML
    void btnBack_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setTitle("Admin Dashboard");
        window.setScene(new Scene(root, 800, 500));
    }
}
