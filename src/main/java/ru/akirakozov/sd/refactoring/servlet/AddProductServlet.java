package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDatabase database = new ProductDatabase("jdbc:sqlite:test.db");
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        Product product = new Product(name, String.valueOf(price));

        try {
            database.addProductToTable(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sendSuccessfulResponse(response);
        response.getWriter().println("OK");
    }
}
