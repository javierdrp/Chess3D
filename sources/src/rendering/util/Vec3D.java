package rendering.util;

public class Vec3D {
    private double x, y, z;

    public Vec3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3D(double... coords) {
        this.x = coords[0];
        this.y = coords[1];
        this.z = coords[2];
    }

    public Vec3D normalize() {
        double len = magnitude();
        return this.divide(len);
    }

    public double[] toArray() {
        return new double[]{this.x, this.y, this.z};
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Vec3D add(Vec3D vector) {
        return new Vec3D(this.x + vector.x(), this.y + vector.y(), this.z + vector.z());
    }

    public Vec3D subtract(Vec3D vector) {
        return new Vec3D(this.x - vector.x(), this.y - vector.y(), this.z - vector.z());
    }

    public Vec3D mult(double num) {
        return new Vec3D(this.x * num, this.y * num, this.z * num);
    }

    public Vec3D divide(double num) {
        return new Vec3D(this.x / num, this.y / num, this.z / num);
    }

    public Vec3D crossProduct(Vec3D v2) {
        return Vec3D.crossProduct(x, y, z, v2.x(), v2.y(), v2.z());
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public static Vec3D crossProduct(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Vec3D(
                y1 * z2 - z1 * y2,
                z1 * x2 - x1 * z2,
                x1 * y2 - y1 * x2
        );
    }

    public static double dotProduct(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1*x2 + y1*y2 + z1*z2;
    }

    public double dotProduct(Vec3D v2) {
        return x*v2.x() + y*v2.y() + z*v2.z();
    }


}
