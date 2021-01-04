/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.lang.Integer;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Nam Quach
 */
public class AddPartViewController implements Initializable {

    private String exceptionMsg = new String();
    private boolean isOutsourced;
    //Variables for RadioButtions
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSourced;
    @FXML
    private ToggleGroup radioGroup;

    //Variables for addPartTableView
    private int partID;
    @FXML
    private Label idLabel;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    private Label partLabel;
    @FXML
    private TextField machineName;

    //Change Scene back to Main Window 
    public void changeToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene mainScene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }

    public void saveToMain(ActionEvent event) throws IOException {
        String partName = partNameField.getText();
        int inv = Integer.parseInt(invField.getText());
        double price = Double.parseDouble(priceField.getText());
        int partMax = Integer.parseInt(partMaxField.getText());
        int partMin = Integer.parseInt(partMinField.getText());
        String machineID = machineName.getText();

        try {
            exceptionMsg = Part.partValidation(partName, inv, partMax, partMin, price, exceptionMsg);
            if (exceptionMsg.length() > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMsg);
                alert.showAndWait();
                exceptionMsg = "";
            } else {
                if (isOutsourced == false) {
                    InHouse iPart = new InHouse(partID, partName, inv, price);
                    iPart.setPartID(partID);
                    iPart.setPartMax(partMax);
                    iPart.setPartMin(partMin);
                    iPart.setPartName(partName);
                    iPart.setInventoryLevel(inv);
                    iPart.setPricePerUnit(price);
                    iPart.setPartMachineID(Integer.parseInt(machineID));
                    Inventory.addPart(iPart);
                } else {
                    Outsourced oPart = new Outsourced(partID, partName, inv, price);
                    oPart.setPartID(partID);
                    oPart.setPartMax(partMax);
                    oPart.setPartMin(partMin);
                    oPart.setPartName(partName);
                    oPart.setInventoryLevel(inv);
                    oPart.setPricePerUnit(price);
                    oPart.setCompanyName(machineID);
                    Inventory.addPart(oPart);
                }

                Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene mainScene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(mainScene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Allow only 1 radio button to be selected at a time
        radioGroup = new ToggleGroup();
        this.inHouse.setToggleGroup(radioGroup);
        this.outSourced.setToggleGroup(radioGroup);

        //set label ID to autoGen
        partID = Inventory.partIDCount();
        idLabel.setText("Auto Gen: " + partID);
    }

    public void inHouseRadio() {
        isOutsourced = false;
        partLabel.setText("Machine ID");
    }

    public void outSourcedRadio() {
        isOutsourced = true;
        partLabel.setText("Company Name");
    }

}
