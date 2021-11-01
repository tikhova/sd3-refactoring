package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
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
        ProductDatabase database = new ProductDatabase("jdbc:sqlite:test.db");
        String command = request.getParameter("command");

        switch(command) {
            case "max": {
                try {
                    String rsText = database.getMaxPriceProduct();
                    String heading = HTMLFormatter.formatHeading("Product with max price: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                break;
            }
            case "min": {
                try {
                    String rsText = database.getMinPriceProduct();
                    String heading = HTMLFormatter.formatHeading("Product with min price: ");
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                break;
            }
            case "sum": {
                try {
                    String rsText = database.getPriceSum() + "\r\n";
                    String heading = "Summary price: \r\n";
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                break;
            }
            case "count": {
                try {
                    String rsText = database.getCount() + "\r\n";
                    String heading = "Number of products: \r\n";
                    String wrappedResponse = HTMLFormatter.wrapText(heading + rsText);
                    response.getWriter().println(wrappedResponse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                break;
            }
            default: {
                response.getWriter().println("Unknown command: " + command);
            }
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
