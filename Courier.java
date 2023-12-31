// Title: Courier Class
// Description: Implementation of Courier structure with necessary methods and variables for solution.
public class Courier {
	// instance variables to store courier information
	private int id;
	private boolean isAvailable;
	private int deliveryTime;

	// constructor to create a Courier object
	public Courier(int id) {
		this.id = id;
		this.isAvailable = true;
		this.deliveryTime = 1;
	}

	// getters and setters for the instance variables
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
}
