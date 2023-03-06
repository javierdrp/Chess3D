package chess.piezas;

import chess.util.ColorEnum;
import chess.ui.Tile;

import java.util.ArrayList;

public class Rook extends Pieza{
    public Rook(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    public Rook(Pawn pawn)
    {
        super(pawn.getColor(),pawn.getTile());
        setBoard(pawn.getBoard());
        board.refreshPiezas();
    }

    @Override
    public ArrayList<Tile> getMoves() {
        ArrayList<Tile> ret = new ArrayList<Tile>();
        ret.addAll(getHorizontal());
        ret.addAll(getVertical());
        return ret;
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WROOK.png"
                : "BROOK.png";

    }
    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "R"
                : "r";
    }
}
