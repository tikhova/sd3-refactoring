package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class TestHelper {
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;

    protected final StringWriter stringWriter = new StringWriter();
    protected final ProductDatabase database = new ProductDatabase("jdbc:sqlite:test.db");

    @Before
    public void setUp() throws IOException, SQLException {
        MockitoAnnotations.openMocks(this);
        database.createTableIfNotExists();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    @After
    public void clearTable() throws SQLException {
        database.clearTable();
    }


}