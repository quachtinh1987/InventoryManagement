/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.sql.rowset.Predicate;
import static project1.Inventory.getPartInventory;
import project1.Inventory;
import project1.Part;

/**
 *
 * @author Nam Quach
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField partSearchField;
    @FXML
    private TextField prodSearchField;
    private Label label;
    private static int modifyPartIndex;
    private static Part modifyPart;
    private static int modifyProductIndex;
    private static Product modifyProduct;

    //return the index of the selected Part
    public static int modifyPartIndex() {
        return modifyPartIndex;
    }

    //return the index of the selected Product
    public static int modifyProductIndex() {
        return modifyProductIndex;
    }

    //Configure Part table 
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> partIDCol;
    @FXML
    private TableColumn<Part, SimpleIntegerProperty> inventoryLevelCol;
    @FXML
    private TableColumn<Part, SimpleDoubleProperty> priceCol;

    //Configure Product Table
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, SimpleIntegerProperty> productIDCol;
    @FXML
    private TableColumn<Product, SimpleIntegerProperty> productInventoryCol;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productPriceCol;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        //Add col to product tablej
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("productInventory"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //load values for Part table 
        partTableView.setItems(Inventory.getPartInventory());

        //load values for Product table
        productTableView.setItems(Inventory.getProductInventory());

        //update Part Table
        updatePartTable();

        //update Product Table
        updateProductTable();

    }

    /**
     * public ObservableList<Part> getPart() { ObservableList<Part> part =
     * FXCollections.observableArrayList(); part.add(new Part(1, "Part 1", 5,
     * 5.00) { }); part.add(new Part(2, "Part 2", 10, 10.00) { }); part.add(new
     * Part(3, "Part 3", 12, 15.00) { }); return part; }
     *
     * add values to Product table public ObservableList<Product> getProduct() {
     * ObservableList<Product> product = FXCollections.observableArrayList();
     * product.add(new Product(1, "Product 1", 5, 5.00) { }); product.add(new
     * Product(2, "Product 2", 10, 10.00) { }); product.add(new Product(3,
     * "Product 3", 12, 15.00) { }); return product;
    }*
     */
    //Change Scene to AddPartView 
    public void changeToAddPart(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddPartView.fxml"));
        Scene addPartScene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.show();
    }

    //Change Scene to ModifyPartView 
    public void ModifyPartScene(ActionEvent event) throws IOException {
        modifyPart = partTableView.getSelectionModel().getSelectedItem(); //get the selected Part from MAIN TABLE VIEW
        modifyPartIndex = Inventory.getPartInventory().indexOf(modifyPart);
        Parent parent = FXMLLoader.load(getClass().getResource("ModifyPartView.fxml"));
        Scene modifyPart = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(modifyPart);
        stage.show();
    }

    //Change Scene to AddProductView  
    public void addProductScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddProductsView.fxml"));
        Scene addProduct = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addProduct);
        stage.show();
    }

    //Change Scene to ModifyProductView  
    public void ModifyProductScene(ActionEvent event) throws IOException {
        modifyProduct = productTableView.getSelectionModel().getSelectedItem(); //get the selected Product from MAIN TABLE VIEW
        modifyProductIndex = Inventory.getProductInventory().indexOf(modifyProduct);
        Parent parent = FXMLLoader.load(getClass().getResource("ModifyProductView.fxml"));
        Scene modifyProduct = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(modifyProduct);
        stage.show();
    }

    //This method will delete the selected Row from the Part table
    public void deletePart() {
        ObservableList<Part> selectedParts, allParts;
        allParts = partTableView.getItems();
        selectedParts = partTableView.getSelectionModel().getSelectedItems();

        //remove selected part
        for (Part part : selectedParts) {
            allParts.remove(part);
        }
    }

    //This method will delete the selected Row from the Product Table
    public void deleteProduct() {
        ObservableList<Product> selectedProduct, allProducts;
        allProducts = productTableView.getItems();
        selectedProduct = productTableView.getSelectionModel().getSelectedItems();

        //remove selected part
        for (Product product : selectedProduct) {
            allProducts.remove(product);
        }
    }

    //method to update rows in Part Table
    public void updatePartTable() {
        partTableView.setItems(Inventory.getPartInventory());
    }

    //method to update rows in Product Table
    public void updateProductTable() {
        productTableView.setItems(Inventory.getProductInventory());
    }

    @FXML
    void searchPartButton(ActionEvent event) throws IOException {
        String searchTerm = partSearchField.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchTerm) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Error");
            alert.setHeaderText("Part not found in the database");
            alert.setContentText("The search keyword entered does not match any Part!");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchTerm);
            Part part = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> filteredPart = FXCollections.observableArrayList();
            filteredPart.add(part);
            partTableView.setItems(filteredPart);
        }
    }

    @FXML
    void searchProButton(ActionEvent event) throws IOException {
        String searchTerm = prodSearchField.getText();
        int prodIndex = -1;
        if (Inventory.lookupProd(searchTerm) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Error");
            alert.setHeaderText("Part not found in the database");
            alert.setContentText("The search keyword entered does not match any Part!");
            alert.showAndWait();
        } else {
            prodIndex = Inventory.lookupProd(searchTerm);
            Product product = Inventory.getProductInventory().get(prodIndex);
            ObservableList<Product> filteredProd = FXCollections.observableArrayList();
            filteredProd.add(product);
            productTableView.setItems(filteredProd);
        }
    }

    @FXML
    void MainExitClick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("You clicked cancel. Please continue your work!");
        }
    }

}
