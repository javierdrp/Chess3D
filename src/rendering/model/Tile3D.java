package rendering.model;

import data.Data;
import rendering.renderdata.Global;
import rendering.mesh.Mesh3D;
import rendering.util.Clickable;

import java.awt.Color;

public class Tile3D extends Clickable {

    public static final Color WHITE = new Color(0xFFFFFF);
    public static final Color BLACK = new Color(0x156900);
    public static final Color SELECTED = new Color(0xD8F7FF00);
    public static final Color AVAILABLE = new Color(0x75FF3C);

    public Mesh3D mesh;

    public Tile3D(Mesh3D mesh, int pos) {
        this.mesh = mesh;
        this.mesh.clickable = this;
        this.pos = pos;
        this.color = mesh.getPolys3D().get(0).getBaseColor();
    }

    @Override
    public void chooseColor() {
        if(Data.selected == pos) mesh.setBaseColor(Tile3D.SELECTED);
        else if(Data.available.contains(pos)) mesh.setBaseColor(Tile3D.AVAILABLE);
        else mesh.setBaseColor(color);
    }
}
