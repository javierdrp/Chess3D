package rendering.model.piece;

import rendering.model.ChessPiece3D;
import rendering.util.Util;

import java.awt.Color;

public class Pawn3D extends ChessPiece3D {
    public Pawn3D(Color color) {
        super("p", color, Util.readMeshFromObjFile("models/pieces/pawn.obj", color));
    }
}
