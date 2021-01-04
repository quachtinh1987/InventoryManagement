/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nam Quach
 */
public class Outsourced extends Part{
    private final StringProperty companyName;

    public Outsourced(int partID, String name, int inventory, double price) {
        super(partID, name, inventory, price);
        companyName = new SimpleStringProperty();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getCompanyName() {
        return this.companyName.get();
    }  
}
