package problemdomain;

public class Dishwasher extends Appliance
{
	private String feature;
	private String soundRating;
	/**
	 * @param itemNumber
	 * @param brand
	 * @param quantity
	 * @param wattage
	 * @param color
	 * @param price
	 * @param feature
	 * @param soundRating
	 */
	public Dishwasher(int itemNumber, String brand, int quantity, int wattage, String color, double price,
			String feature, String soundRating) {
		super(itemNumber, brand, quantity, wattage, color, price);
		this.feature = feature;
		this.soundRating = soundRating;
	}
	
	/**
	 * @return the feature
	 */
	public String getFeature() {
		return feature;
	}
	/**
	 * @param feature the feature to set
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}
	/**
	 * @return the soundRating
	 */
	public String getSoundRating() {
		return soundRating;
	}
	/**
	 * @param soundRating the soundRating to set
	 */
	public void setSoundRating(String soundRating) {
		this.soundRating = soundRating;
	}
}
