package rendering.renderer.camera;

import rendering.renderdata.Global;
import rendering.renderer.point.Point2D;
import rendering.renderer.point.Point3D;
import rendering.util.Matrix;
import rendering.util.Util;
import rendering.util.Vec2D;
import rendering.util.Vec3D;

public class Camera {
    private Vec3D position;
    private Vec3D up;
    private Vec3D lookDir;
    private double near;
    private double far;
    private double fov;
    private double aspectRatio;
    private double fovRad;
    private double[][] matView, matProj, matWorld;

    private double[] rotation;

    private Vec3D target;

    public Camera() {
        near = 0.01;
        far = 100;
        fov = 60;
        rotation = new double[]{0,0,0};
        position = new Vec3D(0, 0, -15);
        target = new Vec3D(0, 0, 1);
        up = new Vec3D(0, 1, 0);
        //target = position.add(lookDir);
    }

    public double[] getRotation() {
        return rotation;
    }

    public void setRotation(double... rotation) {
        this.rotation = rotation;
        this.rotation[0] %= 360;
        this.rotation[1] %= 360;
        this.rotation[2] %= 360;
    }

    public double[][] getMatProj() {
        return matProj;
    }

    public void rotate(double... rotation) {
        this.rotation[0] -= rotation[0];
        this.rotation[1] -= rotation[1];
        this.rotation[2] -= rotation[2];
        if(this.rotation[0] < -90) {
            this.rotation[0] = -90;
        }
        if(this.rotation[0] > 0) this.rotation[0] = 0;
        this.rotation[1] %= 360;
        this.rotation[2] %= 360;
    }

    public Vec3D getPosition() {
        return position;
    }

    public void setPosition(Vec3D position) {
        this.position = position;
    }

    public void setPosition(double x, double y, double z) {
        this.position = new Vec3D(x, y, z);
    }

    public void prepareToRender() {
        aspectRatio = (double)Global.display.getHeight() / (double)Global.display.getWidth();
        fovRad = 1.0 / Math.tan(fov * 0.5 / 180.0 * Math.PI);
        matView = Matrix.lookAt(position, target, up);
        matWorld = Matrix.multiply(Matrix.rotation(rotation), Matrix.translation(position.x(), position.y(), position.z()));
        matProj = new double[][]{
                {aspectRatio * fovRad, 0, 0, 0},
                {0, fovRad, 0, 0},
                {0, 0, far / (far - near), 1},
                {0, 0, -far * near / (far - near), 0}
        };
    }

    public void zoomIn(double zoom) {
        fov += zoom;
        if(fov < 5) fov = 5;
        if(fov > 150) fov = 150;
    }

    public Point2D projectPoint(Point3D point) {
//
//        for(double[] d : matView)
//            System.out.println(d[0]+" "+d[1]+" "+d[2]+" "+d[3]);

        // project to a -1 to 1 range on x and y
        //Vector3D vec = Util.multVecMatrix4x4(Util.multVecMatrix4x4(Util.multVecMatrix4x4(point.getCoords(), Matrix.rotationX(rotation[0])), Matrix.rotationY(rotation[1])), Matrix.rotationZ(rotation[2]));
        Vec3D vec = Util.multVecMatrix4x4(point.getCoords(), matWorld);//matView);
        //vec = Util.multVecMatrix4x4(vec, Matrix.translation());
        vec = Util.multVecMatrix4x4(vec, matProj);
        //vec = vec.add(new Vector3D(0, 0, -10));
        // adapt to display

        vec.setX((double)Global.display.getWidth() / 2 * (1 - vec.x()));
        vec.setY((double)Global.display.getHeight() / 2 * (1 + vec.y()));

        return new Point2D(new Vec2D(vec.x(), vec.y()), vec.z());
//        return new Point2D(new Vector2D((double)Global.display.getWidth() / 2 * (1 + point.x()), (double)Global.display.getHeight() / 2 * (1 - point.y())), point.z());

    }
}
