package main.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.model.InventoryModel;
import main.model.Part;
import main.model.ProductModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Form Controller.
 *
 * This controller handles the main program and also facilitates the transitions of scenes and passing data to the target
 * scenes. Also handles the initial load of the program by populating the data for the tables.
 *
 * LOGICAL ERROR : Initially had issues getting the Stage and Scenes setup, especially for the hand-off scenes. I utilized
 * the videos and external resources to compare Maven JAVAFX with the others that are shown. Used a combination of these
 * resources to come to the conclusion of passing the data from the originating screen by first finding the controller
 * of the target and then setting a static property on that target to accept and hold the items.
 *
 *  FUTURE ENHANCEMENT: Add double click to the table to be able to modify existing parts and products instead of having
 *  to click on the buttons.
 */

public class MainFormController implements Initializable {

    //Buttons
    @FXML
    private Button addPartBtn;
    @FXML
    private Button modifyPartBtn;
    @FXML
    private Button deletePartBtn;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button modifyProductBtn;
    @FXML
    private Button deleteProductBtn;

    // Main Program Exit Button
    @FXML
    private Button mainExitBtn;
    @FXML
    private Stage stage;

    //Table View For Parts Table On Main Page
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;
    @FXML
    private TableColumn<Part, Double> partPriceCost;

    //Table View For Products Table On Main Page
    @FXML
    private TableView<ProductModel> productsTable;
    @FXML
    private TableColumn<ProductModel, Integer> productIDColumn;
    @FXML
    private TableColumn<ProductModel, String> ProductNameColumn;
    @FXML
    private TableColumn<ProductModel, Integer> ProductINVColumn;
    @FXML
    private TableColumn<ProductModel, Double> ProductPriceColumn;

    @FXML
    private TextField partSearchBar;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField productSearchBar;
    @FXML
    private Button productSearchButton;
    @FXML
    private Text didNotDelete;



    // Change Screens
    private String btnID;

    public void BtnPress(ActionEvent actionEvent) throws IOException {
        Button btn = (Button)actionEvent.getSource();
        btnID = btn.getId();
        switch (btnID) {
            case "addPartBtn": partBtnsController(actionEvent);
                break;
            case "addProductBtn", "modifyProductBtn": productBtnsController(actionEvent);
                break;

        }
    }

    public void modifyPart(ActionEvent actionEvent) throws IOException {

        if (partsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("");
            alert.setContentText("Please select a part from the table above to modify.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/inventorymanager/ModifyPartForm-view.fxml"));
        loader.load();
        ModifyPartController mpc = loader.getController();
        mpc.getPart(partsTable.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void modifyProductBtn(ActionEvent actionEvent) throws IOException {

        if (productsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("");
            alert.setContentText("Please select a product from the table above to modify.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/inventorymanager/ModifyProductForm-view.fxml"));
        loader.load();
        ModifyProductController mpc = loader.getController();
        mpc.getProduct(productsTable.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }



    private void partBtnsController(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/inventorymanager/AddPartForm-view.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        //Set Label Dynamically
        AddPartController addPartController = fxmlLoader.getController();
        addPartController.setLabel(btnID);
        stage.setTitle("Part");
        stage.setScene(scene);
        stage.show();
    }


    private void productBtnsController(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/inventorymanager/AddProductForm-view.fxml"));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        //Set Label Dynamically
        AddProductController addProductController = fxmlLoader.getController();
        addProductController.setLabel(btnID);
        stage.setTitle("Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Part Search Bar Method
     * @param
     */
    public void partSearch(ActionEvent actionEvent) {
        String searchString = partSearchBar.getText().toLowerCase();
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
            partsTable.getSelectionModel().clearSelection();
            partsTable.setItems(returnedSearch);
            partSearchBar.setText("");
            if (returnedSearch.size() == 1) {
                partsTable.getSelectionModel().selectFirst();
            }
        } else searchErrorWindow("parts");

    }


    /**
     * Product Search Bar Method
     * @param
     */
    public void productSearch(ActionEvent actionEvent) {
        String searchString = productSearchBar.getText().toLowerCase();
        ObservableList<ProductModel> returnedSearch = InventoryModel.lookupProduct(searchString);

        if (returnedSearch.size() == 0) {
            try {
                int searchID = Integer.parseInt(searchString);
                ProductModel product = InventoryModel.lookupProduct(searchID);
                if (product != null) {
                    returnedSearch.add(product);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

        if(!returnedSearch.isEmpty()) {
            productsTable.getSelectionModel().clearSelection();
            productsTable.setItems(returnedSearch);
            productSearchBar.setText("");
            if (returnedSearch.size() == 1) {
                productsTable.getSelectionModel().selectFirst();
            }
        } else searchErrorWindow("products");

    }


    private void searchErrorWindow(String errorType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No matching " + errorType);
        alert.setHeaderText(null);
        alert.setContentText("No matching " + errorType + " found for search");
        alert.showAndWait();
        if (errorType == "parts") {
            partsTable.getSelectionModel().clearSelection();
            partsTable.setItems(InventoryModel.getAllParts());
            partSearchBar.setText("");
        } else if (errorType == "products") {
            productsTable.getSelectionModel().clearSelection();
            productsTable.setItems(InventoryModel.getAllProducts());
            productSearchBar.setText("");
        }
    }

    public void mainExitBtn(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Method for Deleting Product
     * @param actionEvent
     */

    public void deleteProduct(ActionEvent actionEvent) {

        if (productsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("");
            alert.setContentText("Please select a product from the table above to delete.");
            alert.showAndWait();
            return;
        }

        ProductModel deleteProduct = productsTable.getSelectionModel().getSelectedItem();

        if (!deleteProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Has Associated Parts");
            alert.setHeaderText("");
            alert.setContentText("This Product has associated parts. Please remove the parts before deleting the product.");
            alert.showAndWait();
            return;
        }


        if (deleteProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to delete " + deleteProduct.getName() + "? This cannot be undone");
            alert.showAndWait();

            if (alert.getResult().getText().equals("OK")) {
                InventoryModel.deleteProduct(deleteProduct);
                didNotDelete.setText("Deleted Successfully");
                didNotDelete.setVisible(true);
            }else {
                didNotDelete.setText("Did not delete");
                didNotDelete.setVisible(true);
            }
        } else {
            didNotDelete.setText("Please select an item from either table to delete");
            didNotDelete.setVisible(true);
        }
    }

    /**
     * Method for Deleting Part
     * @param actionEvent
     */
    public void deletePart(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("");
            alert.setContentText("Please select a part from the table above to delete.");
            alert.showAndWait();
            return;
        }

        Part deletePart = partsTable.getSelectionModel().getSelectedItem();

        if (deletePart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to delete " + deletePart.getName() + "? This cannot be undone");
            alert.showAndWait();

            if (alert.getResult().getText().equals("OK")) {
                InventoryModel.deletePart(deletePart);
                didNotDelete.setText("Deleted Successfully");
                didNotDelete.setVisible(true);
            }else {
                didNotDelete.setText("Did not delete");
                didNotDelete.setVisible(true);
            }

        }else {
            didNotDelete.setText("Please select an item from either table to delete");
            didNotDelete.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initializing Parts Table for Run
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(InventoryModel.getAllParts());

        //Initializing Products Table for Run
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductINVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(InventoryModel.getAllProducts());

    }
}