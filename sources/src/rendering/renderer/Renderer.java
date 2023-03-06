package rendering.renderer;

import data.Data;
import rendering.renderdata.Global;
import rendering.mesh.Mesh3D;
import rendering.model.ChessPiece3D;
import rendering.renderer.shape.Poly2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase que se encarga de todo el proceso de renderizado de los objetos
 * @author Javier Díaz de Rábago
 */
public class Renderer {

    public static final int FILL = 0;
    public static final int WIREFRAME = 1;

    public static Graphics graphics;
    private static final Color BGCOLOR = new Color(136, 136, 136, 255);

    private static List<Poly2D> polys2D;

    /**
     * Controla el proceso de renderizado segun el modo elegido:
     * FILL: el modo utilizado en el juego, muestra los objetos normalmente
     * WIREFRAME: solo se marcan los contornos de los poligonos, util en debugging
     * @param mode Modo de renderizado
     */
    public static void render(int mode) {
        Global.camera.prepareToRender();
        switch (mode) {
            case FILL:
                renderAllPixels();
                break;
            case WIREFRAME:
                getAllPolys2D().forEach(p -> p.renderWireframe(graphics, 5));
                break;
            default:
                break;
        }
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        graphics.setColor(ChessPiece3D.WHITE);
        graphics.drawString(Data.whiteTime, 50, 50);
        graphics.setColor(ChessPiece3D.BLACK);
        graphics.drawString(Data.blackTime, Global.display.getWidth()-135, 50);
    }

    private static List<Poly2D> getAllPolys2D() {
        polys2D = new ArrayList<>();
        Global.meshBuffer.getMeshes().stream().map(Mesh3D::getPolys2D).forEach(polys2D::addAll);
        polys2D = polys2D.stream().sorted(Comparator.comparingDouble(Poly2D::avgDepth)).toList();
        return polys2D;
    }

    private static void renderAllPixels() {
        for(Poly2D poly : getAllPolys2D()) {
            graphics.setColor(poly.getRenderColor());
            graphics.fillPolygon(new int[]{(int)poly.getVertex(0).x(), (int)poly.getVertex(1).x(), (int)poly.getVertex(2).x()},
                                 new int[]{(int)poly.getVertex(0).y(), (int)poly.getVertex(1).y(), (int)poly.getVertex(2).y()}, 3);
        }
    }

    public static void clear() {
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, Global.display.getWidth(), Global.display.getHeight());
    }

    /**
     * Al hacer click con el raton, este metodo se encarga de comprobar sobre que poligono se encuentra
     * @param x Posicion horizontal del raton
     * @param y Posicion vertical del raton
     */
    public static void click(int x, int y) {
        try {
            if (polys2D != null)
                for (int i = 0; i < polys2D.size(); i++)
                    //for(int i = polys2D.size()-1; i >= 0; i--)
                    if (polys2D.get(polys2D.size() - i - 1).clicked(x, y))
                        break;
        } catch (Exception e) {

        }
    }
}
