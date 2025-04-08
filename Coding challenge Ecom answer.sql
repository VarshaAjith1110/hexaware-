create schema ECOME
use ECOME

#CREATING PRODUCT TABLE
CREATE TABLE product (
    product_id INT PRIMARY KEY,
    name VARCHAR(100),
    Description varchar(500),
    
    price DECIMAL(10, 2),
    stockQuantity INT
);
INSERT INTO product VALUES
(1, 'Laptop', 'High-performance laptop', 800, 10),
(2, 'smarthone', 'Latest smartphone', 600, 10),
(3, 'Tablet', 'Portable tablet', 300, 20),

(4, 'Headphones', 'Noise-canceling', 150, 30),

(5, 'TV', '4K Smart TV', 900, 5),
(6, 'Coffee Maker', 'Automatic coffee maker', 50, 25),
(7, 'Refrigirator' , 'Energy-efficient', 700, 10),
(8, 'Microwave Oven', 'Countertop microwave', 80, 15),
(9, 'Blender', 'High-speed blender', 70, 20),
(10, 'Vacuum cleaner','Bagless vacuum cleaner', 120, 10);
select * from product

#CREATING CUSTOMERS TABLE
CREATE TABLE customers (
    customerID INT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100),
    address VARCHAR(255)
);

INSERT INTO customers (customerID, firstName, lastName, email, address) VALUES
(1, 'John', 'Doe', 'johndoe@example.com', '123 Main St, City'),
(2, 'Jane', 'Smith', 'janesmith@example.com', '456 Elm St, Town'),
(3, 'Robert', 'Johnson', 'robert@example.com', '789 Oak St, Village'),

(4, 'Sarah', 'Brown',  'sarah@example.com', '101 Pine St, Suburb'),
(5, 'David', 'Lee', 'david@example.com', '234 Cedar St, District'),
(6, 'Laura', 'Hall', 'laura@example.com', '567 Birch St, County'),
(7, 'Michael', 'Davis', 'michael@example.com', '890 Maple St, State'),
(8, 'Emma', 'Wilson', 'emma@example.com', '321 Redwood St, Country'),
(9, 'William', 'Taylor', 'william@example.com', '432 Spruce St, Province'),
(10, 'Olivia', 'Adams',  'olivia@example.com', '765 Fir St, Territory');
select * from customers

#CREATING ORDERS TABLE
CREATE TABLE orders (
    orderID INT PRIMARY KEY,
    customerID INT,
    orderDate DATE,
    totalAmount DECIMAL(10,2),
    FOREIGN KEY (customerID) REFERENCES customers(customerID)
);
INSERT INTO orders (orderID, customerID, orderDate, totalAmount) VALUES
(1, 1, '2023-01-05', 1200.00),
(2, 2, '2023-02-10', 900.00),
(3, 3, '2023-03-15', 300.00),
(4, 4, '2023-04-20', 150.00),

(5, 5, '2023-05-25', 1800.00),
(6, 6, '2023-06-30', 400.00),
(7, 7, '2023-07-05', 700.00),

(8, 8, '2023-08-10', 160.00),
(9, 9, '2023-09-15', 140.00),
(10, 10, '2023-10-20', 1400.00);

select * from orders

#CREATING ORDER ITEM TABLE
CREATE TABLE OrderItem (
    orderItemID INT PRIMARY KEY,
    orderID INT REFERENCES Orders(orderID),
    productID INT REFERENCES Product(productID),
    quantity INT,
    itemAmount DECIMAL(10,2)
);

INSERT INTO OrderItem (orderItemID, orderID, productID, quantity, itemAmount) VALUES
(1, 1, 1, 2, 1600.00),
(2, 1, 3, 1, 300.00),
(3, 2, 2, 3, 1800.00),
(4, 3, 5, 2, 1800.00),
(5, 4, 4, 4, 600.00),
(6, 4, 6, 1, 50.00),
(7, 5, 1, 1, 800.00),
(8, 5, 2, 2, 1200.00),
(9, 6, 10, 2, 240.00),
(10, 6, 9, 3, 210.00);

#CREATING CART TABLE
CREATE TABLE Cart (
    cartID INT PRIMARY KEY,
    customerID INT REFERENCES Customers(customerID),
    productID INT REFERENCES Product(productID),
    quantity INT
);

INSERT INTO Cart (cartID, customerID, productID, quantity) VALUES
(1, 1, 1, 2),
(2, 1, 3, 1),
(3, 2, 2, 3),
(4, 3, 4, 4),
(5, 3, 5, 2),
(6, 4, 6, 1),
(7, 5, 1, 1),
(8, 6, 10, 2),
(9, 6, 9, 3),
(10, 7, 7, 2);
select * from Cart
SET SQL_SAFE_UPDATES = 0;

#(1)Update refrigerator product price to 800
update product set price = 800 where name='Refrigirator'

#(2) Remove all cart items for a specific customer
delete from cart where customerID = 4

#(3)Retrieve Products Priced Below $100
select * from product where price < 100

#(4)Find Products with Stock Quantity Greater Than 5.
select * from product where  stockQuantity > 5

#(5)Retrieve Orders with Total Amount Between $500 and $1000
select * from orders where totalAmount between 500 and 1000

#(6)Find Products which name end with letter ‘r’
select * from product where name like '%r'

#(7) Retrieve Cart Items for Customer 5
select * from cart where customerID = 5
(or)
select name , product_id from product as p
inner join cart as c
on p.product_id = c.productID
where c.customerID = 5

#(8)Find Customers Who Placed Orders in 2023
select * from customers as cust
inner join orders as od
on cust.customerID = od.customerID
where year(od.orderDate)=2023

#(9) Determine the Minimum Stock Quantity for Each Product Category.
select product_id,name,min(stockQuantity) from product group by product_id

#(10)Calculate the Total Amount Spent by Each Customer
select customerID, sum(totalAmount) from orders group by customerID
(or)
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, sum(ord.totalAmount) as total from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
group by cust.customerID

#(11)  Find the Average Order Amount for Each Customer
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, avg(ord.totalAmount) as avg_amount from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
group by cust.customerID

#(12)Count the Number of Orders Placed by Each Customer
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, count(ord.orderID) as No_of_orders from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
group by cust.customerID

#(13)Find the Maximum Order Amount for Each Customer
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, MAX(ord.totalAmount) as MAXIMUM_ORDER_AMOUNT from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
group by cust.customerID

#(14)Get Customers Who Placed Orders Totaling Over $1000
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, ord.totalAmount from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
where ord.totalAmount > 1000

#(15) Subquery to Find Products Not in the Cart
select * from product where product_id not in ( select productID from cart)

#(16) Subquery to Find Customers Who Haven't Placed Orders
select * from customers where customerID not in (select customerID from orders)

#(17)  Subquery to Calculate the Percentage of Total Revenue for a Product
select p.name, (sum(ord.itemAmount)*100 / (select sum(itemAmount) from orderitem)) as Percentage_of_TotalRevenue from product as p
inner join orderitem as ord
on p.product_id= ord.productID
group by p.name

 
#(18)Subquery to Find Products with Low Stock 
select * from product where stockQuantity < ( select avg(stockQuantity) from product)

#(19)Subquery to Find Customers Who Placed High-Value Orders
select cust.customerID, concat(cust.firstName,cust.lastName) as Name, ord.totalAmount from customers as cust
inner join orders as ord
on cust.customerID = ord.customerID
where ord.totalAmount > (select avg(totalAmount) from orders)







