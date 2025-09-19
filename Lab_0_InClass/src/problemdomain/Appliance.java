package problemdomain;

public abstract class Appliance {

    // Attributes
    private int itemNumber;
    private String brand;
    private int quantity;
    private int wattage;
    private String color;
    private double price;

    /**
     * Constructs an Appliance object with specified attributes
     *
     * @param itemNumber
     * @param brand
     * @param quantity
     * @param wattage
     * @param color
     * @param price
     */
    public Appliance(int itemNumber, String brand, int quantity, int wattage, String color, double price) {
        super();
        this.itemNumber = itemNumber;
        this.brand = brand;
        this.quantity = quantity;
        this.wattage = wattage;
        this.color = color;
        this.price = price;
    }

    /**
     * @return the itemNumber
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     * @param itemNumber the itemNumber to set
     */
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the wattage
     */
    public int getWattage() {
        return wattage;
    }

    /**
     * @param wattage the wattage to set
     */
    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Appliance " + getItemNumber() + ", " + getBrand() + ", " + getQuantity()
                + ", " + getWattage() + ", " + getColor() + ", " + getPrice();
    }

    public void reduceQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}