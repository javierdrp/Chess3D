package rendering.util;

import rendering.mesh.Mesh3D;
import rendering.renderer.point.Point3D;
import rendering.renderer.shape.Poly3D;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Vec3D multVecMatrix4x4(Vec3D vec, double[][] mat) {
        double x = vec.x() * mat[0][0] + vec.y() * mat[1][0] + vec.z() * mat[2][0] + mat[3][0];
        double y = vec.x() * mat[0][1] + vec.y() * mat[1][1] + vec.z() * mat[2][1] + mat[3][1];
        double z = vec.x() * mat[0][2] + vec.y() * mat[1][2] + vec.z() * mat[2][2] + mat[3][2];
        double w = vec.x() * mat[0][3] + vec.y() * mat[1][3] + vec.z() * mat[2][3] + mat[3][3];

        if (w != 0.0f) {
            x /= w;
            y /= w;
            //z /= w;
        }

        return new Vec3D(x, y, z);
    }

    public static Mesh3D readMeshFromObjFile(String path, Color color) {
        List<Point3D> vertices = new ArrayList<>();
        BufferedReader reader;
        Mesh3D mesh = new Mesh3D();
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            line = reader.readLine();
            while(line != null) {
                String[] elems = line.split(" ");
                if(elems.length == 0) break;
                switch(elems[0]) {
                    case "v":
                        vertices.add(new Point3D(
                                Double.parseDouble(elems[1]),
                                Double.parseDouble(elems[2]),
                                Double.parseDouble(elems[3])
                        ));
                        break;
                    case "f":
                        mesh.addPoly(new Poly3D(mesh, color,
                                vertices.get(Integer.parseInt(elems[1])-1),
                                vertices.get(Integer.parseInt(elems[2])-1),
                                vertices.get(Integer.parseInt(elems[3])-1)
                        ));
                        break;
                    default:
                        break;
                }
                line = reader.readLine();
            }
            reader.close();

        }
        catch(Exception e) {
            return null;
        }
        return mesh;
    }
}
