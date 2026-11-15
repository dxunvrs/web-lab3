package lab.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
