package main;

import model.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketBookingSystem system = new TicketBookingSystem();

        while (true) {
            System.out.println("\n🎟️ Ticket Booking System (DB Connected)");
            System.out.println("1. Create Event");
            System.out.println("2. Book Tickets");
            System.out.println("3. View Events");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        System.out.print("Enter Event Type (Movie/Concert/Sports): ");
                        String type = sc.nextLine();
                        System.out.print("Event Name: ");
                        String name = sc.nextLine();
                        System.out.print("Event Date (YYYY-MM-DD): ");
                        String date = sc.nextLine();
                        System.out.print("Event Time (HH:MM:SS): ");
                        String time = sc.nextLine();
                        System.out.print("Venue Name: ");
                        String venueName = sc.nextLine();
                        System.out.print("Venue Address: ");
                        String venueAddress = sc.nextLine();
                        System.out.print("Total Seats: ");
                        int seats = sc.nextInt();
                        System.out.print("Ticket Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine(); // clear buffer
                        System.out.print("Extra Info 1: ");
                        String p1 = sc.nextLine();
                        System.out.print("Extra Info 2: ");
                        String p2 = sc.nextLine();

                        Venue venue = new Venue(venueName, venueAddress);
                        system.createEvent(type, name, date, time, seats, price, venue, p1, p2);
                        break;

                    case 2:
                        List<Event> eventList = BookingDAO.getAllEvents();
                        if (eventList.isEmpty()) {
                            System.out.println("❌ No events available to book.");
                            break;
                        }

                        System.out.println("📋 Select Event to Book:");
                        for (int i = 0; i < eventList.size(); i++) {
                            System.out.println((i + 1) + ". " + eventList.get(i).getEventName());
                        }
                        System.out.print("Enter event number: ");
                        int eventChoice = sc.nextInt();
                        sc.nextLine(); // clear buffer

                        if (eventChoice < 1 || eventChoice > eventList.size()) {
                            System.out.println("❌ Invalid event choice.");
                            break;
                        }

                        Event selectedEvent = eventList.get(eventChoice - 1);

                        System.out.print("Number of tickets: ");
                        int num = sc.nextInt();
                        sc.nextLine();

                        if (selectedEvent.getAvailableSeats() < num) {
                            System.out.println("❌ Not enough seats available.");
                            break;
                        }

                        Customer[] customers = new Customer[num];
                        for (int i = 0; i < num; i++) {
                            System.out.println("Enter details for Customer " + (i + 1));
                            System.out.print("Name: ");
                            String cname = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            System.out.print("Phone: ");
                            String phone = sc.nextLine();
                            customers[i] = new Customer(cname, email, phone);
                        }

                        system.bookTickets(selectedEvent, num, customers);
                        break;

                    case 3:
                        system.listEvents();
                        break;

                    case 4:
                        System.out.println("👋 Exiting... Thank you!");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("⚠️ Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
                sc.nextLine(); // clear buffer
            }
        }
    }
}
