package model;

public class Concert extends Event {
    private String artist;
    private String type;

    public Concert(String eventName, String eventDate, String eventTime, Venue venue,
                   int totalSeats, double ticketPrice, String artist, String type) {
        super(eventName, eventDate, eventTime, venue, totalSeats, ticketPrice, "Concert");
        this.artist = artist;
        this.type = type;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("🎵 Concert: " + eventName + " | Artist: " + artist + " | Type: " + type);
        System.out.println("Date: " + eventDate + ", Time: " + eventTime);
        venue.displayVenueDetails();
        System.out.println("Seats: " + availableSeats + "/" + totalSeats + ", Price: ₹" + ticketPrice);
    }
}
