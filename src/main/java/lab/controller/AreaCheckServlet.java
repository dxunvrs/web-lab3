package lab.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lab.model.CheckResult;
import lab.model.Checker;
import lab.model.Point;
import lab.model.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    private final Validator validator = new Validator();
    private final Checker checker = new Checker();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.nanoTime();

        CheckResult result = null;
        String errorMessage = null;

        try {
            Point point = validator.validate(request.getParameter("x"),
                    request.getParameter("y"),
                    request.getParameter("r")
            );

            boolean hit = checker.checkHit(point);

            result = new CheckResult(
                    point,
                    hit,
                    LocalDateTime.now().format(TIME_FORMAT),
                    (System.nanoTime() - startTime) / 1000000.0
            );

            saveResult(request.getSession(), result);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            errorMessage = e.getMessage();
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        
        writeHeader(out, contextPath);
        
        if (result != null) {
            writeResult(out, result);
        } else {
            writeError(out, errorMessage);
        }

        out.println("""
                <p><a class="back-link" href="%s/controller">Вернуться к форме</a></p>
                </main></body></html>""".formatted(contextPath));

    }

    private void saveResult(HttpSession session, CheckResult result) {
        synchronized (session) {
            List<CheckResult> prev = (List<CheckResult>) session.getAttribute("results");

            List<CheckResult> history;
            if (prev == null) {
                history = new ArrayList<>();
            } else {
                history = new ArrayList<>(prev);
            }

            history.add(0, result);
            session.setAttribute("results", history);
            session.setAttribute("lastR", result.getPoint().getR());
        }
    }
    
    private void writeHeader(PrintWriter out, String contextPath) {
        out.println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Результат проверки</title>
                    <link rel="stylesheet" href="%s/style.css">
                </head>
                <body>
                    <header class="header">
                        Результат проверки точки
                    </header>
                    <main class="result-card">
                """.formatted(contextPath));
    }

    private void writeResult(PrintWriter out, CheckResult result) {
        Point point = result.getPoint();
        String msg = result.isHit() ? "Попадание" : "Промах";

        out.println("""
                <h1>%s</h1>
                <table class="history-table">
                    <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Результат</th>
                            <th>Дата и время проверки</th>
                            <th>Время выполнения</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                        </tr>
                     </tbody>
                </table>
                """.formatted(msg,
                            point.getX().toString(),
                            point.getY().toString(),
                            point.getR().toString(),
                            msg,
                            result.getTime(),
                            String.valueOf(result.getDuration())
                ));
    }

    private void writeError(PrintWriter out, String errorMessage) {
        out.println("""
                <h1>Ошибка ввода</h1>
                <p class="error-msg">%s</p>
                """.formatted(errorMessage));
    }
}
