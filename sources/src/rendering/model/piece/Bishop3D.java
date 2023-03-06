package rendering.model.piece;

import rendering.model.ChessPiece3D;
import rendering.util.Util;

import java.awt.Color;

public class Bishop3D extends ChessPiece3D {
    public Bishop3D(Color color) {
        super("b", color, Util.readMeshFromObjFile("models/pieces/bishop.obj", color));
    }
}
