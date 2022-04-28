package rendering.util;

public class Matrix {

    public static double[][] multiply(double[][] m1, double[][] m2) {
        double[][] result = new double[m1.length][m2[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(m1, m2, row, col);
            }
        }

        return result;
    }

    private static double multiplyMatricesCell(double[][] m1, double[][] m2, int row, int col) {
        double cell = 0;
        for (int i = 0; i < m2.length; i++) {
            cell += m1[row][i] * m2[i][col];
        }
        return cell;
    }

    public static double[][] rotationX(double theta) {
        return new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(theta), -Math.sin(theta), 0},
                {0, Math.sin(theta), Math.cos(theta), 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotationY(double theta) {
        return new double[][]{
                {Math.cos(theta), 0, Math.sin(theta), 0},
                {0, 1, 0, 0},
                {-Math.sin(theta), 0, Math.cos(theta), 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotationZ(double theta) {
        return new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0, 0},
                {Math.sin(theta), Math.cos(theta), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] rotation(double... theta) {
        return multiply(multiply(rotationZ(theta[2] * Math.PI/180), rotationY(theta[1] * Math.PI/180)), rotationX(theta[0] * Math.PI/180));
    }

    public static double[][] translation(double x, double y, double z) {
        return new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {x, y, z, 1}
        };
    }

    public static double[][] lookAt(Vec3D pos, Vec3D target, Vec3D up) {
        Vec3D newForward = target.subtract(pos).normalize();
        Vec3D newUp = up.subtract(newForward.mult(up.dotProduct(newForward))).normalize();
        Vec3D newRight = newUp.crossProduct(newForward).normalize();

        return new double[][]{
                {             newRight.x(),              newUp.x(),              newForward.x(),  0},
                {             newRight.y(),              newUp.y(),              newForward.y(),  0},
                {             newRight.z(),              newUp.z(),              newForward.z(),  0},
                {-pos.dotProduct(newRight), -pos.dotProduct(newUp), -pos.dotProduct(newForward),  1}
        };
    }
}
