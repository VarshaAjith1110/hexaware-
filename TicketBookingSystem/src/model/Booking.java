package model;

import java.time.LocalDateTime;

public class Booking {
    private static int bookingCounter = 0;

    private int bookingId;
    private Customer[] customers;
    private Event event;
    private int numTickets;
    private double totalCost;
    private LocalDateTime bookingDate;

    public Booking(Customer[] customers, Event event, int numTickets) {
        this.bookingId = ++bookingCounter;
        this.customers = customers;
        this.event = event;
        this.numTickets = numTickets;
        this.totalCost = numTickets * event.getTicketPrice();
        this.bookingDate = LocalDateTime.now();

        // ✅ This will now work because it's defined in Event.java
        event.bookTickets(numTickets);
    }

    public int getBookingId() {
        return bookingId;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public Event getEvent() {
        return event;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void displayBookingDetails() {
        System.out.println("📄 Booking ID: " + bookingId);
        event.displayEventDetails();
        System.out.println("Number of Tickets: " + numTickets + ", Total Cost: ₹" + totalCost);
        System.out.println("Booking Date: " + bookingDate);
        for (Customer c : customers) {
            c.displayCustomerDetails();
        }
    }
}
