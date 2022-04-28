package rendering.lighting;

import rendering.util.Vec3D;

public class DirectionalLight {

    private Vec3D direction;

    public DirectionalLight(Vec3D direction) {
        this.direction = direction.normalize();
    }

    public DirectionalLight(double... direction) {
        this(new Vec3D(direction));
    }

    public Vec3D getDirection() {
        return direction;
    }

    public void setDirection(Vec3D direction) {
        this.direction = direction;
    }
}
