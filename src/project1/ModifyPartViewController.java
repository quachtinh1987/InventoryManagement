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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nam Quach
 */
public class ModifyPartViewController implements Initializable {
    private String exceptionMsg = new String (); 
    //Variables for RadioButtions
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSourced;
    @FXML
    private ToggleGroup radioGroup;
    
//Variables for modifyPartTableViewp
    private boolean isOutsourced; 
    private int partID;
    int partIndex = project1.FXMLDocumentController.modifyPartIndex(); 
    
    @FXML
    private Label modID;
    @FXML
    private TextField modPartNameField;
    @FXML
    private TextField modInvField;
    @FXML
    private TextField modPriceField;
    @FXML
    private TextField modPartMinField;
    @FXML
    private TextField modPartMaxField;
    @FXML
    private Label modPartLabel;
    @FXML private TextField modNameField;   
    
    
    //Change Scene back to Main Window 
    public void changeToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene mainScene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }

    //save modified data to main table view
    public void saveModifyToMain(ActionEvent event) throws IOException
    {
        String partName = modPartNameField.getText();
        int inv = Integer.parseInt(modInvField.getText());
        double price = Double.parseDouble(modPriceField.getText());
        int partMax = Integer.parseInt(modPartMaxField.getText());
        int partMin = Integer.parseInt(modPartMinField.getText());
        String machineID = modNameField.getText(); 
        
        try{
            exceptionMsg = Part.partValidation(partName, inv, partMax, partMin, price, exceptionMsg); 
       if (exceptionMsg.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMsg);
                alert.showAndWait();
                exceptionMsg = "";
            }
       else{
        if (isOutsourced == false) {
            InHouse iPart = new InHouse(partID, partName, inv, price);
            iPart.setPartID(partID);
            iPart.setPartMax(partMax);
            iPart.setPartMin(partMin);
            iPart.setPartName(partName);
            iPart.setInventoryLevel(inv);
            iPart.setPricePerUnit(price);
            iPart.setPartMachineID(Integer.parseInt(machineID));
            Inventory.updatePart(partIndex, iPart);
        } 
        else {
            Outsourced oPart = new Outsourced(partID, partName, inv, price);
            oPart.setPartID(partID); 
            oPart.setPartMax(partMax);
            oPart.setPartMin(partMin);
            oPart.setPartName(partName);
            oPart.setInventoryLevel(inv);
            oPart.setPricePerUnit(price);
            oPart.setCompanyName(machineID); 
            Inventory.updatePart(partIndex, oPart);
        }

        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene mainScene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }
    }catch(NumberFormatException e)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        
        Part part = Inventory.getPartInventory().get(partIndex); 
        partID = Inventory.getPartInventory().get(partIndex).getPartID();
        modID.setText(("Auto-Gen: " + partID));
        modPartNameField.setText(part.getPartName()); 
        modInvField.setText(Integer.toString(part.getInventoryLevel())); 
        modPriceField.setText(Double.toString(part.getPricePerUnit()));
        modPartMaxField.setText(Integer.toString(part.getPartMax()));
        modPartMinField.setText(Integer.toString(part.getPartMin()));
        if (part instanceof InHouse)
        {
            modPartLabel.setText("Machine ID");
            modNameField.setText(Integer.toString(((InHouse) Inventory.getPartInventory().get(partIndex)).getPartMachineID())); 
            inHouse.setSelected(true);
        }
    }
    
    public void inHouseRadio() {
        isOutsourced = false;
        modPartLabel.setText("Machine ID");
    }

    public void outSourcedRadio() {
        isOutsourced = true;
        modPartLabel.setText("Company Name");
    }

}
