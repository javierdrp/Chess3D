package chess.ui;

import chess.piezas.Pieza;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author Carlos Marí Noguera
 * @version 19/05/2022
 */
public class Tile extends JPanel
{

    private static int size = 100;
    private boolean isOccupied;
    private int color; // 0 white , 1 black
    private Color shade;
    private int position;
    private Pieza pieza;
    JLabel lblPieza;

    /**
     * Crea una casilla
     * @param isOccupied Si la casilla esta ocupada
     * @param color El color de la casilla
     * @param position El numero de la casilla en el tablero [0,63]
     */
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

    /**
     * Si la casilla esta ocupada
     * @return Si la casilla esta ocupada
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Devuelve el color
     * @return Devuelve el color
     */
    public int getColor() {
        return color;
    }

    /**
     * Actualiza el estado de la casilla
     * @param occupied Si la casila esta ocupada
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Actualiza el color del que se pintara la taquilla en 2D
     * @param shade El color de la taquilla en 2D
     */
    public void setShade(Color shade) {
        this.shade = shade;
    }

    /**
     * Devuleve la posicion de la casilla
     * @return La posicion de la casilla
     */
    public int getPosition() {
        return position;
    }

    /**
     * Ocupa la casilla con una pieza
     * @param pieza la pieza que ocupaara la casilla
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        if (pieza != null)
            this.setOccupied(true);
    }

    /**
     * La pieza que esta en la casilla
     * @return Devuelve la pieza que esta en la casilla
     */
    public Pieza getPieza() {
        if (isOccupied)
            return pieza;
        else
            return null;
    }

    /**
     * Repinta la casilla
     * @param g
     */
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

    /**
     * Devuelve el color con el que se pintara la casilla
     * @return El color con el que se pintara la casilla
     */
    public Color getShade() {
        return shade;
    }

    /**
     * El metodo pintar
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (isOccupied)
            pieza.draw(g);
    }

    /**
     * Restaura el color a el original
     */
    public void restoreColor() {
        if (color == 0)
            shade = Color.WHITE;
        else
            shade = Color.GRAY;
    }

    /**
     *
     * @return La coordenada de la casilla
     */
    @Override
    public String toString() {
        return getColumn() + Integer.toString(8-position/8);
    }

    /**
     * Devuelve si dos casillas son la misma
     * @param o
     * @return si la posicion es la misma
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return position == tile.position;
    }

    /**
     * Devuelve la columna de la casilla
     * @return La coumna de la casilla en formato de coordenadas
     */
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
