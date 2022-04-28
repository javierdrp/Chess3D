package rendering.renderer.shape;

import rendering.renderdata.Global;
import rendering.renderer.point.Point2D;
import rendering.util.Vec3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Poly2D {

    final double INF = 10000;

    private final Poly3D poly3D;
    private List<Point2D> contour; // clockwise
    private Color renderColor;

    private double[] rect;

    public Poly2D(Poly3D poly3D, Color color) {
        contour = new ArrayList<>();
        this.renderColor = color;
        rect = new double[4];
        this.poly3D = poly3D;
    }

    public List<Point2D> getContour() {
        return contour;
    }

    public int sides() {
        return contour.size();
    }

    public Color getRenderColor() {
        return renderColor;
    }

    public void addVertex(Point2D point) {
        if (contour.size() == 0)
            rect = new double[]{point.x(), point.x(), point.y(), point.y()};
        else {
            if (point.x() < rect[0]) rect[0] = point.x();
            if (point.x() > rect[1]) rect[1] = point.x();
            if (point.y() < rect[2]) rect[2] = point.y();
            if (point.y() > rect[3]) rect[3] = point.y();
        }
        contour.add(point);
    }

    public boolean isFacingCamera() {
        Vec3D cp = Vec3D.crossProduct(contour.get(1).x() - contour.get(0).x(),
                                            contour.get(1).y() - contour.get(0).y(),
                                            0,
                                            contour.get(2).x() - contour.get(0).x(),
                                            contour.get(2).y() - contour.get(0).y(),
                                            0);
        return cp.z() < 0;
    }

    // draws the outline of the polygon
    public void renderWireframe(Graphics g, float stroke) {
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(stroke));
        for (int i = 0; i < contour.size(); i++) {
            Point2D p1 = contour.get(i);
            Point2D p2 = contour.get((i + 1) % contour.size());
            g.drawLine((int) (p1.x()), (int) (p1.y()), (int) (p2.x()), (int) (p2.y()));
        }
    }

    /*
        public void render(Graphics g) {
            g.setColor(color);
            g.fillPolygon(getXCoords(), getYCoords(), vertices2D.size());
        }
    */
    private int[] getXCoords() {
        int[] xCoords = new int[contour.size()];
        for (int i = 0; i < contour.size(); i++) xCoords[i] = (int) (contour.get(i).x());
        return xCoords;
    }

    private int[] getYCoords() {
        int[] yCoords = new int[contour.size()];
        for (int i = 0; i < contour.size(); i++) yCoords[i] = (int) (contour.get(i).y());
        return yCoords;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    public static int orientation(Point2D p, Point2D q, Point2D r) {
        // Calculates orientation of three points
        double val = (q.y() - p.y()) * (r.x() - q.x()) -
                (q.x() - p.x()) * (r.y() - q.y());

        if (val == 0) return 0; // collinear

        return (val > 0) ? 2 : 1; // clock or counterclock wise
    }

    public boolean containsPoint(Point2D point) {
        boolean inside = false;
        for(int i = 0, j = contour.size()-1; i < contour.size(); j = i++) {
            if((contour.get(i).y() > point.y()) != (contour.get(j).y() > point.y()) &&
                point.x() < (contour.get(j).x() - contour.get(i).x()) * (point.y() - contour.get(i).y()) / (contour.get(j).y() - contour.get(i).y()) + contour.get(i).x())
                inside = !inside;
        }
        return inside;
    }

    @Override
    public String toString() {
        return renderColor.toString();
    }

    public String getRect() {
        return rect[0] + " " + rect[1] + " " + rect[2] + " " + rect[3];
    }

    // intersects normal line to camera with polygon plane
    public double getDepthAtPixel(Point2D p) {
        Point2D a = contour.get(0);
        Point2D b = contour.get(1);
        Point2D c = contour.get(2);
        //System.out.println(a.depth() + " " + b.depth() + " " + c.depth());
        double depth = a.depth() +
                (
                        (p.x() - a.x()) * (b.y() - a.y()) * (c.depth() - a.depth()) + (p.y() - a.y()) * (b.depth() - a.depth()) * (c.x() - a.x()) - (p.x() - a.x()) * (b.depth() - a.depth()) * (c.y() - a.y()) - (p.y() - a.y()) * (b.x() - a.x()) * (c.depth() - a.depth())
                ) / (
                        (b.y() - a.y()) * (c.x() - a.x()) - (b.x() - a.x()) * (c.y() - a.y())
                );
        return depth;
    }

    public Point2D getVertex(int i) {
        return contour.get(i);
    }

    public double getClosestDepth() {
        return contour.stream().sorted(Comparator.comparingDouble(Point2D::depth)).toList().get(0).depth();
    }

    public boolean isOutsideOfDisplay() {
        double[] boundingBox = boundingBox();
        return (boundingBox[0] > Global.display.getWidth() ||
                boundingBox[1] < 0 ||
                boundingBox[2] > Global.display.getHeight() ||
                boundingBox[3] < 0);
    }

    private double[] boundingBox() {
        return new double[]{
                contour.stream().sorted(Comparator.comparingDouble(Point2D::x)).toList().get(0).x(),
                contour.stream().sorted(Comparator.comparingDouble(Point2D::x)).toList().get(2).x(),
                contour.stream().sorted(Comparator.comparingDouble(Point2D::y)).toList().get(0).y(),
                contour.stream().sorted(Comparator.comparingDouble(Point2D::y)).toList().get(2).y()
        };
    }

    public double avgDepth() {
        return (contour.get(0).depth() + contour.get(1).depth() + contour.get(2).depth()) / 3;
    }

    public boolean clicked(int x, int y) {
        if(containsPoint(new Point2D(x, y))) {
            poly3D.getMesh().clicked();
            return true;
        }
        return false;
    }
}
