package chess.piezas;

import chess.util.ColorEnum;
import chess.ui.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Pieza {
    public King(ColorEnum color, Tile tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Tile> getMoves() {
        // -9,-8,-7,-1,+1,+7,+8,+9
        ArrayList<Tile> moves = new ArrayList<Tile>();
        int position = tile.getPosition();
        ArrayList<Integer> possibilities = new ArrayList<Integer>(Arrays.asList(-9,-8,-7,-1,+1,+7,+8,+9));
        if (position % 8 == 0)
        {
            possibilities.remove(Integer.valueOf(-1));
            possibilities.remove(Integer.valueOf(-9));
            possibilities.remove(Integer.valueOf(7));
            }
        else if ((position+1) % 8 == 0)
        {
            possibilities.remove(Integer.valueOf(1));
            possibilities.remove(Integer.valueOf(9));
            possibilities.remove(Integer.valueOf(-7));
        }
        Tile[] nBoard = board.getBoard();
        for(int z:possibilities)
        {
            if(position + z > -1 && position + z < 64) {
                if (!nBoard[position+z].isOccupied() ||
                        (nBoard[position+z].isOccupied()&&nBoard[position+z].getPieza().getColor()!=getColor()) )
                moves.add(nBoard[position + z]);
            }
        }
        return moves;
    }

    @Override
    public String getImage() {
        return getColor() == ColorEnum.WHITE
                ? "WKING.png"
                : "BKING.png";

    }
    @Override
    public String toString() {
        return getColor() == ColorEnum.WHITE
                ? "K"
                : "k";
    }
}
