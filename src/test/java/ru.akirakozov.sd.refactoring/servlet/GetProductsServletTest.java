package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.product.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProductsServletTest extends TestHelper {
    private final GetProductsServlet servlet = new GetProductsServlet();

    private String getHTMLRepresentation(List<Product> products) {
        StringBuilder res = new StringBuilder();
        res.append("<html><body>\r\n");

        for (Product product : products) {
            res.append(product.name).append("\t").append(product.price).append("</br>\r\n");
        }

        res.append("</body></html>\r\n");
        return res.toString();
    }

    @Test
    public void EmptyTable() throws IOException {
        servlet.doGet(request, response);
        Assert.assertEquals(getHTMLRepresentation(new ArrayList<>()), stringWriter.toString());
    }

    @Test
    public void NonEmptyTable() throws SQLException, IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product1", "100"));
        products.add(new Product("Product2", "200"));

        database.addProductsToTable(products);
        servlet.doGet(request, response);

        Assert.assertEquals(getHTMLRepresentation(products), stringWriter.toString());
    }
}