package service;

import java.util.ArrayList;
import java.util.Scanner;

import model.Booking;
import model.Concert;
import model.Customer;
import model.Event;
import model.Movie;
import model.Sports;
import model.Venue;

public class TicketBookingSystem {
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

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
            default:
                System.out.println("❌ Invalid event type.");
        }

        if (event != null) {
            events.add(event);
            System.out.println("✅ Event created and stored.");
        }

        return event;
    }

    public void bookTickets(Event event, int numTickets, Customer[] customers) {
        if (event.getAvailableSeats() >= numTickets) {
            Booking booking = new Booking(customers, event, numTickets);
            bookings.add(booking);
            System.out.println("✅ Booking successful! Booking ID: " + booking.getBookingId());
        } else {
            System.out.println("❌ Not enough seats.");
        }
    }

    public void cancelBooking(int bookingId) {
        Booking toCancel = null;
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) {
                toCancel = b;
                break;
            }
        }

        if (toCancel != null) {
            toCancel.getEvent().cancelBooking(toCancel.getNumTickets());
            bookings.remove(toCancel);
            System.out.println("🗑️ Booking cancelled.");
        } else {
            System.out.println("❌ Booking ID not found.");
        }
    }

    public void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        for (Event e : events) {
            e.displayEventDetails();
            System.out.println("---------------");
        }
    }
}
