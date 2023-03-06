package chess.piezas;

import chess.ui.PromotionDialog;
import chess.util.ColorEnum;
import chess.ui.Tile;
import chess.util.PromotionThread;
import data.Data;
import rendering.model.Board3D;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.currentThread;

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

        int newPosition = position+16*valueColor;
        if (newPosition >=  0 && newPosition < 64 &&
                (hasMoved || nBoard[newPosition].isOccupied()))

                relativeMoves.remove(Integer.valueOf(16));

        newPosition = position+8*valueColor;
        if(newPosition >=  0 && newPosition < 64 &&
                nBoard[position+8*valueColor].isOccupied())
        {
            relativeMoves.remove(Integer.valueOf(8));
            relativeMoves.remove(Integer.valueOf(16));
        }

        newPosition = position+7*valueColor;
        if(newPosition >=  0 && newPosition < 64 &&
                (nBoard[newPosition].isOccupied() &&
                nBoard[newPosition].getPieza().getColor() != getColor())
                && nBoard[newPosition].getColor() == tile.getColor())
        {
            relativeMoves.add(Integer.valueOf(7));
        }

        newPosition = position+9*valueColor;
        if(newPosition >=  0 && newPosition < 64 &&(nBoard[newPosition].isOccupied() &&
                nBoard[newPosition].getPieza().getColor() != getColor())
                && nBoard[newPosition].getColor() == tile.getColor())
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

            PromotionDialog pml = new PromotionDialog(this,newTile);


            //System.out.println("He Salido");
            //Pieza p = new Queen(this);//pml.getPieza();

            return true;
        }
        else{
            return super.move(newTile,cond);
        }

    }

    public void finishPromotion(Pieza p,Tile newTile)
    {
        board.getPiezas().remove(this);
        board.getPiezas().add(p);
        p.move(newTile);
        Board3D.addPiece(p.toString());
        board.loadGame(board.toString());
        board.repaint();
        Data.game = board.toString();
    }
}
