<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8" />
        <title>Лабораторная работа 3</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
    </head>
    <body>
        <header class="header">
            <div class="header-info">
                <span>Шулай Роман Юрьевич</span>
                <span>Группа: P3215</span>
                <span>Вариант: 12854</span>
            </div>
        </header>

        <main class="content">
            <section class="canvas-section">
                <div class="canvas-container">
                    <canvas
                        class="graph-canvas"
                        width="600"
                        height="600"
                    ></canvas>
                </div>
            </section>

            <section class="form-section">
                <form class="point-form" method="post" action="${pageContext.request.contextPath}/controller">
                    <div class="form-group">
                        <label class="group-title">
                            Координата по X:
                        </label>
                        <input type="hidden" class="x-input" name="x">
                        <div class="x-buttons">
                            <c:forEach var="x" items="-3,-2,-1,0,1,2,3,4,5">
                                <button type="button" class="x-button" data-x="${x}">
                                    ${x}
                                </button>
                            </c:forEach>
                        </div>
                        <span class="error-msg" id="x-error"></span>
                    </div>

                    <div class="form-group">
                        <label class="group-title">
                            Координата по Y: (от -5 до 3)
                        </label>
                        <input type="text" class="y-input" name="y" />
                        <span class="error-msg" id="y-error"></span>
                    </div>

                    <div class="form-group">
                        <label class="group-title">
                            Радиус R: (от 1 до 4)
                        </label>
                        <input type="text" class="r-input" name="r" value="{sessionScope.lastR}" />
                        <span class="error-msg" id="r-error"></span>
                    </div>

                    <div class="buttons">
                        <button type="submit" id="submit-btn">Проверить</button>
                        <button type="button" id="clear-btn">
                            Очистить историю
                        </button>
                    </div>
                    <span class="error-msg" id="server-error"></span>
                </form>
            </section>
        </main>
        <section class="table-section">
            <div class="table-container">
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
                    <tbody class="history-body">
                        <c:forEach var="result" items="${sessionScope.results}">
                            <tr
                                data-x="${result.point.x}"
                                data-y="${result.point.y}"
                                data-r="${result.point.r}"
                                data-hit="${result.hit}">

                                <td>
                                    ${result.point.x}
                                </td>
                                <td>
                                    ${result.point.y}
                                </td>
                                <td>
                                    ${result.point.r}
                                </td>
                                <td>
                                    ${result.hit ? "Попадание" : "Промах"}
                                </td>
                                <td>
                                    ${result.time}
                                </td>
                                <td>
                                    ${result.duration}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

        <script src="${pageContext.request.contextPath}/script.js"></script>
    </body>
</html>
