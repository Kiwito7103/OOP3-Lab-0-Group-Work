package problemdomain;

public class Mircrowave extends Appliance {

    private double capacity;
    private String roomType;

    /**
     * @param itemNumber
     * @param brand
     * @param quantity
     * @param wattage
     * @param color
     * @param price
     */
    public Mircrowave(int itemNumber, String brand, int quantity, int wattage, String color, double price, double capacity, String roomType) {
        super(itemNumber, brand, quantity, wattage, color, price);
        this.setCapacity(capacity);
        this.setRoomType(roomType);
    }

    /**
     * @return the capacity
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the roomType
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Microwave " + super.toString() + ", " + getCapacity() + ", " + getRoomType();
    }

}