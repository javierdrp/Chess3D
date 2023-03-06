package rendering.util;

public class Vec2D {
    private double x, y;

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(double[] coords) {
        this.x = coords[0];
        this.y = coords[1];
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double[] toArray() {
        return new double[]{this.x, this.y};
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public Vec2D add(Vec2D vector) {
        return new Vec2D(this.x + vector.x(), this.y + vector.y());
    }

    public Vec2D subtract(Vec2D vector) {
        return new Vec2D(this.x - vector.x(), this.y - vector.y());
    }
}
