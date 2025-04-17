// File: main/Main.java

package main;

import com.hexaware.myexceptions.IncidentNumberNotFoundException; // Import the custom exception
import com.hexaware.myexceptions.IncidentService; // Import the IncidentService class

public class Main {
    public static void main(String[] args) {
        // Create an instance of the IncidentService class
        IncidentService service = new IncidentService();

        try {
            // Simulate user entering an invalid incident number
            int incidentNumber = 12345; // Change this to simulate different scenarios
            service.findIncidentByNumber(incidentNumber); // Call method that may throw the exception
        } catch (IncidentNumberNotFoundException e) {
            // Catch the custom exception and print the error message
            System.out.println("Error: " + e.getMessage());
        }
    }
}
