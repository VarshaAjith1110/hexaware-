package model;
import model.Venue;

public class Sports extends Event {
    private String sportName;
    private String teams;

    public Sports(String eventName, String eventDate, String eventTime, Venue venue,
                  int totalSeats, double ticketPrice, String sportName, String teams) {
        super(eventName, eventDate, eventTime, venue, totalSeats, ticketPrice, "Sports");
        this.sportName = sportName;
        this.teams = teams;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("🏆 Sports Event: " + eventName + " | Sport: " + sportName + " | Teams: " + teams);
        System.out.println("Date: " + eventDate + ", Time: " + eventTime);
        venue.displayVenueDetails();
        System.out.println("Seats: " + availableSeats + "/" + totalSeats + ", Price: ₹" + ticketPrice);
    }
}
