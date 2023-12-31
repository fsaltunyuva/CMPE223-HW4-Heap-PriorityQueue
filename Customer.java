// Title: Customer Class
// Description: The Customer class represents a customer with fields for storing their 
//ID, registration year, arrival time, service time, and priority. 
//It implements the Comparable interface and has a compareTo method for comparing customers based on their registration year and ID
public class Customer implements Comparable<Customer> {
	// instance variables to store customer information
	int id;
	int regYear;
	int arrivalTime;
	int serviceTime;
	int priority;

	// constructor to create a Customer object
	public Customer(int id, int regYear, int arrivalTime, int serviceTime) {
		this.id = id;
		this.regYear = regYear;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		// calculate priority based on registration year (more recent years have higher priority)
		this.priority = regYear - 1968;
	}

	// implementation of the compareTo method required by the Comparable interface
	@Override
	public int compareTo(Customer other) {
		// compare registration year first
		if (this.regYear != other.regYear) {
			return (this.regYear - other.regYear);
		}
		// if registration year is the same, compare IDs
		return (this.id - other.id);
	}
}
