package rendering.model.piece;

import rendering.model.ChessPiece3D;
import rendering.util.Util;

import java.awt.Color;

public class Queen3D extends ChessPiece3D {
    public Queen3D(Color color) {
        super("q", color, Util.readMeshFromObjFile("models/pieces/queen.obj", color));
    }
}
