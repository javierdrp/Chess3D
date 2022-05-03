package chess.piezas;

import chess.util.ColorEnum;
import chess.ui.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Pieza{
    public Pawn(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Tile> getMoves() {
        // Multiplicar lista por -1 si son blancos
        // Que cuando comen se mentengan en el mismo color
        Tile[] nBoard = board.getBoard();

        int position = getTile().getPosition();

        // White pieces move in the negative direction, black pieces in the positive
        int valueColor = getColor().getValue();
        ArrayList<Integer> relativeMoves = new ArrayList<Integer>(Arrays.asList(+8,+16));
        ArrayList<Tile> moves = new ArrayList<Tile>();


        if (position+16*valueColor >=  0 && position+16 * valueColor < 64 &&
                (hasMoved || nBoard[position+16*valueColor].isOccupied()))

                relativeMoves.remove(Integer.valueOf(16));

        if(position+8*valueColor >=  0 && position+8 * valueColor < 64 &&
                nBoard[position+8*valueColor].isOccupied())
        {
            relativeMoves.remove(Integer.valueOf(8));
            relativeMoves.remove(Integer.valueOf(16));
        }

        if(position+7*valueColor >=  0 && position+7 * valueColor < 64 &&
                (nBoard[position+7*valueColor].isOccupied() &&
                nBoard[position+7*valueColor].getPieza().getColor() != getColor()))
        {
            relativeMoves.add(Integer.valueOf(7));
        }

        if(position+9*valueColor >=  0 && position+9 * valueColor < 64 &&(nBoard[position+9*valueColor].isOccupied() &&
                nBoard[position+9*valueColor].getPieza().getColor() != getColor()))
        {
            relativeMoves.add(Integer.valueOf(9));
        }




        for(int relative:relativeMoves)
        {
            int newPos = relative*valueColor + position;
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



    @Override
    public boolean move(Tile newTile,boolean cond) {

        if ((newTile.getPosition() / 8 == 0 || newTile.getPosition() / 8 == 7) && cond)
        {
            if(newTile.isOccupied())
                board.getPiezas().remove(newTile.getPieza());
            Pieza p = new Queen(this);
            board.getPiezas().remove(this);
            board.getPiezas().add(p);
            boolean ret = p.move(newTile);
            board.loadGame(board.toString());
            board.repaint();
            return ret;
        }
        else{
            return super.move(newTile,cond);
        }

    }
}
