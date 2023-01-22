package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory Model.
 * Handles the implementation and serves as a main holding ground to house all parts and products in one place.
 * Also allows other methods to call here to get any info about parts or products.
 */

public class InventoryModel {
    // Variable Declaration
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<ProductModel> allProducts = FXCollections.observableArrayList();

    // Custom Methods
    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(ProductModel product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static ProductModel lookupProduct(int productId) {
        for (ProductModel product : getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> allParts = getAllParts();
        ObservableList<Part> partSearch = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName)) {
                partSearch.add(part);
            }
        }

        return partSearch;
    }

    public static ObservableList<ProductModel> lookupProduct(String productName) {
        ObservableList<ProductModel> allProducts = getAllProducts();
        ObservableList<ProductModel> productSearch = FXCollections.observableArrayList();

        for (ProductModel product : allProducts) {
            if (product.getName().toLowerCase().contains(productName)) {
                productSearch.add(product);
            }
        }

        return productSearch;
    }

    public static void updatePart(int index, Part selectedPart) {

        getAllParts().remove(index);


    }

    public static void updateProduct(int index, ProductModel selectedProject) {
       ProductModel productUpdate = allProducts.get(index);

    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(ProductModel selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<ProductModel> getAllProducts() {
        return allProducts;
    }
}
