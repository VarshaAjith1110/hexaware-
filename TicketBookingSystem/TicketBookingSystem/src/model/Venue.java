package model;

public class Venue {
    private String venueName;
    private String address;

    public Venue() {}

    public Venue(String venueName, String address) {
        this.venueName = venueName;
        this.address = address;
    }

    public void displayVenueDetails() {
        System.out.println("Venue: " + venueName);
        System.out.println("Address: " + address);
    }

    public String getVenueName() {
        return venueName;
    }

    public String getAddress() {
        return address;
    }
}
