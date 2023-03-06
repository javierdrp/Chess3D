package rendering.mesh;

import rendering.renderer.shape.Poly2D;
import rendering.renderer.shape.Poly3D;
import rendering.util.Clickable;
import rendering.util.Vec3D;

import java.awt.Color;
import java.util.*;

/**
 * Un objeto en 3D formado por una serie de poligonos
 * @author Javier Díaz de Rábago
 */
public class Mesh3D {

    private final List<Poly3D> polys3D;
    private Vec3D orig;

    public Clickable clickable;

    public String name;

    public Mesh3D() {
        this("mesh");
    }

    public Mesh3D(String name) {
        this.polys3D = new ArrayList<>();
        this.orig = new Vec3D(0, 0, 0);
        this.name = name;
    }

    /**
     * Se puede usar para unir varios objetos en uno
     * @param name el nombre del nuevo objeto
     * @param meshes los objetos a unir
     */
    public Mesh3D(String name, Mesh3D... meshes) { // join meshes
        this(name);
        for(Mesh3D mesh : meshes) {
            for (Poly3D poly : mesh.getPolys3D())
                this.addPoly(poly);
        }
    }

    public void setBaseColor(Color color) {
        for(Poly3D poly : polys3D) poly.setBaseColor(color);
    }

    public void addPoly(Poly3D poly) {
        polys3D.add(poly);
    }
    
    public List<Poly3D> getPolys3D() {
        return polys3D;
    }

    /**
     * Convierte los poligonos 3D a 2D
     * @return Una lista con los poligonos proyectados
     */
    public List<Poly2D> getPolys2D() {
        if(clickable != null) clickable.chooseColor();
        List<Poly2D> polys2D = new ArrayList<>();
        polys3D.stream().map(Poly3D::to2D).filter(Poly2D::isFacingCamera).filter(p -> !p.isOutsideOfDisplay()).forEach(polys2D::add);
        return polys2D;
    }

    public void rotate(double... theta) {
        for(Poly3D poly : polys3D)
            poly.rotate(orig, theta);
    }

    public void move(double x, double y, double z) {
        for(Poly3D poly : polys3D)
            poly.move(x, y, z);
        orig = orig.add(new Vec3D(x, y, z));
    }

    public void move(Vec3D v) {
        move(v.x(), v.y(), v.z());
    }

    /**
     * Ejecuta el metodo click si lo tiene
     */
    public void clicked() {
        if(clickable != null) clickable.click();
    }

    public void moveTo(double x, double y, double z) {
        moveTo(new Vec3D(x, y, z));
    }

    public void moveTo(Vec3D v) {
        move(v.subtract(orig));
    }
}
