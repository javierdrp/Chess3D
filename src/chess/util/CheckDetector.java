package chess.util;

import chess.piezas.King;
import chess.piezas.Pieza;
import chess.ui.Board;
import chess.ui.ColorEnum;
import chess.ui.Tile;

public abstract class CheckDetector {
    public static boolean detectCheck(Board board, ColorEnum color)
    {

        boolean cond = false;
        // Checks if player of given color is checked
        for(Tile tile:board.getAllMoves(color))
        {
            if(tile.isOccupied() && tile.getPieza() instanceof King)
                cond = true;

        }
        return cond;
    }

}
