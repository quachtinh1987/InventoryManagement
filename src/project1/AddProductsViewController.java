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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nam Quach
 */
public class AddProductsViewController implements Initializable {

    private String exceptionMsg = new String();
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    @FXML
    TextField searchField;
    //Variables for Add Product Table View 
    private int productID;
    @FXML
    private Label productIDField;
    @FXML
    private TextField prodNameField;
    @FXML
    private TextField prodInvField;
    @FXML
    private TextField prodPriceField;
    @FXML
    private TextField prodMaxField;
    @FXML
    private TextField prodMinField;

    //Variables for Add Part Table View
    @FXML
    private TableView<Part> addPartTable;
    @FXML
    private TableColumn<Part, String> addPartNameCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> addPartIDCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> addPartInvCol;
    @FXML
    private TableColumn<Part, SimpleDoubleProperty> addPriceCol;

    //Variables for Delete Part Table View
    @FXML
    private TableView<Part> delPartTable;
    @FXML
    private TableColumn<Part, String> delPartNameCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> delPartIDCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> delPartInvCol;
    @FXML
    private TableColumn<Part, SimpleDoubleProperty> delPriceCol;

    public void changeToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene mainScene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();
    }

    //Save product to main view 
    public void saveButton(ActionEvent event) throws IOException {
        String prodName = prodNameField.getText();
        int inv = Integer.parseInt(prodInvField.getText());
        double price = Double.parseDouble(prodPriceField.getText());
        int prodMax = Integer.parseInt(prodMaxField.getText());
        int prodMin = Integer.parseInt(prodMinField.getText());

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
                Product product = new Product(productID, prodName, inv, price);
                product.setProductID(productID);
                product.setPrice(price);
                product.setProductInventory(inv);
                product.setProductName(prodName);
                product.setProductMax(prodMax);
                product.setProductMin(prodMin);
                Inventory.addProduct(product);

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID = Inventory.productIDCount();
        productIDField.setText("Auto Gen: " + productID);

        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        delPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        delPartInvCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        delPriceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        updateAddPartTable();
        updateDelPartTable();
    }

    public void updateAddPartTable() {
        addPartTable.setItems(Inventory.getPartInventory());
    }

    public void updateDelPartTable() {
        delPartTable.setItems(currentParts);
    }

    //Add selected parts from AddTableView to DelTableView
    public void addBtnAddTable() {
        Part selectedPart = addPartTable.getSelectionModel().getSelectedItem();
        currentParts.add(selectedPart);
        updateAddPartTable();
    }

    //Delete parts from DelTableView
    public void delBtnDelTable() {
        Part selectedPart = delPartTable.getSelectionModel().getSelectedItem();
        currentParts.remove(selectedPart);
        updateDelPartTable();
    }

    @FXML
    void searchButton(ActionEvent event) throws IOException {
        String searchTerm = searchField.getText();
        int index = -1;
        if (Inventory.lookupPart(searchTerm) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Search Error");
            alert.setHeaderText("Product not found in the database");
            alert.setContentText("The search keyword entered does not match any Product!");
            alert.showAndWait();
        } else {
            index = Inventory.lookupProd(searchTerm);
            Part part = Inventory.getPartInventory().get(index);
            ObservableList<Part> filteredProd = FXCollections.observableArrayList();
            filteredProd.add(part);
            addPartTable.setItems(filteredProd);
        }
    }
}
