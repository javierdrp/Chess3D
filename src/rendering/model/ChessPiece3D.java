package rendering.model;

import data.Data;
import rendering.renderdata.Global;
import rendering.mesh.Mesh3D;
import rendering.util.Clickable;

import java.awt.Color;

public class ChessPiece3D extends Clickable {

    public static final Color WHITE = new Color(218, 203, 158);
    public static final Color BLACK = new Color(100, 69, 69);
    public static final Color SELECTED = new Color(133, 255, 105);
    public static final Color AVAILABLE = new Color(0xD8F7FF00);

    private Mesh3D mesh;

    public String name;

    public ChessPiece3D(String name, Color color, Mesh3D mesh) {
        this.name = (color.equals(ChessPiece3D.WHITE))?name.toUpperCase():name.toLowerCase();
        this.name = this.name + Board3D.use(this.name);
        this.color = color;
        this.mesh = mesh;
        if(color.equals(ChessPiece3D.BLACK)) this.mesh.rotate(0,180,0);
        moveToTile(pos);
        this.mesh.name = this.name;
        this.mesh.clickable = this;
    }

    public void moveToTile(int tile) {
        pos = tile;
        int x = tile % 8;
        int y = tile /8;
        mesh.moveTo(x-3.5, -.35, y-3.5);
    }

    public Mesh3D getMesh() {
        return mesh;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChessPiece3D that)
            return this.name.equals(that.name);
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public void chooseColor() {
        if(Data.selected == pos) mesh.setBaseColor(ChessPiece3D.SELECTED);
        else if(Data.available.contains(pos)) mesh.setBaseColor(ChessPiece3D.AVAILABLE);
        else mesh.setBaseColor(color);
    }
}
