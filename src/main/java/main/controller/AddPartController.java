package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.model.InhouseModel;
import main.model.InventoryModel;
import main.model.OutsourcedModel;

import java.io.IOException;

/**
 * This class handles the adding of a new part component. This class also handles the form attributes changing
 * between inhouse -or- outsourced radio buttons.
 */
public class AddPartController {
    @FXML
    private Stage stage;
    @FXML
    public Text companyNamepart;
    @FXML
    public TextField companyNameFormPart;
    @FXML
    private Text formLabel;

    @FXML
    private Toggle inHouseToggle;

    @FXML
    private Toggle outsourcedToggle;

    @FXML
    private TextField machineIDpartForm;

    @FXML
    private Text machineIDpart;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField AddPartPrice;
    @FXML
    private TextField AddPartMax;
    @FXML
    private TextField AddPartMin;
    @FXML
    private Button addPartSave;
    @FXML
    private Label validationError;



    /**
     * This method dynamically changes the window label to reflect
     * "Modify Part" -or- "Add Part".
     * Uses the btnID param from the ActionEvent of the mouseclick, to go through a
     * switch statement to determine the text for formlabel.
     * @param btnID
     */
    public void setLabel(String btnID) {
        switch (btnID) {
            case "addPartBtn": formLabel.setText("Add Part");
                break;
            case "modifyPartBtn": formLabel.setText("Modify Part");
                break;
        }
    }

    /**
     * This method Toggles the In-House & OutSource Fields on the
     * add/modify part form(s).
     * Uses the actionEvent passed into it by listening for the toggle of the two radio buttons.
     * Determines which radio button is active through a if/else statement and hides/displays the corresponding fields.
     * @param actionEvent
     */
    public void partSceneToggle(ActionEvent actionEvent) {
        if (inHouseToggle.isSelected()){
            machineIDpart.setVisible(true);
            machineIDpartForm.setVisible(true);
            companyNamepart.setVisible(false);
            companyNameFormPart.setVisible(false);
        } else {
            machineIDpart.setVisible(false);
            machineIDpartForm.setVisible(false);
            companyNamepart.setVisible(true);
            companyNameFormPart.setVisible(true);
        }
    }

    /**
     * Unique ID Method For Parts and Products
     */
    public static int unqID = 0;
    public static int uniqueID() {
        unqID++;
        return unqID;
    }

    /**
     * Add a New Part Method
     * @param actionEvent
     * @throws IOException
     */
    public void addNewPart(ActionEvent actionEvent) throws IOException {
        int partID = uniqueID();
        // String Variables
        String partName = addPartName.getText();
        String partStock = addPartInv.getText();
        String partMin = AddPartMin.getText();
        String partMax = AddPartMax.getText();
        String partPrice = AddPartPrice.getText();

        // In-House / Outsourced String Variables
        String machineID = machineIDpartForm.getText();
        String companyName = companyNameFormPart.getText();
/**
 * RUNTIME ERROR.
 * Issues getting the variables to validate. I tried using a separate validation method to call, but since this one had
 * the original actionEvent, I had to use this method.
 */

/**
 * FUTURE ENHANCEMENT.
 * Separate the logic from the validation into a separate controller or method to be called when the actionEvent is triggered.
 */
        // Validated Variable Initialization
        //productName stays the same
        double partPriceDouble = 0.0;
        int partMaxInt = 0;
        int partMinInt = 0;
        int partStockInt = 0;
        int machineIDInt = 0;
        //Company Name stays the same.

        //Checking Part Name -> Checking if blank
        if (partName.isBlank()){
            validationError.setText("Product Name Cannot Be Empty");
            validationError.setVisible(true);
            return;
        }
        //Part Price.
        try{
            partPriceDouble = Double.parseDouble(partPrice);
        } catch (NumberFormatException e){
            validationError.setText("Part Price Must Be Numbers Only, separated by optionally '.'");
            validationError.setVisible(true);
            return;
        }
        //Part Max
        try{
            partMaxInt = Integer.parseInt(partMax);
        } catch (NumberFormatException e){
            validationError.setText("Part Max Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        //Part Min
        try{
            partMinInt = Integer.parseInt(partMin);
        } catch (NumberFormatException e){
            validationError.setText("Part Min Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        //Part Stock
        try{
            partStockInt = Integer.parseInt(partStock);
        } catch (NumberFormatException e){
            validationError.setText("Part Min Must Be Numbers Only.");
            validationError.setVisible(true);
            return;
        }
        // Checking Min-Max to make sure min is lower than max
        if ((partMaxInt < partMinInt) || (partMaxInt <= 0) || (partMinInt <= 0)) {
            validationError.setText("Max has to be larger than Min. Both have to be at least 0");
            validationError.setVisible(true);
            return;
        }
        // Checking Stock to make sure stock is between Min and max
        if ((partStockInt < partMinInt) || (partStockInt > partMaxInt)) {
            validationError.setText("Part Stock has to be in between Min & Max fields.");
            validationError.setVisible(true);
            return;
        }

        //Checking The Toggle Values
        if (inHouseToggle.isSelected()) {
            try {
                machineIDInt = Integer.parseInt(machineID);
            } catch (NumberFormatException e) {
                validationError.setText("Machine ID Must Be Numbers Only.");
                validationError.setVisible(true);
                return;
            }
        } else if (outsourcedToggle.isSelected()) {
            if (companyName.isBlank()) {
                validationError.setText("Company Name cannot be empty");
                validationError.setVisible(true);
                return;
            }
        }


        if (inHouseToggle.isSelected()) {
            InhouseModel newPart = new InhouseModel(partID, partName, partPriceDouble,
                    partStockInt, partMinInt, partMaxInt, machineIDInt);
            InventoryModel.addPart(newPart);
        } else {
            OutsourcedModel newPart = new OutsourcedModel(partID, partName, partPriceDouble,
                    partStockInt, partMinInt, partMaxInt, companyName);
            InventoryModel.addPart(newPart);
        }

        cancelBtn(actionEvent);

    }

    /**
     * Part Form Cancel Button Method.
     * This method listens for an action event passed to it, once the cancel button is clicked
     * it changes the view back to the main view.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/inventorymanager/MainForm-view.fxml"));
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

}