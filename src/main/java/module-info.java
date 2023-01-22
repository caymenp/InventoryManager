module main.model {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.inventorymanager to javafx.fxml;
    exports main.model;
    exports main.controller;
    opens main.controller to javafx.fxml;
}