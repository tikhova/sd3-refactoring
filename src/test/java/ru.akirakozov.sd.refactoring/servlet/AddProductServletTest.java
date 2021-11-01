package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class AddProductServletTest extends TestHelper {
    private final AddProductServlet servlet = new AddProductServlet();
    private final String SUCCESSFUL_RESPONSE = "OK";

    private void addProducts(List<Product> products) throws IOException {
        for (Product product : products) {
            when(request.getParameter("name")).thenReturn(product.name);
            when(request.getParameter("price")).thenReturn(product.price);
        }
        servlet.doGet(request, response);
    }

    @Test(expected = NumberFormatException.class)
    public void NoProducts() throws IOException {
        addProducts(Collections.emptyList());
    }

    @Test
    public void OneProduct() throws IOException {
        addProducts(Collections.singletonList(new Product("Product", "100")));
        Assert.assertEquals(SUCCESSFUL_RESPONSE, stringWriter.toString().trim());
    }

    @Test
    public void ManyProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product1", "100"));
        products.add(new Product("Product2", "200"));
        addProducts(products);

        Assert.assertEquals(SUCCESSFUL_RESPONSE, stringWriter.toString().trim());
    }

    @Test(expected = NumberFormatException.class)
    public void WrongPriceFormat() throws IOException {
        addProducts(Collections.singletonList(new Product("Product", "100$")));
    }
}