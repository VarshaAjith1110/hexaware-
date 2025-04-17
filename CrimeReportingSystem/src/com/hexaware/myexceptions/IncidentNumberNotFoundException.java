

// File: com/hexaware/myexceptions/IncidentNumberNotFoundException.java

package com.hexaware.myexceptions;

public class IncidentNumberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    // Constructor to pass the message to the Exception class
    public IncidentNumberNotFoundException(String message) {
        super(message); // Call the parent class constructor
    }
}
