// Title: Customer Class
// Description: Main class is the entry point of the program. 

//It simulates the delivery process for a store with different numbers of couriers.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Main {
	/**
	 * This is the main method of the program. It simulates the delivery process
	 * with different numbers of couriers,
	 * and calculates the average waiting time for each simulation. It stops when
	 * the average waiting time is below
	 * the given maximum average waiting time.
	 */
	public static void main(String[] args) {
		// Initialize the priority queue for customers and the lists for couriers and outputs
		HeapPriorityQueue<Customer> customers = new HeapPriorityQueue<>(200); // Priority queue with capacity 200
		ArrayList<Courier> couriers = new ArrayList<>(); // Arraylist that stores couriers
		ArrayList<String> outputs = new ArrayList<>(); // Arraylist that stores outputs to print at the end

		// Initialize variables for tracking the total wait time and average wait time
		int totalWait = 0; // sum of all wait times
		int averageWaitTime = 0; // average wait time (total wait time/customer count)

		// Initialize the scanner for user input
		Scanner scanner = new Scanner(System.in);

		// Prompt the user for the input filename
		System.out.println("Enter input filename:");
		String filename = scanner.next(); // input by user in order to decide the txt file

		// Read the customer data from the file and add it to the priority queue
		readDataAndAddToQueue(filename, customers);

		// Prompt the user for the maximum average waiting time
		System.out.println("Enter the maximum average waiting time: ");
		averageWaitTime = scanner.nextInt();

		// Initialize variables for the current time and number of couriers
		int currentTime = 1;
		int numCouriers = 2; //In the beginning, we assigned 2 couriers as stated

		// Loop until the average waiting time is below the given maximum
		do {
			// Clear the outputs list for the next simulation
			outputs.clear();

			// Reset the total wait time
			totalWait = 1;

			// Create the couriers for this simulation
			for (int i = 0; i < numCouriers; i++) {
				couriers.add(new Courier(i));
			}

			// Add output for the number of couriers and the start of the simulation
			outputs.add("");
			outputs.add("Minimum number of couriers required: " + numCouriers);
			outputs.add("");
			outputs.add("Simulation with " + numCouriers + " couriers:");
			outputs.add("");

			// Loop until all customers have been served
			while (!customers.isEmpty()) {
				// Iterate through the couriers
				for (Courier courier : couriers) {
					// If the courier has finished a delivery, set them to available
					if (courier.getDeliveryTime() == currentTime) {
						courier.setAvailable(true);
					}

					// If the courier is available, find a customer for them to serve
					if (courier.isAvailable()) {


						// Find the first customer who has arrived at the store (if any)
						Customer c = null;
						for (int i = 0; i < customers.size(); i++) {
							if (customers.getHeap()[i].arrivalTime <= currentTime) {
								// If the customer has arrived, remove them from the priority queue and store them in the 'c' variable
								c = customers.pollByTime(currentTime);
								break;
							}
						}

						// Set the courier to not available and update their delivery time
						courier.setAvailable(false);
						courier.setDeliveryTime(courier.getDeliveryTime() + c.serviceTime);

						// Create a string for the output of this delivery
						String output = "Courier " + courier.getId() + " takes customer " + c.id + " at minute "
								+ currentTime + " (wait: " + (currentTime - c.arrivalTime) + " mins)";

						// Add the output string to the list of outputs
						outputs.add(output);

						// Add the customer's wait time to the total wait time
						totalWait += (currentTime - c.arrivalTime);

					}
				}
				// Increment the current time
				currentTime++;
			}

			// Re-add the customer data from the file to the priority queue
			readDataAndAddToQueue("sampleinput1.txt", customers);

			// Increment the number of couriers for the next simulation
			numCouriers++;

			// Reset the current time for the next simulation
			currentTime = 1;

			// Repeat the simulation loop until the average waiting time is below the given
			// maximum
		} while (totalWait / customers.size() > averageWaitTime);

		// Print all the outputs for the simulation
		for (String string : outputs) {
			System.out.println(string);
		}
		System.out.println();

		// Calculate the average waiting time for this simulation
		double avgWait = (float) (totalWait - 1) / customers.size();

		// Format the average waiting time to 5 decimal places
		avgWait = Double.parseDouble(new DecimalFormat("##.#####").format(avgWait));

		// Print the average waiting time with the appropriate formatting
		if (avgWait % 1 == 0) {
			System.out.println("Average waiting time: " + (int) avgWait + " minutes");
		} else {
			System.out.println("Average waiting time: " + avgWait + " minutes");
		}

	}

	// This method reads customer data from a file and adds it to the given priority queue.
	public static void readDataAndAddToQueue(String fileName, HeapPriorityQueue<Customer> queue) {
		// Try with resources block to automatically close the BufferedReader when done
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			// Read the first line of the file, which should contain the number of customers
			int numCustomers = Integer.parseInt(reader.readLine().trim());

			// Loop through the rest of the lines in the file (each representing a customer)
			for (int i = 0; i < numCustomers; i++) {
				// Split the line by white space and store the resulting tokens in an array
				String[] tokens = reader.readLine().split("\\s+");

				// Parse the relevant data from the tokens array
				int id = Integer.parseInt(tokens[0]);
				int regYear = Integer.parseInt(tokens[1]);
				int arrivalTime = Integer.parseInt(tokens[2]);
				int serviceTime = Integer.parseInt(tokens[3]);

				// Create a new Customer object with the parsed data
				Customer customer = new Customer(id, regYear, arrivalTime, serviceTime);

				// Insert the Customer object into the priority queue
				queue.insert(customer);
			}
		} catch (IOException e) {
			// Print the stack trace if an IOException occurs
			e.printStackTrace();
		}
	}

}
