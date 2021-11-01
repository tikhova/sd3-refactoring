package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.html.HTMLFormatter;
import ru.akirakozov.sd.refactoring.product.Product;

import java.sql.*;
import java.util.List;

public class ProductDatabase extends AbstractDatabase {
    public ProductDatabase(String url) {
        super(url);
    }

    @Override
    protected String getName() {
        return "PRODUCT";
    }

    @Override
    protected List<String> getFieldDescriptions() {
        return List.of("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL", "NAME TEXT NOT NULL",
                "PRICE INT NOT NULL");
    }

    public void addProductToTable(Product product) throws SQLException {
        insert(List.of("NAME", "PRICE"), product.name, product.price);
    }

    public void addProductsToTable(List<Product> products) throws SQLException {
        for (Product product : products) {
            addProductToTable(product);
        }
    }

    public String getMaxPriceProduct() throws SQLException {
        try (ResultSet rs = select("*", "ORDER BY PRICE DESC LIMIT 1")) {
            return HTMLFormatter.formatResultSet(rs);
        }
    }

    public String getMinPriceProduct() throws SQLException {
        try (ResultSet rs = select("*", "ORDER BY PRICE LIMIT 1")) {
            return HTMLFormatter.formatResultSet(rs);
        }
    }

    public String getPriceSum() throws SQLException {
        try (ResultSet rs = select("SUM(price)", "")) {
            return HTMLFormatter.formatColumn(rs, 1);
        }
    }

    public String getCount() throws SQLException {
        try (ResultSet rs = select("COUNT(*)", "")) {
            return HTMLFormatter.formatColumn(rs, 1);
        }
    }

    public String getAll() throws SQLException {
        try (ResultSet rs = select("*", "")) {
            return HTMLFormatter.formatResultSet(rs);
        }
    }
}