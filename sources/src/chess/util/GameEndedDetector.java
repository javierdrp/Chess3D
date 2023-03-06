package chess.util;

import chess.piezas.King;
import chess.ui.Board;
import chess.ui.GameEndedDialog;
import chess.ui.Tile;

import java.util.List;

public class GameEndedDetector {
    /* Checks if the game has ended, the game ends if
            -- Time has finished for any of the players
                -- If the players with time on the clock doesn't have enough checmate material Draw
                --  Else win
            -- Both of the players lack Checkmate material DRAW
            -- A player doesn't have any moves
                -- If that player is checked -- Checkmate
                -- else StaleMate
            -- 50 consecutive moves without moving a pawn (draw)
            -- 3 Consecutive sequences of equal moves (draw)

            -- A player doesnt have enough checkmate material if
                -- It only has a bishop
                -- It only has a knight or 2 knights
                -- A player has 2 white bishops or 2 black bishops
     */
    King wKing;
    King bKing;
    Board board;
    public GameEndedDetector(Board board)
    {
        this.board = board;
        this.wKing = board.getKing(ColorEnum.WHITE);
        this.bKing = board.getKing(ColorEnum.BLACK);
    }

    public boolean inCheckmate(ColorEnum color)
    {
        List<Tile> movs = board.getLegalMoves(color);
        if(movs.size() == 0)
            if(CheckDetector.detectCheck(board,color.changeColor()))
                new GameEndedDialog(GameEndedDialog.WIN,color.changeColor());
            else
                new GameEndedDialog(GameEndedDialog.DRAW,color);
        /*if (CheckDetector.detectCheck(board,color.changeColor(color)))
        {
            if (movs.size() == 0)
                System.out.println("Adios");

        }*/
        return false;
    }
}
