package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDatabase database = new ProductDatabase("jdbc:sqlite:test.db");
        HTMLWriter writer = new HTMLWriter(response);

        try {
            writer.printHTML(database.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sendSuccessfulResponse(response);
    }
}
