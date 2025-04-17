package model;

import java.time.LocalDateTime;

public class Booking {
    private static int bookingCounter = 1000; // 🔹 static booking ID generator

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
        event.bookTickets(numTickets);
    }

    public void displayBookingDetails() {
        System.out.println("📄 Booking ID: " + bookingId);
        event.displayEventDetails();
        System.out.println("Number of Tickets: " + numTickets + ", Total Cost: ₹" + totalCost);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("👤 Customers:");
        for (Customer c : customers) {
            c.displayCustomerDetails();
        }
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public Event getEvent() {
        return event;
    }
}
