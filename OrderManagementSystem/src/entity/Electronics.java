package entity;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    // 🔹 Default constructor
    public Electronics() {}

    // 🔹 Parameterized constructor (with all product + electronics fields)
    public Electronics(int productId, String productName, String description, double price,
            int quantityInStock, String type, String brand, int warrantyPeriod) {
    	super(productId, productName, description, price, quantityInStock, type);
    	this.brand = brand;
    	this.warrantyPeriod = warrantyPeriod;
    }

    

    // 🔹 Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
