package main.model;

/**
 * In-House Part Model.
 *
 * Handles the implementation of the InHouse part type, by calling the super constructor to ensure this object becomes
 * a Part object, while holding its own unique values.
 */

public class InhouseModel extends Part {

    // Variable Declaration
    private int machineID;
    public static int unqID = 0;

    /**
     * LOGICAL ERROR : Had issues with the polymorphism and inheritance of this type of part with the larger parent part.
     * Getting a better understanding of how to separate these classes within the program and ensure that the abstract Part
     * class only served as a template.
     */

    // Constructor using Super(Part). Adding machineID through polymorphism.
    public InhouseModel(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Method to Create Unique ID's
     * @return unqID
     */

    /**
     *  FUTURE ENHANCEMENT: Different unique part numbers to be able to distinguish these from outsourced parts.
     */

    public static int uniqueID(){
        return unqID++;
    }

    //Getter
    public int getMachineID() {
        return machineID;
    }

    //Setter
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
