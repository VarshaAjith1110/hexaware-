package dao;

import java.sql.*;
import java.util.*;

import entity.*;
import util.DBConnUtil;
import exception.*;

public class OrderProcessor implements IOrderManagementRepository {

    @Override
    public void createUser(User user) throws UserNotFoundException {
        try {
            Connection conn = DBConnUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)");
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new UserNotFoundException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public void createProduct(User user, Product product) throws UserNotFoundException {
        if (!user.getRole().equalsIgnoreCase("Admin")) {
            System.out.println("Access denied: Only Admin can add products.");
            return;
        }

        try {
            Connection conn = DBConnUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO products (productId, productName, description, price, quantityInStock, type, brand, warrantyPeriod, size, color) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantityInStock());
            ps.setString(6, product.getType());

            if (product instanceof Electronics) {
                Electronics e = (Electronics) product;
                ps.setString(7, e.getBrand());
                ps.setInt(8, e.getWarrantyPeriod());
                ps.setNull(9, Types.VARCHAR);
                ps.setNull(10, Types.VARCHAR);
            } else if (product instanceof Clothing) {
                Clothing c = (Clothing) product;
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
                ps.setString(9, c.getSize());
                ps.setString(10, c.getColor());
            } else {
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.VARCHAR);
                ps.setNull(10, Types.VARCHAR);
            }

            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new UserNotFoundException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public void createOrder(User user, List<Product> products) throws UserNotFoundException, OrderNotFoundException {
        try {
            Connection conn = DBConnUtil.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement checkUser = conn.prepareStatement("SELECT * FROM users WHERE userId = ?");
            checkUser.setInt(1, user.getUserId());
            ResultSet rs = checkUser.executeQuery();
            if (!rs.next()) {
                throw new UserNotFoundException("User not found, creating new user.");
            }

            PreparedStatement orderStmt = conn.prepareStatement(
                "INSERT INTO orders (userId) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, user.getUserId());
            orderStmt.executeUpdate();

            ResultSet keys = orderStmt.getGeneratedKeys();
            keys.next();
            int orderId = keys.getInt(1);

            PreparedStatement itemStmt = conn.prepareStatement(
                "INSERT INTO order_products (orderId, productId, quantity) VALUES (?, ?, ?)");

            for (Product p : products) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, p.getProductId());
                itemStmt.setInt(3, 1);
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            throw new OrderNotFoundException("Error creating order: " + e.getMessage());
        }
    }

    @Override
    public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException {
        try {
            Connection conn = DBConnUtil.getConnection();

            PreparedStatement checkOrder = conn.prepareStatement("SELECT * FROM orders WHERE orderId = ? AND userId = ?");
            checkOrder.setInt(1, orderId);
            checkOrder.setInt(2, userId);
            ResultSet rs = checkOrder.executeQuery();

            if (!rs.next()) {
                throw new OrderNotFoundException("Order not found for this user.");
            }

            PreparedStatement deleteItems = conn.prepareStatement("DELETE FROM order_products WHERE orderId = ?");
            deleteItems.setInt(1, orderId);
            deleteItems.executeUpdate();

            PreparedStatement deleteOrder = conn.prepareStatement("DELETE FROM orders WHERE orderId = ?");
            deleteOrder.setInt(1, orderId);
            deleteOrder.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            throw new OrderNotFoundException("Error canceling order: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProducts() throws UserNotFoundException {
        try {
            Connection conn = DBConnUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");

            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                String type = rs.getString("type");
                if ("Electronics".equalsIgnoreCase(type)) {
                    list.add(new Electronics(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type,
                        rs.getString("brand"),
                        rs.getInt("warrantyPeriod")
                    ));
                } else if ("Clothing".equalsIgnoreCase(type)) {
                    list.add(new Clothing(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type,
                        rs.getString("size"),
                        rs.getString("color")
                    ));
                } else {
                    list.add(new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type
                    ));
                }
            }

            conn.close();
            return list;
        } catch (SQLException e) {
            throw new UserNotFoundException("Error fetching all products: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getOrderByUser(User user) throws UserNotFoundException {
        try {
            Connection conn = DBConnUtil.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT p.* FROM products p " +
                "JOIN order_products op ON p.productId = op.productId " +
                "JOIN orders o ON o.orderId = op.orderId " +
                "WHERE o.userId = ?"
            );
            ps.setInt(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            List<Product> list = new ArrayList<>();

            while (rs.next()) {
                String type = rs.getString("type");
                if ("Electronics".equalsIgnoreCase(type)) {
                    list.add(new Electronics(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type,
                        rs.getString("brand"),
                        rs.getInt("warrantyPeriod")
                    ));
                } else if ("Clothing".equalsIgnoreCase(type)) {
                    list.add(new Clothing(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type,
                        rs.getString("size"),
                        rs.getString("color")
                    ));
                } else {
                    list.add(new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantityInStock"),
                        type
                    ));
                }
            }

            conn.close();
            return list;
        } catch (SQLException e) {
            throw new UserNotFoundException("Error fetching orders for user: " + e.getMessage());
        }
    }
}
