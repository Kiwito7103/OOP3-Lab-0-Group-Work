package problemdomain;

public class Vacuum extends Appliance {

    private String grade;
    private int voltage;

    /**
     * @param itemNumber
     * @param brand
     * @param quantity
     * @param wattage
     * @param color
     * @param price
     * @param grade
     * @param voltage
     */
    public Vacuum(int itemNumber, String brand, int quantity, int wattage, String color, double price, String grade, int voltage) {
        super(itemNumber, brand, quantity, wattage, color, price);
        this.setGrade(grade);
        this.setVoltage(voltage);
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return the voltage
     */
    public int getVoltage() {
        return voltage;
    }

    /**
     * @param voltage the voltage to set
     */
    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    @Override
    public String toString() {
        return "Vacuum " + super.toString() + ", " + getGrade() + ", " + getVoltage();

    }
}