package main;

import model.*;
import service.*;

import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketBookingSystem system = new TicketBookingSystem();
        Event currentEvent = null;

        while (true) {
            System.out.println("\n🎟️ Ticket Booking System");
            System.out.println("1. Create Event");
            System.out.println("2. Book Tickets");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Events");
            System.out.println("5. Exit");
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
                        System.out.print("Event Time (HH:MM): ");
                        String time = sc.nextLine();
                        System.out.print("Venue Name: ");
                        String venueName = sc.nextLine();
                        System.out.print("Venue Address: ");
                        String venueAddress = sc.nextLine();
                        System.out.print("Total Seats: ");
                        int seats = sc.nextInt();
                        System.out.print("Ticket Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Extra Info 1: ");
                        String extra1 = sc.nextLine();
                        System.out.print("Extra Info 2: ");
                        String extra2 = sc.nextLine();

                        Venue venue = new Venue(venueName, venueAddress);
                        currentEvent = system.createEvent(type, name, date, time, seats, price, venue, extra1, extra2);
                        break;

                    case 2:
                        if (currentEvent == null) {
                            System.out.println("❌ No event selected to book tickets.");
                        } else {
                            Customer[] customers = getCustomers(sc);
                            system.bookTickets(currentEvent, customers.length, customers);
                        }
                        break;

                    case 3:
                        System.out.print("Enter Booking ID to cancel: ");
                        int cancelId = sc.nextInt();
                        system.cancelBooking(cancelId);
                        break;

                    case 4:
                        system.listEvents();
                        break;

                    case 5:
                        System.out.println("👋 Exiting... Thank you!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("⚠️ Invalid choice. Please enter 1–5.");
                }

            } catch (NullPointerException npe) {
                System.out.println("⚠️ Null pointer error. Please ensure all inputs are filled.");
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error: " + e.getMessage());
                sc.nextLine(); // prevent loop on bad input
            }
        }
    }

    private static Customer[] getCustomers(Scanner sc) {
        System.out.print("How many tickets to book? ");
        int count = sc.nextInt();
        sc.nextLine();
        Customer[] customers = new Customer[count];
        for (int i = 0; i < count; i++) {
            System.out.println("Customer " + (i + 1) + " details:");
            System.out.print("Name: ");
            String cname = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Phone: ");
            String phone = sc.nextLine();
            customers[i] = new Customer(cname, email, phone);
        }
        return customers;
    }
}
