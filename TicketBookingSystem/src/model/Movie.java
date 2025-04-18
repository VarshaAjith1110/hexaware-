package model;

public class Movie extends Event {
    private String genre;
    private String actor;

    public Movie(String eventName, String eventDate, String eventTime, Venue venue,
                 int totalSeats, double ticketPrice, String genre, String actor) {
        super(eventName, eventDate, eventTime, venue, totalSeats, ticketPrice, "Movie");
        this.genre = genre;
        this.actor = actor;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("🎬 Movie: " + eventName + " | Genre: " + genre + " | Actor: " + actor);
        System.out.println("Date: " + eventDate + ", Time: " + eventTime);
        venue.displayVenueDetails();
        System.out.println("Seats: " + availableSeats + "/" + totalSeats + ", Price: ₹" + ticketPrice);
    }
}
