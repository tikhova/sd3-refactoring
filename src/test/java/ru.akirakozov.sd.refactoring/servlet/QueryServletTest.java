package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.product.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class QueryServletTest extends TestHelper {
    private final QueryServlet servlet = new QueryServlet();

    @Test
    public void EmptyTableMax() throws IOException {
        when(request.getParameter("command")).thenReturn("max");
        servlet.doGet(request, response);
        Assert.assertEquals(buildMaxResponse("", ""), stringWriter.toString());
    }

    @Test
    public void EmptyTableMin() throws IOException {
        when(request.getParameter("command")).thenReturn("min");
        servlet.doGet(request, response);
        Assert.assertEquals(buildMinResponse("", ""), stringWriter.toString());
    }

    @Test
    public void EmptyTableSum() throws IOException {
        when(request.getParameter("command")).thenReturn("sum");
        servlet.doGet(request, response);
        Assert.assertEquals(buildSumResponse(0), stringWriter.toString());
    }

    @Test
    public void EmptyTableCount() throws IOException {
        when(request.getParameter("command")).thenReturn("count");
        servlet.doGet(request, response);
        Assert.assertEquals(buildCountResponse(0), stringWriter.toString());
    }

    @Test
    public void EmptyTableUnknownCommand() throws IOException {
        when(request.getParameter("command")).thenReturn("unknown");
        servlet.doGet(request, response);
        Assert.assertEquals(buildUnknownCommandResponse(), stringWriter.toString());
    }

    @Test
    public void Max() throws SQLException, IOException {
        when(request.getParameter("command")).thenReturn("max");
        addExamples();

        servlet.doGet(request, response);
        Assert.assertEquals(buildMaxResponse("Max price product", "200"), stringWriter.toString());
    }

    @Test
    public void Min() throws SQLException, IOException {
        when(request.getParameter("command")).thenReturn("min");
        addExamples();

        servlet.doGet(request, response);
        Assert.assertEquals(buildMinResponse("Min price product", "50"), stringWriter.toString());
    }

    @Test
    public void Sum() throws SQLException, IOException {
        when(request.getParameter("command")).thenReturn("sum");
        addExamples();

        servlet.doGet(request, response);
        Assert.assertEquals(buildSumResponse(350), stringWriter.toString());
    }

    @Test
    public void Count() throws SQLException, IOException {
        when(request.getParameter("command")).thenReturn("count");
        addExamples();

        servlet.doGet(request, response);
        Assert.assertEquals(buildCountResponse(3), stringWriter.toString());
    }

    @Test
    public void UnknownCommand() throws SQLException, IOException {
        when(request.getParameter("command")).thenReturn("unknown");
        addExamples();

        servlet.doGet(request, response);
        Assert.assertEquals(buildUnknownCommandResponse(), stringWriter.toString());
    }


    private void addExamples() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product", "100"));
        products.add(new Product("Max price product", "200"));
        products.add(new Product("Min price product", "50"));

        database.addProductsToTable(products);
    }

    private String wrapString(String s) {
        return "<html><body>\r\n" + s + "</body></html>\r\n";
    }

    private String buildMaxResponse(String name, String price) {
        return wrapString("<h1>Product with max price: </h1>\r\n" + nameAndPrice(name, price));
    }

    private String buildMinResponse(String name, String price) {
        return wrapString("<h1>Product with min price: </h1>\r\n" + nameAndPrice(name, price));
    }

    private String buildSumResponse(int price) {
        return wrapString("Summary price: \r\n" + price + "\r\n");
    }

    private String buildCountResponse(int num) {
        return wrapString("Number of products: \r\n" + num + "\r\n");
    }

    private String buildUnknownCommandResponse() {
        return "Unknown command: unknown\r\n";
    }

    private String nameAndPrice(String name, String price) {
        if (name != null && !name.isEmpty()) {
            return name + "\t" + price + "</br>\r\n";
        }

        return "";
    }
}