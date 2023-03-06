package rendering.model.piece;

import rendering.model.ChessPiece3D;
import rendering.util.Util;

import java.awt.Color;

public class Rook3D extends ChessPiece3D {
    public Rook3D(Color color) {
        super("r", color, Util.readMeshFromObjFile("models/pieces/rook.obj", color));
    }
}
