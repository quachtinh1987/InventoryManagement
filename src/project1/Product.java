/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nam Quach
 */
public class Product {

    private final SimpleStringProperty productName;
    private final IntegerProperty productID, productInventory, min, max;
    private final DoubleProperty price;
    private static ObservableList<Part> parts = FXCollections.observableArrayList();


    public Product(int productID, String productName, int productInventory, double price) {
        this.productID = new SimpleIntegerProperty(productID);
        this.price = new SimpleDoubleProperty(price);
        this.productInventory = new SimpleIntegerProperty(productInventory);
        this.productName = new SimpleStringProperty(productName);
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    //Getters
    public String getProductName() {
        return productName.get();
    }

    public int getProductID() {
        return productID.get();
    }

    public int getProductInventory() {
        return productInventory.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getProductMin() {
        return min.get();
    }

    public int getProductMax() {
        return max.get();
    }

    //Setters
    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setProductInventory(int productInventory) {
        this.productInventory.set(productInventory);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setProductMin(int min) {
        this.min.set(min);
    }

    public void setProductMax(int max) {
        this.max.set(max);
    }
    
    public void setProductParts(ObservableList<Part> parts)
    {
        this.parts = parts; 
    }

    //Validate Products 
    //Validate Parts 
    public static String prodValidation(String prodName, int inv, int min, int max, double price, String errorMessage, ObservableList<Part> parts) {
        double sumParts = 0.00;
        for (int i = 0; i < parts.size(); i++) {
            sumParts = sumParts + parts.get(i).getPricePerUnit();
        }
        if (prodName == null) {
            errorMessage += "Product Name cannot be blank";
        }
        if (inv < 1) {
            errorMessage += "The Inventory must be greater than zero";
        }
        if (inv > max || inv < min) {
            errorMessage += "The Inventory must be the values between MIN and MAX. ";
        }
        if (max < min) {
            errorMessage = errorMessage + ("The inventory MIN must be less than the MAX.");
        }
        if (price < 1) {
            errorMessage += "The price must be greater than zero";
        }
        if (price < sumParts) {
            errorMessage += "Product price must be greater than total cost of parts.";
        }
        return errorMessage;
    }
}
