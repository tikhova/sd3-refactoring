package ru.akirakozov.sd.refactoring.html;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLWriter {
    PrintWriter writer;

    public HTMLWriter(HttpServletResponse response) throws IOException {
        writer = response.getWriter();
    }

    public void printText(String text) {
        writer.println(text);
    }

    public void printHTML(String text) {
        writer.println(HTMLFormatter.wrapText(text));
    }

    public void printHTML(String heading, String body) {
        printHTML(HTMLFormatter.formatHeading(heading) + body);
    }
}
