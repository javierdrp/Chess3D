package chess.piezas;

import chess.ui.ColorEnum;
import chess.ui.Tile;

import java.util.ArrayList;

public class Knight extends Pieza{
    public Knight(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Tile> getMoves() {
        return getKnight();
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WKNIGHT.png"
                : "BKNIGHT.png";

    }
    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "N"
                : "n";
    }
}
