package lab.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getForm(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("clear".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession();

            synchronized (session) {
                session.removeAttribute("results");
            }

            getForm(request, response);
            return;
        }
        boolean hasParameters;
        hasParameters = request.getParameter("x") != null || request.getParameter("y") != null || request.getParameter("r") != null;

        if (hasParameters) {
            getServletContext().getNamedDispatcher("AreaCheckServlet").forward(request, response);
        } else {
            getForm(request, response);
        }
    }

    private void getForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
