package service;

import model.Customer;
import model.Event;
import model.EventException;
import model.BookingIDException;

public interface BookingServiceProvider {
    void bookTickets(Event event, int numTickets, Customer[] customers) throws EventException;
    void cancelBooking(int bookingId) throws BookingIDException; 
}
