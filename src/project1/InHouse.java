/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Nam Quach
 */
public class InHouse extends Part {
    private final IntegerProperty machineID;

    public InHouse(int partID, String name, int inventory, double price) {
        super(partID, name, inventory, price);
        machineID = new SimpleIntegerProperty();
    }

    public void setPartMachineID(int machineID) {
        this.machineID.set(machineID);
    }

    public int getPartMachineID() {
        return this.machineID.get();
    }    
}
