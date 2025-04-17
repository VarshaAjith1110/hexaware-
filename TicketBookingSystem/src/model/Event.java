package model;

public abstract class Event {
    protected String eventName;
    protected String eventDate;
    protected String eventTime;
    protected Venue venue;
    protected int totalSeats;
    protected int availableSeats;
    protected double ticketPrice;
    protected String eventType;

    public Event(String eventName, String eventDate, String eventTime, Venue venue,
                 int totalSeats, double ticketPrice, String eventType) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.eventType = eventType;
    }

    public abstract void displayEventDetails();

    public void bookTickets(int numTickets) {
        if (availableSeats >= numTickets) {
            availableSeats -= numTickets;
            System.out.println(numTickets + " ticket(s) booked.");
        } else {
            System.out.println("Not enough seats available.");
        }
    }

    public void cancelBooking(int numTickets) {
        availableSeats += numTickets;
        System.out.println(numTickets + " ticket(s) cancelled.");
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getEventName() {
        return eventName;
    }
}
