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
import org.json.simple.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private Button cartButton;

    @FXML
    private Button info;

    @FXML
    private ScrollPane sp;

    @FXML
    private TextField searchText;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private Button history;

    @FXML
    private Button logOut;

    @FXML
    private Button dashboard_btn;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String username;
    public static boolean isAdmin = false;

    List<Product> p;

    @FXML
    public void initialize() throws FileNotFoundException {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "All",
                        "Makeup",
                        "Clothing",
                        "Mobile Accessories",
                        "TVs and Electronics",
                        "Sports",
                        "Food",
                        "Home care and cleaning",
                        "Home and kitchen",
                        "House appliances",
                        "Mobile phones"
                );
        combo.setItems(options);
        int x = 0, y = 0;
        p= getProducts();
        GridPane productGridPanel = new GridPane();
        productGridPanel.setHgap(30);
        productGridPanel.setVgap(30);
        sp.setContent(productGridPanel);
        productGridPanel.getChildren().clear();
        productGridPanel.setPadding(new Insets(40, 40, 40, 40));

        for(int i = 0; i < p.size(); i++){
            VBox oneProductView = productView(p.get(i));
            productGridPanel.add(oneProductView,x,y);
            x++;
            if(x == 3){
                x = 0;
                y++;
            }
        }
        if (isAdmin){
            dashboard_btn.setVisible(true);
        }
    }
    @FXML
    static List<Product> getProducts(){
        int size=0;
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        json.put("products", "All");
        String dist = "get-all-products";
        JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
        size=Integer.parseInt((String) serverResponse.get("size"));
        List<Product> products = new ArrayList<>(size);
        for(int i =0 ; i<size ; i++){
            Product p = new Product();
            p.setName((String) serverResponse.get("n"+String.valueOf(i)));
            p.setPrice(Float.valueOf((String) serverResponse.get("p"+String.valueOf(i))));
            p.setQty(Integer.valueOf((String) serverResponse.get("q"+String.valueOf(i))));
            p.setCategory((String) serverResponse.get("c"+String.valueOf(i)));
            p.setImage((String) serverResponse.get("i"+String.valueOf(i)));
            products.add(p);
        }
        return products;
    }


    
