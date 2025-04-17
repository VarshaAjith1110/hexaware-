package model;

public class Customer {
    private String customerName;
    private String email;
    private String phoneNumber;

    public Customer() {}

    public Customer(String name, String email, String phone) {
        this.customerName = name;
        this.email = email;
        this.phoneNumber = phone;
    }

    public void displayCustomerDetails() {
        System.out.println("Customer: " + customerName + " | Email: " + email + " | Phone: " + phoneNumber);
    }

    public String getCustomerName() {
        return customerName;
    }
}
