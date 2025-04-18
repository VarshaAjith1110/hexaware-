-- Create the database
CREATE DATABASE TicketBookingSystem;
USE TicketBookingSystem;

CREATE TABLE Venue (
    venue_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);
CREATE TABLE Event (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    event_time TIME NOT NULL,
    venue_id INT NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    ticket_price DOUBLE NOT NULL,
    event_type ENUM('Movie', 'Concert', 'Sports') NOT NULL,
    extra_info_1 VARCHAR(100),
    extra_info_2 VARCHAR(100),
    FOREIGN KEY (venue_id) REFERENCES Venue(venue_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES Event(event_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE BookingCustomer (
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    PRIMARY KEY (booking_id, customer_id),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
        ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
        ON DELETE CASCADE
);
-- Insert into Venue
INSERT INTO Venue (venue_name, address) VALUES
('Stadium Arena', 'City Center'),
('Grand Theatre', 'Main Road'),
('Expo Hall', 'Downtown'),
('Open Grounds', 'Suburb Street'),
('Jazz Club', 'Hillview'),
('Convention Center', 'Lake View'),
('City Sports Arena', 'High Street'),
('Cineplex', 'Movie Avenue'),
('Rooftop Theater', 'Skyline Road'),
('Auditorium Hall', 'Campus Circle');

-- Insert into Venue


-- Insert into Event
INSERT INTO Event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type, extra_info_1, extra_info_2)
VALUES
('Jazz Night', '2024-05-10', '19:00:00', 5, 300, 250, 1800, 'Concert', 'Jazz Band', 'Live'),
('Rock Concert', '2024-05-12', '20:00:00', 2, 1000, 850, 2200, 'Concert', 'Rockstars', 'Headliner'),
('World Cup Final', '2024-06-20', '18:00:00', 1, 20000, 18000, 3500, 'Sports', 'Cricket', 'Final'),
('Movie Premiere', '2024-04-30', '21:00:00', 8, 250, 250, 1200, 'Movie', 'Action', 'Starring A'),
('Boxing Championship', '2024-05-05', '19:30:00', 3, 5000, 4900, 1500, 'Sports', 'Boxing', 'National'),
('Drama Play', '2024-06-15', '18:30:00', 10, 300, 270, 900, 'Movie', 'Drama', 'Stage'),
('Tech Talk', '2024-06-10', '14:00:00', 6, 600, 600, 500, 'Movie', 'Tech', 'Conference'),
('Live DJ Night', '2024-05-15', '22:00:00', 9, 400, 390, 1000, 'Concert', 'DJ Ray', 'EDM'),
('Local Football Match', '2024-05-25', '16:00:00', 7, 8000, 7800, 1000, 'Sports', 'Football', 'League'),
('Cupcake Expo', '2024-06-05', '10:00:00', 4, 1000, 950, 600, 'Movie', 'Food', 'Cupcakes');

-- Insert into Customer
INSERT INTO Customer (name, email, phone) VALUES
('Alice', 'alice@gmail.com', '9876540000'),
('Bob', 'bob@gmail.com', '9876540001'),
('Charlie', 'charlie@gmail.com', '9876540002'),
('Daisy', 'daisy@gmail.com', '9876543000'),
('Ethan', 'ethan@gmail.com', '9876540003'),
('Fiona', 'fiona@gmail.com', '9876544000'),
('George', 'george@gmail.com', '9876540004'),
('Hannah', 'hannah@gmail.com', '9876540005'),
('Ivan', 'ivan@gmail.com', '9876540006'),
('Julia', 'julia@gmail.com', '9876540007');

-- Insert into Booking
INSERT INTO Booking (event_id) VALUES
(1), (1), (2), (3), (4), (5), (5), (6), (6), (7);

-- BookingCustomer for multiple customers per booking
INSERT INTO BookingCustomer (booking_id, customer_id) VALUES
(1, 1), (1, 2), (2, 3), (3, 4), (3, 5),
(4, 6), (5, 7), (6, 8), (6, 9), (6, 10),
(7, 1), (7, 3), (8, 4), (9, 5), (9, 6),
(10, 2), (10, 7);

#list all Events
SELECT * FROM Event;

#Select events with available tickets
SELECT * FROM Event
WHERE available_seats > 0;

#Select events name partial match with ‘cup’
SELECT * FROM Event
WHERE event_name LIKE '%cup%';

##Select events with ticket price range between 1000 to 2500
SELECT * FROM Event
WHERE ticket_price BETWEEN 1000 AND 2500;

## Retrieve events with dates in a specific range
SELECT * FROM Event
WHERE event_date BETWEEN '2024-05-01' AND '2024-06-01';

##Retrieve events with available tickets that also have "Concert" in their name
SELECT * FROM Event
WHERE available_seats > 0
  AND event_name LIKE '%Concert%';
  
#Retrieve users in batches of 5, starting from the 6th user
SELECT * FROM Customer
LIMIT 5 OFFSET 5;

##Retrieve booking details with more than 4 tickets booked
SELECT b.booking_id, COUNT(bc.customer_id) AS total_tickets
FROM Booking b
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY b.booking_id
HAVING COUNT(bc.customer_id) > 4;

#Retrieve customer info whose phone number ends with ‘000’
SELECT * FROM Customer
WHERE phone LIKE '%000';

#Retrieve events in order where seat capacity > 15000
SELECT * FROM Event
WHERE total_seats > 15000
ORDER BY total_seats DESC;

#Select event names not starting with ‘x’, ‘y’, ‘z’
SELECT * FROM Event
WHERE event_name NOT LIKE 'x%' AND event_name NOT LIKE 'y%' AND event_name NOT LIKE 'z%';

-- task 3

SELECT event_name, AVG(ticket_price) AS avg_price
FROM Event
GROUP BY event_name;

#Calculate the Total Revenue Generated by Events
SELECT e.event_name, 
       COUNT(bc.customer_id) * e.ticket_price AS total_revenue
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY e.event_id;

## Find the Event with the Highest Ticket Sales
SELECT e.event_name, COUNT(bc.customer_id) AS tickets_sold
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY e.event_id
ORDER BY tickets_sold DESC
LIMIT 1;

#Total Number of Tickets Sold for Each Event
SELECT e.event_name, COUNT(bc.customer_id) AS total_tickets_sold
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY e.event_id;

#Find Events with No Ticket Sales

# User Who Has Booked the Most Tickets
SELECT c.name, COUNT(bc.booking_id) AS total_booked
FROM Customer c
JOIN BookingCustomer bc ON c.customer_id = bc.customer_id
GROUP BY c.customer_id
ORDER BY total_booked DESC
LIMIT 1;

# Events and Tickets Sold per Month
SELECT DATE_FORMAT(b.booking_date, '%Y-%m') AS month,
       e.event_name,
       COUNT(bc.customer_id) AS tickets_sold
FROM Booking b
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
JOIN Event e ON b.event_id = e.event_id
GROUP BY month, e.event_name
ORDER BY month;
 
#Average Ticket Price for Events in Each Venue	
SELECT v.venue_name, AVG(e.ticket_price) AS avg_price
FROM Venue v
JOIN Event e ON v.venue_id = e.venue_id
GROUP BY v.venue_id;
#Tickets Sold for Each Event Type
SELECT e.event_type, COUNT(bc.customer_id) AS total_tickets
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY e.event_type;

#Total Revenue by Events in Each Year
SELECT YEAR(b.booking_date) AS year,
       SUM(e.ticket_price) AS total_revenue
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
GROUP BY YEAR(b.booking_date);


#Users Who Have Booked Tickets for Multiple Events
SELECT c.name, COUNT(DISTINCT e.event_id) AS unique_events
FROM Customer c
JOIN BookingCustomer bc ON c.customer_id = bc.customer_id
JOIN Booking b ON bc.booking_id = b.booking_id
JOIN Event e ON b.event_id = e.event_id
GROUP BY c.customer_id
HAVING unique_events > 1;

 -- Total Revenue by Events for Each User
 SELECT c.name, SUM(e.ticket_price) AS total_spent
FROM Customer c
JOIN BookingCustomer bc ON c.customer_id = bc.customer_id
JOIN Booking b ON bc.booking_id = b.booking_id
JOIN Event e ON b.event_id = e.event_id
GROUP BY c.customer_id;

#Average Ticket Price by Event Category and Venue
SELECT e.event_type, v.venue_name, AVG(e.ticket_price) AS avg_price
FROM Event e
JOIN Venue v ON e.venue_id = v.venue_id
GROUP BY e.event_type, v.venue_id;

#Users and Total Tickets Purchased in Last 30 Days
SELECT c.name, COUNT(bc.booking_id) AS tickets_purchased
FROM Customer c
JOIN BookingCustomer bc ON c.customer_id = bc.customer_id
JOIN Booking b ON bc.booking_id = b.booking_id
WHERE b.booking_date >= CURDATE() - INTERVAL 30 DAY
GROUP BY c.customer_id;

-- task 4
#Average Ticket Price for Events in Each Venue Using a Subquery
SELECT venue_name,
       (SELECT AVG(e.ticket_price)
        FROM Event e
        WHERE e.venue_id = v.venue_id) AS avg_ticket_price
FROM Venue v;

#Events with More Than 50% of Tickets Sold (Using Subquery)
SELECT event_name
FROM Event
WHERE event_id IN (
    SELECT e.event_id
    FROM Event e
    JOIN Booking b ON e.event_id = b.event_id
    JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
    GROUP BY e.event_id
    HAVING COUNT(bc.customer_id) > e.total_seats / 2
);

#3
SELECT e.event_name,
       (SELECT COUNT(*)
        FROM Booking b
        JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
        WHERE b.event_id = e.event_id) AS total_tickets_sold
FROM Event e;

#4	Users Who Have Not Booked Any Tickets (Using NOT EXISTS)

SELECT *
FROM Customer c
WHERE NOT EXISTS (
    SELECT 1
    FROM BookingCustomer bc
    WHERE bc.customer_id = c.customer_id
);

#Events with No Ticket Sales (Using NOT IN)
SELECT event_name
FROM Event
WHERE event_id NOT IN (
    SELECT b.event_id
    FROM Booking b
    JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
);
-- 6. Calculate the Total Number of Tickets Sold for Each Event Type Using a Subquery in the FROM Clause
SELECT sub.event_type, SUM(sub.tickets_sold) AS total_tickets
FROM (
    SELECT e.event_type, COUNT(bc.customer_id) AS tickets_sold
    FROM Event e
    JOIN Booking b ON e.event_id = b.event_id
    JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
    GROUP BY e.event_id
) AS sub
GROUP BY sub.event_type;

-- 7. Find Events with Ticket Prices Higher Than the Average Ticket Price Using a Subquery in the WHERE Clause
SELECT event_name, ticket_price
FROM Event
WHERE ticket_price > (
    SELECT AVG(ticket_price)
    FROM Event
);

-- 8. Calculate the Total Revenue Generated by Events for Each User Using a Correlated Subquery
SELECT c.name,
       (SELECT SUM(e.ticket_price)
        FROM BookingCustomer bc
        JOIN Booking b ON bc.booking_id = b.booking_id
        JOIN Event e ON b.event_id = e.event_id
        WHERE bc.customer_id = c.customer_id) AS total_revenue
FROM Customer c;

-- 9. List Users Who Have Booked Tickets for Events in a Given Venue Using a Subquery in the WHERE Clause

SELECT name
FROM Customer
WHERE customer_id IN (
    SELECT bc.customer_id
    FROM BookingCustomer bc
    JOIN Booking b ON bc.booking_id = b.booking_id
    WHERE b.event_id IN (
        SELECT event_id FROM Event WHERE venue_id = 1
    )
);

-- 10. Calculate the Total Number of Tickets Sold for Each Event Category Using a Subquery with GROUP BY
SELECT event_type,
       (SELECT COUNT(bc.customer_id)
        FROM Event e2
        JOIN Booking b ON e2.event_id = b.event_id
        JOIN BookingCustomer bc ON b.booking_id = bc.booking_id
        WHERE e2.event_type = e1.event_type
       ) AS total_tickets
FROM Event e1
GROUP BY event_type;

-- 11. Find Users Who Have Booked Tickets for Events in each Month Using a Subquery with DATE_FORMAT
SELECT DISTINCT c.name
FROM Customer c
WHERE EXISTS (
    SELECT 1
    FROM BookingCustomer bc
    JOIN Booking b ON bc.booking_id = b.booking_id
    WHERE bc.customer_id = c.customer_id
      AND DATE_FORMAT(b.booking_date, '%Y-%m') IS NOT NULL
);

-- 12. Calculate the Average Ticket Price for Events in Each Venue Using a Subquery
SELECT venue_name,
       (SELECT AVG(ticket_price)
        FROM Event e
        WHERE e.venue_id = v.venue_id) AS avg_price
FROM Venue v;

