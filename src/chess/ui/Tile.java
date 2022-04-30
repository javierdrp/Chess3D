package chess.ui;

import chess.piezas.Pieza;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tile extends JPanel
{
    private static int size = 100;
    private boolean isOccupied;
    private int color; // 0 white , 1 black
    private Color shade;
    private int position;
    private Pieza pieza;
    JLabel lblPieza;
    public Tile(boolean isOccupied, int color, int position) {
        this.isOccupied = isOccupied;
        this.color = color;
        this.position = position;
        this.lblPieza = new JLabel();
        this.setSize(size,size);
        restoreColor();
        //this.lblPieza.setVisible(false);
        //this.add(lblPieza,10);
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getColor() {
        return color;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setShade(Color shade) {
        this.shade = shade;
    }

    public int getPosition() {
        return position;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        if (pieza != null)
            this.setOccupied(true);
    }

    public Pieza getPieza() {
        if (isOccupied)
            return pieza;
        else
            return null;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(shade);
        //System.out.println("PAINTING AT "+ this.getX() + ", " + this.getY());
        g.fillRect(this.getX(),this.getY(),size,size);
        if (isOccupied)
            pieza.draw(g);

    }

    public Color getShade() {
        return shade;
    }

    @Override
    public void paint(Graphics g) {
        if (isOccupied)
            pieza.draw(g);
    }
    public void restoreColor() {
        if (color == 0)
            shade = Color.WHITE;
        else
            shade = Color.GRAY;
    }
    @Override
    public String toString() {
        return getColumn() + Integer.toString(8-position/8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return position == tile.position;
    }

    public String getColumn()
    {
        int coordinate = position % 8;
        char position = 'z';
        switch(coordinate)
        {
            case(0): position = 'a'; break;
            case(1): position = 'b'; break;
            case(2): position = 'c'; break;
            case(3): position = 'd'; break;
            case(4): position = 'e'; break;
            case(5): position = 'f'; break;
            case(6): position = 'g'; break;
            case(7): position = 'h'; break;
            default: System.out.println("ERROR");

        }
        return Character.toString(position);
    }

}
