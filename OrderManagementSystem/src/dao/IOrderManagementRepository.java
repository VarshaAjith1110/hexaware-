package dao;

import entity.User;
import entity.Product;
import exception.UserNotFoundException;
import exception.OrderNotFoundException;
import java.util.List;


public interface IOrderManagementRepository {
    
    // 🔹 1. Create order
    void createOrder(User user, List<Product> products) throws Exception, UserNotFoundException, OrderNotFoundException;

    // 🔹 2. Cancel order
    void cancelOrder(int userId, int orderId) throws Exception, OrderNotFoundException;

    // 🔹 3. Create a product (admin only)
    void createProduct(User user, Product product) throws Exception, UserNotFoundException;

    // 🔹 4. Create a user
    void createUser(User user) throws Exception, UserNotFoundException;

    // 🔹 5. Get all products
    List<Product> getAllProducts() throws Exception;

    // 🔹 6. Get order details by user
    List<Product> getOrderByUser(User user) throws Exception, UserNotFoundException;
}
