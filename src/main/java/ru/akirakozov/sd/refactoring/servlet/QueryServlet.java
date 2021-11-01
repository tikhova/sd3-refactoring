package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HTMLFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

                    String rsText = HTMLFormatter.formatResultSet(rs);
                    String heading = HTMLFormatter.formatHeading("Product with max price: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

                    String rsText = HTMLFormatter.formatResultSet(rs);
                    String heading = HTMLFormatter.formatHeading("Product with min price: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

                    String rsText = HTMLFormatter.formatResultSet(rs);
                    String heading = HTMLFormatter.formatHeading("Summary price: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

                    String rsText = HTMLFormatter.formatColumn(rs, 1);
                    String heading = HTMLFormatter.formatHeading("Number of products: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
