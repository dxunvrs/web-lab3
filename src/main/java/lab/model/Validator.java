package lab.model;

import java.math.BigDecimal;

public class Validator {
    private static final BigDecimal MIN_X = new BigDecimal("-3");
    private static final BigDecimal MAX_X = new BigDecimal("5");
    private static final BigDecimal MIN_Y = new BigDecimal("-5");
    private static final BigDecimal MAX_Y = new BigDecimal("3");
    private static final BigDecimal MIN_R = new BigDecimal("1");
    private static final BigDecimal MAX_R = new BigDecimal("4");

    public Point validate(String xString, String yString, String rString) {
        if (xString == null || yString == null || rString == null) {
            throw new IllegalArgumentException("Должны быть переданы все параметры");
        }

        BigDecimal x;
        BigDecimal y;
        BigDecimal r;

        try {
            x = new BigDecimal(xString.replace(',', '.').trim());
            y = new BigDecimal(yString.replace(',', '.').trim());
            r = new BigDecimal(rString.replace(',', '.').trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Переданы некорректные параметры", e);
        }

        if (x.compareTo(MIN_X) < 0 || x.compareTo(MAX_X) > 0) {
            throw new IllegalArgumentException("Число X должно принадлежать диапазону от -3 до 5 включительно");
        }

        if (y.compareTo(MIN_Y) <= 0 || y.compareTo(MAX_Y) >= 0) {
            throw new IllegalArgumentException("Число Y должно строго принадлежать интервалу от -5 до 3");
        }

        if (r.compareTo(MIN_R) <= 0 || r.compareTo(MAX_R) >= 0) {
            throw new IllegalArgumentException("Число R должно строго принадлежать интервалу от 1 до 4");
        }

        return new Point(x, y, r);
    }
}
