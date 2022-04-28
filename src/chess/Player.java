package chess;

import chess.piezas.Pieza;
import chess.ui.ColorEnum;

import java.util.ArrayList;

public class Player {
    private ArrayList<Pieza> piezas;
    private ColorEnum color;
    private boolean isTurn;

    public Player(ColorEnum color, boolean isTurn) {
        this.color = color;
        this.isTurn = isTurn;
    }

    public void setPiezas(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public ColorEnum getColor() {
        return color;
    }

    public boolean isTurn() {
        return isTurn;
    }
}
