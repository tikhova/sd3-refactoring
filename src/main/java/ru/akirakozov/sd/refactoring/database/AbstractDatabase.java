package ru.akirakozov.sd.refactoring.database;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDatabase {
    private static String URL;

    public AbstractDatabase(String url) {
        URL = url;
    }

    protected abstract String getName();
    protected abstract List<String> getFieldDescriptions();

    protected void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            try (Statement stmt = c.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }

    protected ResultSet executeQuery(String sql) throws SQLException {
        Connection c = DriverManager.getConnection(URL);
        Statement stmt = c.createStatement();

        return stmt.executeQuery(sql);
    }

    public void createTableIfNotExists() throws SQLException {
        String fields = String.join(", ", getFieldDescriptions());
        executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (%s)", getName(), fields));
    }

    public void clearTable() throws SQLException {
        executeUpdate(String.format("DELETE FROM %s", getName()));
    }

    protected void insert(List<String> fieldNames, Object... fieldValues) throws SQLException {
        if (fieldNames.size() != fieldValues.length) {
            throw new IllegalArgumentException("Field names count must be equal to field valuesString count");
        }

        String valuesString = Arrays.stream(fieldValues)
                .map(x -> "\"" + x.toString() + "\"")
                .collect(Collectors.joining(", "));
        String query = "INSERT INTO " + getName() +
                " (" + String.join(", ", fieldNames) + ") " +
                "VALUES (" + valuesString + ")";
        executeUpdate(query);
    }

    protected ResultSet select(String expr, String params) throws SQLException {
        return executeQuery(String.format("SELECT %s FROM %s %s", expr, getName(), params));
    }
}