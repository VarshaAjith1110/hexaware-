package service;

import model.Event;
import model.Venue;


public interface EventServiceProvider {
 Event createEvent(String type, String name, String date, String time,
                   int seats, double price, Venue venue, String p1, String p2);
 void listEvents();
}
