package service;

import model.Event;

public abstract class BookingSystem {
    public abstract Event createEvent(String type, String name, String date, String time,
                                      int seats, double price, String venue,
                                      String param1, String param2);

    public abstract void displayEventDetails(Event event);

    public abstract double bookTickets(Event event, int numTickets);

    public abstract void cancelTickets(Event event, int numTickets);
}
