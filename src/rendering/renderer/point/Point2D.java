package rendering.renderer.point;

import rendering.util.Vec2D;

public class Point2D {

    private Vec2D coords;
    private double depth;
    private boolean isIntersection;

    public Point2D(Vec2D coords, double depth) {
        this.coords = coords;
        this.depth = depth;
        isIntersection = false;
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "coords=" + coords +
                ", depth=" + depth +
                ", isIntersection=" + isIntersection +
                '}';
    }

    public Point2D(double x, double y, double depth) {
        this(new Vec2D(x, y), depth);
    }

    public Point2D(Vec2D coords) {
        this(coords, 0);
    }

    public Point2D(double x, double y) {
        this(new Vec2D(x, y), 0);
    }

    public void isIntersection() {
        isIntersection = true;
    }

    public Vec2D getCoords() {
        return this.coords;
    }

    public Point2D clone() {
        return new Point2D(this.coords, depth);
    }

    public double x() {
        return coords.x();
    }

    public double y() {
        return coords.y();
    }

    public double depth() {
        return depth;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point2D)) return false;
        Point2D point = (Point2D) obj;
        return this.x() == point.x() && this.y() == point.y() && this.depth() == point.depth();
    }

    public double distanceTo(Point2D p) {
        return Math.sqrt((p.x()-x())*(p.x()-x()) + (p.y()-y())*(p.y()-y()));
    }


}
