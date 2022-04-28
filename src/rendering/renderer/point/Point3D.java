package rendering.renderer.point;

import rendering.util.Matrix;
import rendering.util.Util;
import rendering.util.Vec3D;

public class Point3D {

    private Vec3D coords;

    public Point3D(double x, double y, double z) {
        this.coords = new Vec3D(x, y, z);
    }

    public Point3D(Vec3D coords) {
        this.coords = coords;
    }

    public Vec3D getCoords() {
        return this.coords;
    }

    public Point3D clone() {
        return new Point3D(this.coords);
    }

    public double x() {
        return coords.x();
    }

    public double y() {
        return coords.y();
    }

    public double z() {
        return coords.z();
    }

   /* public boolean isConvex(Point3D b, Point3D c) {
        double cosAlpha = (
                    (b.x()-x())*(c.x()-x()) + (b.y()-y())*(c.y()-y()) + (b.z()-z())*(c.z()-z())
                ) / Math.sqrt(
                    (Math.pow(b.x()-x(), 2) + Math.pow(b.y()-y(), 2) + Math.pow(b.z()-z(), 2)) *
                    (Math.pow(c.x()-x(), 2) + Math.pow(c.y()-y(), 2) + Math.pow(c.z()-z(), 2))
                );
        double alpha = Math.acos(cosAlpha);
    }
*/

    @Override
    public String toString() {
        return "Point3D{" +
                "coords=" + coords +
                '}';
    }

    public Point3D rotate(Vec3D orig, double... theta) {
        Vec3D vec = coords.subtract(orig);
        vec = Util.multVecMatrix4x4(vec, Matrix.rotation(theta));
        return new Point3D(
            vec.add(orig)
        );
    }

    public Point3D move(double x, double y, double z) {
        return new Point3D(coords.add(new Vec3D(x, y, z)));
    }

//    public Point3D rotateAxisX(boolean CW, double degrees) {
//        double radius = Math.sqrt(this.y() * this.y() + this.z() * this.z());
//        double theta = Math.atan2(this.z(), this.y());
//        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
//        coords.setY(radius * Math.cos(theta));
//        coords.setZ(radius * Math.sin(theta));
//        return this;
//    }
//
//    public Point3D rotateAxisY(boolean CW, double degrees) {
//        double radius = Math.sqrt(this.x() * this.x() + this.z() * this.z());
//        double theta = Math.atan2(this.x(), this.z());
//        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
//        coords.setX(radius * Math.sin(theta));
//        coords.setZ(radius * Math.cos(theta));
//        return this;
//    }
//
//    public Point3D rotateAxisZ(boolean CW, double degrees) {
//        double radius = Math.sqrt(this.x() * this.x() + this.y() * this.y());
//        double theta = Math.atan2(this.y(), this.x());
//        theta += 2 * Math.PI / 360 * degrees * (CW?-1:1);
//        coords.setX(radius * Math.cos(theta));
//        coords.setY(radius * Math.sin(theta));
//        return this;
//    }
}
