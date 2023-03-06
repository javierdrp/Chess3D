package rendering.renderer.shape;

import rendering.renderdata.Global;
import rendering.mesh.Mesh3D;
import rendering.renderer.point.Point3D;
import rendering.util.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Poly3D {

    private Color baseColor;
    private List<Point3D> contour; // counter-clockwise
    private Mesh3D mesh;

    public Poly3D(Mesh3D mesh, Color color, Point3D... contour) {
        this.mesh = mesh;
        this.baseColor = color;
        this.contour = new ArrayList<>(contour.length);
        for(Point3D point : contour) this.contour.add(point.clone());
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Mesh3D getMesh() {
        return mesh;
    }

    // triangulate ------ INTENTAR TRIANGULACION DE MONOTONOS - POR AHORA ASUMO QUE SOLO TRIANGULOS Y POLIGONOS CONVEXOS
   /* public List<Poly3D> toTriangles() {
        List<Poly3D> tris = new ArrayList<>();
        //if(vertices3D.size() == 3) tris.add(this);
        *//*else if(vertices3D.size() == 4) {
            tris = to2D().toTriangles().stream().map(Polygon2D::to3D).toList();
        }*//*

        for(int i = 1; i < mainContour.size()-1; i++) {
            tris.add(new Poly3D(baseColor, mainContour.get(0), mainContour.get(i), mainContour.get(i+1)));
        }
        return tris;
    }*/

    public Poly2D to2D() {
        Poly2D poly2D = new Poly2D(this, calculateColor());
        for(Point3D point : this.getContour())
            poly2D.addVertex(Global.camera.projectPoint(point));
            //poly2D.addVertex(new Point2D(new Vector2D(point.x()*100+250, point.y()*100+250), -point.z()));
        return poly2D;
    }

    private Color calculateColor() {
        if(Global.light == null) return baseColor;
        double factor = normal().dotProduct(Global.light.getDirection()) / 3 + .66;//Util.multVecMatrix4x4(Global.light.getDirection(), Global.camera.getMatProj()));
        if(factor <= 0) return Color.BLACK;
        return new Color(
                (int)(baseColor.getRed() * Math.abs(factor)),
                (int)(baseColor.getGreen() * Math.abs(factor)),
                (int)(baseColor.getBlue() * Math.abs(factor))
        );
//        return baseColor;
    }

    public Vec3D normal() {
        return contour.get(1).getCoords().subtract(contour.get(0).getCoords())
                .crossProduct
              (contour.get(2).getCoords().subtract(contour.get(0).getCoords())).normalize();
    }

//    public Poly3D relativeToCamera() {
//        return new Poly3D(baseColor, contour.stream().map(p -> new Point3D(Util.multVecMatrix4x4(p.getCoords(), Matrix.identity(4)/*Global.camera.worldMatrix()*/))).toArray(Point3D[]::new));
//    }

    public void rotate(Vec3D orig, double... theta) {
        for(int i = 0; i < contour.size(); i++) {
            contour.set(i, contour.get(i).rotate(orig, theta));
        }
    }

    public void move(double x, double y, double z) {
        for(int i = 0; i < contour.size(); i++) {
            contour.set(i, contour.get(i).clone().move(x, y, z));
        }
    }

    public List<Point3D> getContour() {
        return contour;
    }
}
