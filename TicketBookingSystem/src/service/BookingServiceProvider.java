package service;

import model.Customer;
import model.Event;
import model.EventException;


public interface BookingServiceProvider {
	void bookTickets(Event event, int numTickets, Customer[] customers);
	void cancelBooking(int bookingId);
}
