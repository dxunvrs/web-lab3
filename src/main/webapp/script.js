const canvas = document.querySelector(".graph-canvas");
const ctx = canvas.getContext("2d");

const form = document.querySelector(".point-form");
const xInput = document.querySelector(".x-input");
const yInput = document.querySelector(".y-input");
const rInput = document.querySelector(".r-input");
const xButtons = document.querySelectorAll(".x-button");
const clearBtn = document.querySelector("#clear-btn");
const submitBtn = document.querySelector("#submit-btn");
const historyBody = document.querySelector(".history-body");

const xError = document.querySelector("#x-error");
const yError = document.querySelector("#y-error");
const rError = document.querySelector("#r-error");
const serverError = document.querySelector("#server-error");

const history = loadHistory();

function clearErrors() {
    xError.textContent = "";
    yError.textContent = "";
    rError.textContent = "";
    serverError.textContent = "";
}

function validateX(raw) {
    const numRegex = /^-?\d+(\.\d+)?$/;
    const xRegex =
        /^(?:0*[0-4](?:\.\d+)?|0*5(?:\.0+)?|-0*[0-2](?:\.\d+)?|-0*3(?:\.0+)?)$/;

    if (raw === "") {
        return "Выберите координату X";
    } else if (!numRegex.test(raw)) {
        return "X должен быть действительным числом";
    } else if (!xRegex.test(raw)) {
        return "Число X должно принадлежать диапазону от -3 до 5 включительно";
    }
}

function validateY(raw) {
    const yRegex = /^(?:0*[0-2](?:\.\d+)?|-0*[0-4](?:\.\d+)?)$/;
    const numRegex = /^-?\d+(\.\d+)?$/;

    if (raw === "") {
        return "Заполните поле Y";
    } else if (!numRegex.test(raw)) {
        return "Y должен быть действительным числом";
    } else if (!yRegex.test(raw)) {
        return "Число Y должно строго принадлежать интервалу от -5 до 3";
    }
}

function validateR(raw) {
    const rRegex = /^(?:0*1\.0*[1-9]\d*|0*[23](?:\.\d+)?)$/;
    const numRegex = /^-?\d+(\.\d+)?$/;

    if (raw === "") {
        return "Заполните поле R";
    } else if (!numRegex.test(raw)) {
        return "R должен быть действительным числом";
    } else if (!rRegex.test(raw)) {
        return "Число R должно строго принадлежать интервалу от 1 до 4";
    }
}

function validateFormInputs() {
    clearErrors();

    const xRaw = xInput.value.trim().replace(",", ".");
    const yRaw = yInput.value.trim().replace(",", ".");
    const rRaw = rInput.value.trim().replace(",", ".");

    const xErr = validateX(xRaw);
    const yErr = validateY(yRaw);
    const rErr = validateR(rRaw);

    if (xErr) xError.textContent = xErr;
    if (yErr) yError.textContent = yErr;
    if (rErr) rError.textContent = rErr;

    if (xErr || yErr || rErr) {
        return { isValid: false };
    }

    return {
        isValid: true,
        x: xRaw,
        y: yRaw,
        r: rRaw,
    };
}

function selectX(value) {
    xInput.value = value;

    xButtons.forEach((button) => {
        button.setAttribute("aria-pressed", String(button.dataset.x === value));
    });
}

function loadHistory() {
    return Array.from(historyBody.querySelectorAll("tr[data-x]")).map(
        (row) => ({
            x: row.dataset.x,
            y: row.dataset.y,
            r: row.dataset.r,
            hit: row.dataset.hit === "true",
        }),
    );
}

function drawCanvas() {
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = (centerX - 30) / 5;

    const rRaw = rInput.value.trim().replace(",", ".");
    const rErr = validateR(rRaw);
    const r = Number(rRaw);

    ctx.clearRect(0, 0, width, height);

    if (!rErr) {
        ctx.fillStyle = "lightsteelblue";

        // Первая четверть.
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, r * scale, -Math.PI / 2, 0);
        ctx.closePath();
        ctx.fill();

        // Третья четверть.
        ctx.beginPath();
        ctx.rect(
            centerX - (r / 2) * scale,
            centerY,
            (r / 2) * scale,
            r * scale,
        );
        ctx.fill();

        // Четвертая четверть.
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX + r * scale, centerY);
        ctx.lineTo(centerX, centerY + (r / 2) * scale);
        ctx.closePath();
        ctx.fill();
    }

    // Оси.
    ctx.strokeStyle = "black";
    ctx.fillStyle = "black";
    ctx.lineWidth = 1;

    ctx.beginPath();
    ctx.moveTo(15, centerY);
    ctx.lineTo(width - 15, centerY);
    ctx.lineTo(width - 25, centerY - 5);
    ctx.moveTo(width - 15, centerY);
    ctx.lineTo(width - 25, centerY + 5);

    ctx.moveTo(centerX, height - 15);
    ctx.lineTo(centerX, 15);
    ctx.lineTo(centerX - 5, 25);
    ctx.moveTo(centerX, 15);
    ctx.lineTo(centerX + 5, 25);
    ctx.stroke();

    ctx.font = "14px sans-serif";
    ctx.textAlign = "left";
    ctx.fillText("X", width - 25, centerY - 15);
    ctx.fillText("Y", centerX + 12, 20);

    // Деления и подписи.
    for (let value = -5; value <= 5; value++) {
        if (value === 0) {
            continue;
        }

        const x = centerX + value * scale;
        const y = centerY - value * scale;

        ctx.beginPath();
        ctx.moveTo(x, centerY - 4);
        ctx.lineTo(x, centerY + 4);
        ctx.moveTo(centerX - 4, y);
        ctx.lineTo(centerX + 4, y);
        ctx.stroke();

        ctx.textAlign = "center";
        ctx.fillText(String(value), x, centerY + 20);

        ctx.textAlign = "left";
        ctx.fillText(String(value), centerX + 10, y + 4);
    }

    ctx.fillText("0", centerX + 8, centerY + 20);

    // Результаты проверок с выбранным радиусом.
    history.forEach((point) => {
        const x = centerX + Number(point.x) * scale;
        const y = centerY - Number(point.y) * scale;

        ctx.beginPath();
        ctx.arc(x, y, 4, 0, 2 * Math.PI);
        ctx.fillStyle = point.hit ? "green" : "red";
        ctx.fill();

        ctx.strokeStyle = "white";
        ctx.stroke();
    });
}

xButtons.forEach((button) => {
    button.addEventListener("click", () => {
        selectX(button.dataset.x);
        xError.textContent = "";
    });
});

form.addEventListener("submit", (event) => {
    const data = validateFormInputs();

    if (!data.isValid) {
        event.preventDefault();
        return;
    }

    xInput.value = data.x;
    yInput.value = data.y;
    rInput.value = data.r;
});

canvas.addEventListener("click", (event) => {
    clearErrors();

    const rRaw = rInput.value.trim().replace(",", ".");
    const rErr = validateR(rRaw);

    if (rErr) {
        rError.textContent =
            "Для определения координат задайте R в интервале (1; 4)";
        rInput.focus();
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const scale = (centerX - 30) / 5;

    let mathX = (clickX - centerX) / scale;
    let mathY = (centerY - clickY) / scale;

    mathX = parseFloat(mathX.toFixed(3));
    mathY = parseFloat(mathY.toFixed(3));

    const xErr = validateX(`${mathX}`);
    const yErr = validateY(`${mathY}`);

    if (xErr) {
        xError.textContent = xErr;
    }
    if (yErr) {
        yError.textContent = yErr;
    }
    if (xErr || yErr) return;

    selectX(`${mathX}`);
    yInput.value = mathY;

    form.requestSubmit();
});

clearBtn.addEventListener("click", () => {
    const clearForm = document.createElement("form");
    clearForm.method = "post";
    clearForm.action = form.action;

    const actionInput = document.createElement("input");
    actionInput.type = "hidden";
    actionInput.name = "action";
    actionInput.value = "clear";

    clearForm.append(actionInput);
    document.body.append(clearForm);

    clearForm.submit();
});

window.addEventListener("pageshow", () => {
    const xRaw = xInput.value.trim().replace(",", ".");

    xButtons.forEach((button) => {
        button.setAttribute("aria-pressed", String(button.dataset.x === xRaw));
    });

    drawCanvas();
});

rInput.addEventListener("input", () => {
    rError.textContent = "";
    drawCanvas();
});
