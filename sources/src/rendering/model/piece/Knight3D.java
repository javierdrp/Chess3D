package rendering.model.piece;

import rendering.model.ChessPiece3D;
import rendering.util.Util;

import java.awt.Color;

public class Knight3D extends ChessPiece3D {
    public Knight3D(Color color) {
        super("n", color, Util.readMeshFromObjFile("models/pieces/knight.obj", color));
    }
}
