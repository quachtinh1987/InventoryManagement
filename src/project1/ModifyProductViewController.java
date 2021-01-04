/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nam Quach
 */
public class ModifyProductViewController implements Initializable {
    private String exceptionMsg = new String(); 
    @FXML
    private TextField searchField;
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    int productIndex = project1.FXMLDocumentController.modifyProductIndex();
    private int prodID;
    @FXML
    private TextField modProdNameField;
    @FXML
    private TextField modProdInvField;
    @FXML
    private TextField modProdPriceField;
    @FXML
    private TextField modProdMinField;
    @FXML
    private TextField modProdMaxField;
    @FXML
    private Label modProdLabel;

    //Variables for Add Part Table View
    @FXML
    private TableView<Part> modAddPartTable;
    @FXML
    private TableColumn<Part, String> modAddPartNameCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> modAddPartIDCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> modAddPartInvCol;
    @FXML
    private TableColumn<Part, SimpleDoubleProperty> modAddPriceCol;

    //Variables for Delete Part Table View
    @FXML
    private TableView<Part> modDelPartTable;
    @FXML
    private TableColumn<Part, String> modDelPartNameCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> modDelPartIDCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> modDelPartInvCol;
    @FXML
    private TableColumn<Part, SimpleDoubleProperty> modDelPriceCol;

    public void changeToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene mainScene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = Inventory.getProductInventory().get(productIndex);
        prodID = Inventory.getPartInventory().get(productIndex).getPartID();
        modProdLabel.setText(("Auto-Gen: " + prodID));
        modProdNameField.setText(product.getProductName());
        modProdInvField.setText(Integer.toString(product.getProductInventory()));
        modProdPriceField.setText(Double.toString(product.getPrice()));
        modProdMaxField.setText(Integer.toString(product.getProductMax()));
        modProdMinField.setText(Integer.toString(product.getProductMin()));

        modAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modAddPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modAddPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        modAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        modDelPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modDelPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modDelPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        modDelPriceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        updateAddPartTable();
        updateDelPartTable();
    }

    public void updateAddPartTable() {
        modAddPartTable.setItems(Inventory.getPartInventory());
    }

    public void updateDelPartTable() {
        modDelPartTable.setItems(currentParts);
    }

    //Add selected parts from AddTableView to DelTableView
    public void addBtnAddTable() {
        Part selectedPart = modAddPartTable.getSelectionModel().getSelectedItem();
        currentParts.add(selectedPart);
        updateAddPartTable();
    }

    //Delete parts from DelTableView
    public void delBtnDelTable() {
        Part selectedPart = modDelPartTable.getSelectionModel().getSelectedItem();
        currentParts.remove(selectedPart);
        updateDelPartTable();
    }

    @FXML
    void searchButton(ActionEvent event) throws IOException {
        String searchTerm = searchField.getText();
        int index = -1;
        if (Inventory.lookupPart(searchTerm) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Error");
            alert.setHeaderText("Part not found in the database");
            alert.setContentText("The search keyword entered does not match any Part!");
            alert.showAndWait();
        } else {
            index = Inventory.lookupPart(searchTerm);
            Part part = Inventory.getPartInventory().get(index);
            ObservableList<Part> filteredPart = FXCollections.observableArrayList();
            filteredPart.add(part);
            modAddPartTable.setItems(filteredPart);
        }
    }

    //Save product to main view 
    public void modSaveButton(ActionEvent event) throws IOException {
        String prodName = modProdNameField.getText();
        int inv = Integer.parseInt(modProdInvField.getText());
        double price = Double.parseDouble(modProdPriceField.getText());
        int prodMax = Integer.parseInt(modProdMaxField.getText());
        int prodMin = Integer.parseInt(modProdMinField.getText());

        try {
            exceptionMsg = Product.prodValidation(prodName, inv, prodMax, prodMin, price, exceptionMsg, currentParts);
            if (exceptionMsg.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMsg);
                alert.showAndWait();
                exceptionMsg = "";
            } else {
                Product product = new Product(prodID, prodName, inv, price);
                product.setProductID(prodID);
                product.setPrice(price);
                product.setProductInventory(inv);
                product.setProductName(prodName);
                product.setProductMax(prodMax);
                product.setProductMin(prodMin);
                product.setProductParts(currentParts);
                Inventory.updateProduct(productIndex, product);

                Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene mainScene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(mainScene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

}
