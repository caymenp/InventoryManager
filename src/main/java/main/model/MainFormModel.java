package main.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * MainForm Program Model.
 * Handles the implementation and running of the main program.
 *
 * JAVADOCS IS IN THE MAIN APPLICATION FOLDER.
 */

public class MainFormModel extends Application {

    /**
     * RUNTIME ERROR.
     * Issues initially with creating the scene and attaching the correct FXML doc. Getting errors related to the program
     * not being able to find the FXML doc. Had to use the full path to the FXML doc.
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFormModel.class.getResource("/main/inventorymanager/MainForm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {


        launch();

    }
}