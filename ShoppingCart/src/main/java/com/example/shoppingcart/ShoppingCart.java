package com.example.shoppingcart;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private static ShoppingCart SINGLETON_INSTANCE;
    private Map<String, CartEntry> entries;

    public ShoppingCart(){
        this.entries = new HashMap<>();
    }

    public static ShoppingCart getInstance(){
        if(SINGLETON_INSTANCE == null){
            SINGLETON_INSTANCE = new ShoppingCart();
        }
        return SINGLETON_INSTANCE;
    }

    public boolean addProduct(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry != null){
            if(productEntry.getProduct().getQty() == productEntry.getQuantity()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Not Enough Stock Is Available");
                alert.showAndWait();
                return false;
            }
            productEntry.increaseQuantity();
            return true;
        }
        else{
            List<Product> p = HomeController.getProducts();
            for(int i = 0; i < p.size(); i++){
                if(p.get(i).getName().equalsIgnoreCase(productName)){
                    if(p.get(i).getQty() > 0){
                        CartEntry entry = new CartEntry(p.get(i),1);
                        entries.put(productName.toUpperCase(), entry);
                        return true;
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setContentText("Not Enough Stock Is Available");
                        alert.showAndWait();
                        return false;
                    }
                }
            }
            return false;
        }
    }

    public void removeProduct(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry != null){
            productEntry.decreaseQuantity();
        }
        if(productEntry.getQuantity() == 0){
            entries.remove(productName.toUpperCase());
        }
    }

    public int getQuantity(String productName){
        CartEntry entry = entries.get(productName.toUpperCase());
        if(entry != null){
            return entry.getQuantity();
        }
        else{
            return 0;
        }
    }

    public float calculateTotal(){
        float total = 0;
        for(CartEntry entry: entries.values()){
            total += entry.getProduct().getPrice() * entry.getQuantity();
        }
        return total;
    }

    public Map<String, CartEntry> getEntries() {
        return entries;
    }

    public List<CartEntry> getEntriesList(){
        return new ArrayList<>(entries.values());
    }

    public void emptyShoppingCart(){
        entries.clear();
    }

}
