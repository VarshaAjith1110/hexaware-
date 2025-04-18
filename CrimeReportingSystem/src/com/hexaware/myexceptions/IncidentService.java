

package com.hexaware.myexceptions;

public class IncidentService {

    // Method that throws IncidentNumberNotFoundException if the incident is not found
    public void findIncidentByNumber(int incidentNumber) throws IncidentNumberNotFoundException {
        boolean incidentExists = false; // Assume incident does not exist

        if (!incidentExists) {
            // Throw the custom exception if incident is not found
            throw new IncidentNumberNotFoundException("Incident number " + incidentNumber + " not found.");
        }

        // If incident exists, print a message (not implemented here)
        System.out.println("Incident number " + incidentNumber + " found.");
    }
}
