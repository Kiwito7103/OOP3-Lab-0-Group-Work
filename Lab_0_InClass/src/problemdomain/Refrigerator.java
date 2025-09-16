package problemdomain;

public class Refrigerator extends Appliance
{
	private int numDoors;
	private double height;
	private double width;
	
	
//	public Refrigerator()
//	{
//		super();
//	}
	
	
	
	/**
	 * @param itemNumber
	 * @param brand
	 * @param quantity
	 * @param wattage
	 * @param color
	 * @param price
	 * @param numDoors
	 * @param height
	 * @param width
	 */
	public Refrigerator(int itemNumber, String brand, int quantity, int wattage, String color, double price,
			int numDoors, double height, double width) 
	{
		super(itemNumber, brand, quantity, wattage, color, price);
		this.numDoors = numDoors;
		this.height = height;
		this.width = width;
	}


	/**
	 * @return the numDoors
	 */
	public int getNumDoors() {
		return numDoors;
	}


	/**
	 * @param numDoors the numDoors to set
	 */
	public void setNumDoors(int numDoors) {
		this.numDoors = numDoors;
	}


	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}


	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}


	@Override
	public String toString() {
		return "Refrigerator" + super.toString() +  getNumDoors() + ", " + getHeight() + ", " + getWidth();
	}
	
	
	
}
