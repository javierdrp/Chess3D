package chess.piezas;

import chess.ui.ColorEnum;
import chess.ui.Tile;

import java.util.ArrayList;

public class Pawn extends Pieza{
    public Pawn(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Tile> getMoves() {
        // Multiplicar lista por -1 si son blancos
        // Que cuando comen se mentengan en el mismo color

        ArrayList<Integer> relativeMoves = new ArrayList<Integer>();
        ArrayList<Tile> moves = new ArrayList<Tile>();
        if (hasMoved)
            if(getColor() == ColorEnum.WHITE)
                relativeMoves.add(-8);
            else
                relativeMoves.add(+8);
        else{
        if(getColor() == ColorEnum.WHITE) {
            relativeMoves.add(-8);
            relativeMoves.add(-16);
        }
        else {
            relativeMoves.add(+8);
            relativeMoves.add(+16);
        }}
        Tile[] nBoard = board.getBoard();
        int position = tile.getPosition();
        // Crear White y Black Pawns (class)
        if(getColor() == ColorEnum.WHITE){
            if (nBoard[position-7].isOccupied() && nBoard[position-7].getPieza().getColor() != ColorEnum.WHITE)
                relativeMoves.add(-7);
            if(nBoard[position-9].isOccupied() && nBoard[position-9].getPieza().getColor() != ColorEnum.WHITE)
                relativeMoves.add(-9);
        }else{
            if (nBoard[position+7].isOccupied() && nBoard[position+7].getPieza().getColor() != ColorEnum.BLACK)
                relativeMoves.add(+7);
            if(nBoard[position+9].isOccupied() && nBoard[position+9].getPieza().getColor() != ColorEnum.BLACK)
                relativeMoves.add(+9);
        }
        for(int relative:relativeMoves)
        {
            int newPos = relative + position;
            if (newPos < 64 && newPos >= 0)
                moves.add(nBoard[newPos]);
        }
        return moves;
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WPAWN.png"
                : "BPAWN.png";

    }
    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "P"
                : "p";
    }
}
