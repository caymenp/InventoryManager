package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Model.
 * Handles the creation of products and serves as a template for other methods/classes/models to call to implement
 * products or change existing products.
 */

public class ProductModel{
    //Variable Declaration
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * LOGICAL ERROR.
     * No runtime errors on this one. I did have some issues with the logic of creating the initial associatedParts list.
     * Realizing what class it was supposed to be holding as part.
     */

    //Constructor
    public ProductModel(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    // Add Associated Parts
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    // Delete Associated Part
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        int indexOfRemoval = associatedParts.indexOf(selectedAssociatedPart);
        if (indexOfRemoval != -1) {
            associatedParts.remove(indexOfRemoval);
            return true;
        } else return false;
    }

    //Get All Associated Parts
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
