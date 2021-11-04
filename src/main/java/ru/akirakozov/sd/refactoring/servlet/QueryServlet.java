package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDatabase database = new ProductDatabase("jdbc:sqlite:test.db");
        String command = request.getParameter("command");
        HTMLWriter writer = new HTMLWriter(response);

        try {
            switch (command) {
                case "max":
                    writer.printHTML("Product with max price: ", database.getMaxPriceProduct());
                    break;
                case "min":
                    writer.printHTML("Product with min price: ", database.getMinPriceProduct());
                    break;
                case "sum":
                    writer.printHTML("Summary price: \r\n" + database.getPriceSum());
                    break;
                case "count":
                    writer.printHTML("Number of products: \r\n" + database.getCount());
                    break;
                default:
                    writer.printText("Unknown command: " + command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sendSuccessfulResponse(response);
    }

}
