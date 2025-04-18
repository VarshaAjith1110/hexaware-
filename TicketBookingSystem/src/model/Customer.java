package model;

public class Customer {
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // ✅ Getter methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // ✅ Display method (to fix your error)
    public void displayCustomerDetails() {
        System.out.println("👤 Customer: " + name + ", Email: " + email + ", Phone: " + phone);
    }
}
