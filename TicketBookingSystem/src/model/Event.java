package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Event {
    protected int eventId;
    protected String eventName;
    protected LocalDate eventDate;
    protected LocalTime eventTime;
    protected Venue venue;
    protected int totalSeats;
    protected int availableSeats;
    protected double ticketPrice;
    protected String eventType;
    protected String extra_info_1;
    protected String extra_info_2;

    public Event(String eventName, String date, String time, Venue venue,
                 int totalSeats, double ticketPrice, String eventType) {
        this.eventName = eventName;
        this.eventDate = LocalDate.parse(date);
        this.eventTime = LocalTime.parse(time);
        this.venue = venue;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.eventType = eventType;
    }

    // ✅ Getter methods
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getEventType() {
        return eventType;
    }

    public String getExtraInfo1() {
        return extra_info_1;
    }

    public String getExtraInfo2() {
        return extra_info_2;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void bookTickets(int numTickets) {
        this.availableSeats -= numTickets;
    }

    public void cancelBooking(int numTickets) {
        this.availableSeats += numTickets;
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayEventDetails();
}
