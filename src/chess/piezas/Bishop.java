package chess.piezas;

import chess.ui.ColorEnum;
import chess.ui.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Pieza{
    public Bishop(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Tile> getMoves() {
        ArrayList<Tile> moves = new ArrayList<Tile>();
        moves.addAll(getDiagSec());
        moves.addAll(getDiagPrincipal());
        return moves;
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WBISHOP.png"
                : "BBISHOP.png";

    }
    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "B"
                : "b";
    }
}
