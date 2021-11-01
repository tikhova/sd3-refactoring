package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.product.Product;

import java.sql.*;
import java.util.List;

public class ProductDatabase {
    private static String URL;

    public ProductDatabase(String url) {
        URL = url;
    }

    public void executeSQL(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public void executeQuery(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        }
    }

    public void createTableIfNotExist() throws SQLException {
        executeSQL("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void addProductToTable(Product product) throws SQLException {
        executeSQL("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.name + "\"," + product.price + ")");
    }

    public void addProductsToTable(List<Product> products) throws SQLException {
        for (Product product : products) {
            addProductToTable(product);
        }
    }

    public void clearTable() throws SQLException {
        executeSQL("DELETE FROM PRODUCT");
    }
}