package com.example.shoppingcart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CartController extends ScrollPane{

    @FXML
    private ScrollPane sp;

    @FXML
    private Label totalPrice;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() throws FileNotFoundException {
        List<CartEntry> entries = ShoppingCart.getInstance().getEntriesList();
        VBox v = new VBox();
        sp.setContent(v);
        v.getChildren().clear();

        if(entries.isEmpty()){
            Label emptyLabel = new Label("Your Shopping Cart is Empty!");
            emptyLabel.setStyle("-fx-font: 20 arial; -fx-padding:10px");
            v.getChildren().add(emptyLabel);
            totalPrice.setText("0");
        }
        else{
            for(CartEntry c:entries){
                if(c.getQuantity() != 0){
                    HBox productView = cartEntryView(c);
                    v.getChildren().add(productView);
                }
            }
            totalPrice.setText(String.valueOf(ShoppingCart.getInstance().calculateTotal()));
        }
    }

    private HBox cartEntryView(CartEntry cartEntry) throws FileNotFoundException {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER_LEFT);

        InputStream input = this.getClass().getClassLoader().getResourceAsStream("images/" + cartEntry.getProduct().getImage());
//        FileInputStream input = new FileInputStream("/FOEASU/3rd Computer/second term/distributed/project/marketplace/ShoppingCart/src/main/resources/images/" + product.getImage());
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitHeight(100);

        Label productName = new Label(cartEntry.getProduct().getName());
        productName.setPrefWidth(100);
        productName.setStyle("-fx-font-size:15px; -fx-padding:5px");

        Label quantity = new Label(String.valueOf(cartEntry.getQuantity()));
        quantity.setStyle("-fx-padding:5px");

        Button plusButton = new Button("+");
        plusButton.setStyle("-fx-padding:5px");
        plusButton.setUserData(cartEntry.getProduct().getName());
        plusButton.setOnAction( e -> {
            String name = (String) ((Node) e.getSource()).getUserData();
            boolean success = ShoppingCart.getInstance().addProduct(name);
            if(success){
                quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
                this.totalPrice.setText(String.valueOf(ShoppingCart.getInstance().calculateTotal()));
            }

        });

        Button minusButton = new Button("-");
        minusButton.setStyle("-fx-padding:5px");
        minusButton.setUserData(cartEntry.getProduct().getName());
        minusButton.setOnAction( e -> {
            String name = (String) ((Node) e.getSource()).getUserData();
            ShoppingCart.getInstance().removeProduct(name);
            quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
            this.totalPrice.setText(String.valueOf(ShoppingCart.getInstance().calculateTotal()));
        });

        Label price = new Label(String.valueOf("EGP " + cartEntry.getProduct().getPrice()));
        price.setStyle("-fx-padding:5px");

        layout.getChildren().addAll(imageView,productName, plusButton, quantity, minusButton, price);

        return layout;
    }

    @FXML
    void btnCancelButton_Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage window = (Stage) cancelButton.getScene().getWindow();
        window.setTitle("Marketplace System");
        window.setScene(new Scene(root, 800, 500));
    }


}
