package chess.piezas;

import chess.ui.ColorEnum;
import chess.ui.Tile;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Queen extends Pieza{
    @Override
    public ArrayList<Tile> getMoves() {
        ArrayList<ArrayList<Tile>> listas = new ArrayList<ArrayList<Tile>>();
        listas.add(getHorizontal());
        listas.add(getVertical());
        listas.add(getDiagPrincipal());
        listas.add(getDiagSec());
        ArrayList<Tile> ret = new ArrayList<Tile>();
        for(ArrayList<Tile> lista:listas)
            ret.addAll(lista);
        return ret;

    }

    public Queen(ColorEnum color, Tile tile) {
            super(color, tile);
        }

    public Queen(Pawn pawn)
    {
        super(pawn.getColor(),pawn.getTile());
        setBoard(pawn.getBoard());
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WQUEEN.png"
                : "BQUEEN.png";

    }

    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "Q"
                : "q";
    }


}
