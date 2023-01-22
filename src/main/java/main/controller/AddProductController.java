package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.InventoryModel;
import main.model.Part;
import main.model.ProductModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Add Product Controller.
 * Handles the addition of new parts within the program. Allows parts to be added/removed from a product during the addition.
*/

public class AddProductController implements Initializable {
    public Button addProductPart;
    public TextField newProductName;
    public TextField newProductInv;
    public TextField newProductPrice;
    public TextField newProductMax;
    public TextField newProductMin;
    @FXML
    private TextField productPartSearch;
    public TableColumn<Part, Double> allPartPrice;
    public TableColumn<Part, Integer> allPartInv;
    public TableColumn<Part, String> allPartName;
    public TableColumn<Part, Integer> allPartID;
    public TableView<Part> allParts;
    public Button removeProductPart;
    public Button saveNewProduct;
    public TableView<Part> productParts;
    public TableColumn<Part, Integer> productPartID;
    public TableColumn<Part, String> productPartName;
    public TableColumn<Part, Integer> productPartInv;
    public TableColumn<Part, Double> productPartPrice;
    @FXML
    Stage stage;
    @FXML
    private Label formLabel;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField newProductID;
    @FXML
    private Label validationError;
    /**
     * LOGICAL ERROR.
     * Initially had some issues getting the parts to load for each individual product and be unique
 * to those products. I fixed this by setting up an empty arraylist for the new products parts. As parts are added
 * to the product in the application they are added to this "temp" part list. When saved, there is loop that takes the
 * parts from the temp parts list and adds them to the products part list after the product constructor is complete.
     * */

    ObservableList<Part> newProductList = FXCollections.observableArrayList();


    /**
     * Unique ID Method For Parts and Products
     */
    public static int unqID = 1;
    public static int uniqueID() {
        unqID = unqID + 2;
        return unqID;
    }

    public void saveNewProduct(ActionEvent actionEvent) throws IOException{
        //Strings Variables to Validate
        String productName = newProductName.getText();

        //Number variables to validate -> Still In String Format
        int productID = uniqueID();
        String productPrice = newProductPrice.getText();
        String productStock = newProductInv.getText();
        String productMin = newProductMin.getText();
        String productMax = newProductMax.getText();

        // Validated Variable Initialization
        double productPriceDouble = 0.00;
        int productMaxInt = 0;
        int productMinInt = 0;
        int productStockInt = 0;

        //Product Name -> Checking if blank
        if (productName.isBlank()) {
            validationError.setText("Product Name Cannot Be Blank");
            validationError.setVisible(true);
            return;
        }
        //Product Price. Trying to parse as Double, rename to productPriceDouble.
        try {
            productPriceDouble = Double.parseDouble(productPrice);
        } catch (NumberFormatException e) {
            validationError.setText("Product Price Must Be Numbers Only, separated by optionally '.'");
            validationError.setVisible(true);
            return;
        }
        //Product Max. Trying to parse as int, rename to productMaxInt.
        try {
            productMaxInt = Integer.parseInt(productMax);
        } catch (NumberFormatException e) {
            validationError.setText("Product Max Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        //Product Min. Trying to parse as int, rename to productMinInt.
        try {
            productMinInt = Integer.parseInt(productMin);
        } catch (NumberFormatException e) {
            validationError.setText("Product Min Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        //Product Stock. Trying to parse as Int, rename to productStockInt.
        try {
            productStockInt = Integer.parseInt(productStock);
        } catch (NumberFormatException e) {
            validationError.setText("Product Stock Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        // Checking to see if Max is greater than Min and both are above 0.
        if ((productMaxInt < productMinInt) || (productMaxInt <= 0) || (productMinInt <= 0)) {
            validationError.setText("Max has to be larger than Min. Both have to be at least 0");
            validationError.setVisible(true);
            return;
        }
        //Checking to see if Stock is in between Min & Max
        if ((productStockInt < productMinInt) || (productStockInt > productMaxInt)) {
            validationError.setText("Product Stock has to be in between Min & Max fields.");
            validationError.setVisible(true);
            return;
        }




        ProductModel productModel = new ProductModel(
                productID,
                productName,
                productPriceDouble,
                productStockInt,
                productMinInt,
                productMaxInt
        );


        for (Part part : newProductList) {
            productModel.addAssociatedPart(part);
        }



        InventoryModel.addProduct(productModel);
        cancelBtn(actionEvent);
    }

    public void removeProductPart(ActionEvent actionEvent) {

        Part partToRemove = productParts.getSelectionModel().getSelectedItem();
        newProductList.remove(partToRemove);
    }

    public void addProductPart(ActionEvent actionEvent) {
        Part addPart = allParts.getSelectionModel().getSelectedItem();

        newProductList.add(addPart);

    }

    public void productPartSearch(ActionEvent actionEvent) {
        String searchString = productPartSearch.getText().toLowerCase();
        ObservableList<Part> returnedSearch = InventoryModel.lookupPart(searchString);

        if (returnedSearch.size() == 0) {
            try {
                int searchID = Integer.parseInt(searchString);
                Part part = InventoryModel.lookupPart(searchID);
                if (part != null) {
                    returnedSearch.add(part);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

        if(!returnedSearch.isEmpty()) {
            allParts.getSelectionModel().clearSelection();
            allParts.setItems(returnedSearch);
            productPartSearch.setText("");
            if (returnedSearch.size() == 1) {
                allParts.getSelectionModel().selectFirst();
            }
        } else searchErrorWindow("parts");

    }

    private void searchErrorWindow(String errorType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No matching " + errorType);
        alert.setHeaderText(null);
        alert.setContentText("No matching " + errorType + " found for search");
        alert.showAndWait();
            allParts.getSelectionModel().clearSelection();
            allParts.setItems(InventoryModel.getAllParts());
            productPartSearch.setText("");
    }


    // Setting Label Text Dynamically When Scenes Switch
    public void setLabel(String btnID) {
        switch (btnID) {
            case "addProductBtn": formLabel.setText("Add Product");
                break;
            case "modifyProductBtn": formLabel.setText("Modify Product");
                break;
        }
    }



    // Cancel Button
    public void cancelBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/inventorymanager/MainForm-view.fxml"));
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  FUTURE ENHANCEMENT: Only show the available parts that aren't already assigned to the product from "all parts"
     *  instead of just showing a static table of all parts. With that, you would need to implement a count for the part,
     *  since you could add more of one part.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initializing Parts Table for Run
        allPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        allParts.setItems(InventoryModel.getAllParts());

        productPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productParts.setItems(newProductList);

    }
}