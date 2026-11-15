package lab.model;

import java.math.BigDecimal;

public class Checker {
    private static final BigDecimal HALF = new BigDecimal("0.5");

    public boolean checkHit(Point point) {
        BigDecimal x = point.x();
        BigDecimal y = point.y();
        BigDecimal r = point.r();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal halfR = r.multiply(HALF);
        BigDecimal halfX = x.multiply(HALF);
        BigDecimal squareX = x.pow(2);
        BigDecimal squareY = y.pow(2);
        BigDecimal squareR = r.pow(2);

        if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0) {
            return squareX.add(squareY).compareTo(squareR) <= 0;
        }
        if (x.compareTo(zero) <= 0 && y.compareTo(zero) <= 0) {
            return x.compareTo(halfR.negate()) >= 0 && y.compareTo(r.negate()) >= 0;
        }
        if (x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0) {
            return x.compareTo(r) <= 0 && y.compareTo(halfX.subtract(halfR)) >= 0;
        }
        return false;
    }
}
