package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class TicketBookingSystem implements EventServiceProvider, BookingServiceProvider {

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public Event createEvent(String type, String name, String date, String time,
                             int seats, double price, Venue venue, String p1, String p2) {
        Event event = null;

        switch (type.toLowerCase()) {
            case "movie":
                event = new Movie(name, date, time, venue, seats, price, p1, p2);
                break;
            case "concert":
                event = new Concert(name, date, time, venue, seats, price, p1, p2);
                break;
            case "sports":
                event = new Sports(name, date, time, venue, seats, price, p1, p2);
                break;
        }

        if (event != null) {
            events.add(event);
            BookingDAO.insertEvent(event); // ✅ Store in MySQL
            System.out.println("✅ Event created and saved to database.");
        }

        return event;
    }

    @Override
    public void bookTickets(Event event, int numTickets, Customer[] customers) {
        if (event.getAvailableSeats() >= numTickets) {
            Booking booking = new Booking(customers, event, numTickets);
            bookings.add(booking);

            // JDBC: Insert booking and customers
            int bookingId = BookingDAO.insertBooking(event);
            for (Customer c : customers) {
                int customerId = BookingDAO.insertCustomer(c);
                BookingDAO.insertBookingCustomer(bookingId, customerId);
            }

            event.bookTickets(numTickets);
            System.out.println("✅ Booking successful and stored in DB.");
        } else {
            System.out.println("❌ Not enough seats available.");
        }
    }

    @Override
    public void cancelBooking(int bookingId) {
        // Optional: JDBC delete logic can go here
        System.out.println("🧹 Booking cancellation via DB is not implemented yet.");
    }

    @Override
    public void listEvents() {
        List<Event> dbEvents = BookingDAO.getAllEvents();

        if (dbEvents.isEmpty()) {
            System.out.println("No events found in the database.");
            return;
        }

        for (Event e : dbEvents) {
            e.displayEventDetails();
            System.out.println("---------------");
        }
    }
}
