package com.hexaware.myexceptions;

public class IncidentNumberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public IncidentNumberNotFoundException(String message) {
        super(message);
    }
}
