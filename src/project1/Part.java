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

/**
 *
 * @author Nam Quach
 */
public abstract class Part {

    private final SimpleStringProperty partName;
    private final IntegerProperty partID, inventoryLevel;
    private final DoubleProperty pricePerUnit;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Part(int partID, String partName, int inventoryLevel, double pricePerUnit) {
        this.partName = new SimpleStringProperty(partName);
        this.partID = new SimpleIntegerProperty(partID);
        this.inventoryLevel = new SimpleIntegerProperty(inventoryLevel);
        this.pricePerUnit = new SimpleDoubleProperty(pricePerUnit);
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    //Getters
    public String getPartName() {
        return partName.get();
    }

    public int getPartID() {
        return partID.get();
    }

    public int getInventoryLevel() {
        return inventoryLevel.get();
    }

    public double getPricePerUnit() {
        return pricePerUnit.get();
    }

    public int getPartMin() {
        return min.get();
    }

    public int getPartMax() {
        return max.get();
    }

    //Setters
    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel.set(inventoryLevel);
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit.set(pricePerUnit);
    }

    public void setPartMin(int min) {
        this.min.set(min);
    }

    public void setPartMax(int max) {
        this.max.set(max);
    }

    //Validate Parts 
    public static String partValidation(String partName, int inv, int min, int max, double price, String errorMessage) {
        if (partName == null) {
            errorMessage += "Part Name is blank";
        }
        if (inv < 1) {
            errorMessage += "The Inventory must be greater than zero";
        }
        if (inv > max || inv < min) {
            errorMessage += "The Inventory must be the values between MIN and MAX";
        }
        if (max < min) {
            errorMessage = errorMessage + ("The inventory MIN must be less than the MAX.");
        }
        if (price < 1) {
            errorMessage += "The price must be greater than zero";
        }
        return errorMessage;
    }

}
