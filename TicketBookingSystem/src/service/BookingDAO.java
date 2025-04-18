package service;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static int insertVenue(Venue venue) {
        String sql = "INSERT INTO Venue (venue_name, address) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, venue.getVenueName());
            ps.setString(2, venue.getAddress());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void insertEvent(Event event) {
        int venueId = insertVenue(event.getVenue());

        String sql = "INSERT INTO Event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type, extra_info_1, extra_info_2) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setDate(2, java.sql.Date.valueOf(event.getEventDate()));
            ps.setTime(3, java.sql.Time.valueOf(event.getEventTime()));
            ps.setInt(4, venueId);
            ps.setInt(5, event.getTotalSeats());
            ps.setInt(6, event.getAvailableSeats());
            ps.setDouble(7, event.getTicketPrice());
            ps.setString(8, event.getEventType());
            ps.setString(9, event.getExtraInfo1());
            ps.setString(10, event.getExtraInfo2());

            ps.executeUpdate();
            System.out.println("📦 Event inserted into MySQL.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertBooking(Event event) {
        String sql = "INSERT INTO Booking (event_id) VALUES (?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, event.getEventId()); // ✅ MUST be set before calling this
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void insertBookingCustomer(int bookingId, int customerId) {
        String sql = "INSERT INTO BookingCustomer (booking_id, customer_id) VALUES (?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, customerId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, v.venue_name, v.address FROM Event e JOIN Venue v ON e.venue_id = v.venue_id";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("event_type");
                String name = rs.getString("event_name");
                String date = rs.getDate("event_date").toString();
                String time = rs.getTime("event_time").toString();
                int totalSeats = rs.getInt("total_seats");
                double price = rs.getDouble("ticket_price");
                int availableSeats = rs.getInt("available_seats");

                String extra1 = rs.getString("extra_info_1");
                String extra2 = rs.getString("extra_info_2");

                Venue venue = new Venue(rs.getString("venue_name"), rs.getString("address"));

                Event event = null;
                switch (type.toLowerCase()) {
                    case "movie":
                        event = new Movie(name, date, time, venue, totalSeats, price, extra1, extra2);
                        break;
                    case "concert":
                        event = new Concert(name, date, time, venue, totalSeats, price, extra1, extra2);
                        break;
                    case "sports":
                        event = new Sports(name, date, time, venue, totalSeats, price, extra1, extra2);
                        break;
                }

                if (event != null) {
                    event.setAvailableSeats(availableSeats);
                    event.setEventId(rs.getInt("event_id")); // ✅ THIS FIXES THE CONSTRAINT ERROR
                    events.add(event);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
}
