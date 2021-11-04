package ru.akirakozov.sd.refactoring.html;

import java.sql.*;

public class HTMLFormatter {
    public static String formatResultSet(final ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            long price = resultSet.getLong("price");
            sb.append(name).append("\t").append(price).append("</br>\r\n");
        }

        return sb.toString();
    }

    public static String formatColumn(ResultSet resultSet, int columnIndex) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt(columnIndex) + "\r\n";
        }

        return "";
    }

    public static String wrapText(String text) {
        return "<html><body>\r\n" + text + "</body></html>";
    }

    public static String formatHeading(String heading) {
        return "<h1>" + heading + "</h1>\r\n";
    }
}