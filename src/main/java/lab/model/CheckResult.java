package lab.model;

import java.io.Serializable;

public class CheckResult implements Serializable {
    private Point point;
    private boolean hit;
    private String time;
    private double duration;

    public CheckResult() {
    }

    public CheckResult(Point point, boolean hit, String time, double duration) {
        this.point = point;
        this.hit = hit;
        this.time = time;
        this.duration = duration;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
