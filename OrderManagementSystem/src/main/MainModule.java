package main;

import dao.*;
import entity.*;
import java.util.*;

public class MainModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IOrderManagementRepository repo = new OrderProcessor();

        while (true) {
            System.out.println("\n--- Order Management Menu ---");
            System.out.println("1. Create User");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. Get All Products");
            System.out.println("6. Get Orders by User");
            System.out.println("7. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1: // Create user
                        System.out.print("User ID: ");
                        int userId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Username: ");
                        String uname = sc.nextLine();
                        System.out.print("Password: ");
                        String pass = sc.nextLine();
                        System.out.print("Role (Admin/User): ");
                        String role = sc.nextLine();
                        repo.createUser(new User(userId, uname, pass, role));
                        break;

                    case 2: // Create product
                        System.out.print("Admin User ID: ");
                        int aid = sc.nextInt();
                        sc.nextLine();
                        User admin = new User(aid, "", "", "Admin");

                        System.out.print("Product ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Type (Electronics/Clothing): ");
                        String type = sc.nextLine();

                        Product prod = null;
                        if (type.equalsIgnoreCase("Electronics")) {
                            System.out.print("Brand: ");
                            String brand = sc.nextLine();
                            System.out.print("Warranty: ");
                            int wp = sc.nextInt();
                            prod = new Electronics(pid, name, desc, price, qty, type, brand, wp);
                        } else {
                            System.out.print("Size: ");
                            String size = sc.nextLine();
                            System.out.print("Color: ");
                            String color = sc.nextLine();
                            prod = new Clothing(pid, name, desc, price, qty, type, size, color);
                        }
                        repo.createProduct(admin, prod);
                        break;

                    case 3: // Create order
                        System.out.print("User ID: ");
                        int ouid = sc.nextInt();
                        sc.nextLine();
                        User u = new User(ouid, "", "", "User");

                        List<Product> plist = new ArrayList<>();
                        System.out.print("How many products in this order? ");
                        int count = sc.nextInt();
                        for (int i = 0; i < count; i++) {
                            System.out.print("Product ID: ");
                            int pid2 = sc.nextInt();
                            sc.nextLine();
                            plist.add(new Product(pid2, "", "", 0, 0, ""));
                        }
                        repo.createOrder(u, plist);
                        break;

                    case 4: // Cancel order
                        System.out.print("User ID: ");
                        int cuid = sc.nextInt();
                        System.out.print("Order ID: ");
                        int oid = sc.nextInt();
                        repo.cancelOrder(cuid, oid);
                        break;

                    case 5: // Get all products
                        List<Product> all = repo.getAllProducts();
                        for (Product p : all) {
                            System.out.println(p.getProductId() + " - " + p.getProductName() + " - " + p.getType());
                        }
                        break;

                    case 6: // Get orders by user
                        System.out.print("User ID: ");
                        int uid = sc.nextInt();
                        User orderUser = new User(uid, "", "", "User");
                        List<Product> orders = repo.getOrderByUser(orderUser);
                        for (Product p : orders) {
                            System.out.println(p.getProductName() + " - " + p.getType());
                        }
                        break;

                    case 7:
                        System.out.println("Goodbye!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
