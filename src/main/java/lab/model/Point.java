package lab.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Point implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;

    public Point() {
    }

    public Point(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }
}
