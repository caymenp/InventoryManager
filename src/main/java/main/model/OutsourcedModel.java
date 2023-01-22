package main.model;

/**
 * Outsourced Part Model.
 *
 * Handles the implementation of the outsourced part type, by calling the super constructor to ensure this object becomes
 * a Part object, while holding its own unique values.
 */

public class OutsourcedModel extends Part {

    //Variable Declaration
    private String companyName;

    /**
     * LOGICAL ERROR : Had issues with the polymorphism and inheritance of this type of part with the larger parent part.
     * Getting a better understanding of how to separate these classes within the program and ensure that the abstract Part
     * class only served as a template.
     */

    //Constructor Using Parent Method (Part). Adding companyName string with polymorphism
    public OutsourcedModel(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // Getter
    public String getCompanyName() {
        return companyName;
    }

    /**
     *  FUTURE ENHANCEMENT: Different unique part numbers to be able to distinguish these from In-House parts.
     */

    // Setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
