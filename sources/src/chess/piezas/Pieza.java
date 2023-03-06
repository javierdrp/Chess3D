package chess.piezas;

import chess.ui.Board;
import chess.util.ColorEnum;
import chess.ui.Tile;
import chess.util.CheckDetector;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Esta clase define la estructura que seguiran todas las piezas, asi como su interfaz y metodos comunes
 * @author Carlos Mari Noguera
 * @version : 19/05/2022/A
 *
 */
public abstract class Pieza
{

    protected static Board board;
    private ColorEnum color;
    private int x;
    private int y;
    protected Tile tile;
    private BufferedImage img;
    protected boolean hasMoved;
    //private Image image

    /**
     *
     *
     * @param color El color de la pieza
     * @param tile La casilla del tablero en la que se encotrara la pieza
     */
    public Pieza(ColorEnum color,Tile tile) {

        this.color = color;
        this.tile = tile;
        hasMoved = false;
        tile.setPieza(this);
        x = tile.getX();
        y = tile.getY();
        String imgFile = getImage();
        try{
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource("resources/" + imgFile));
            }
            } catch(IOException e){
                System.out.println("File not found");
            }
        }

    /**
     *  Metodo que devuelve el tablero
     * @return
     */
    public static Board getBoard() {
        return board;
    }

    /**
     *  Define el valor de la x
     * @param x Coordenada x en el entorno gráfico
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Define el valor de la y
     * @param y Coordenada y en el entorno gráfico
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param g Graphics
     */
    public void draw(Graphics g)
    {
        g.drawImage(this.img,x+15,y+15,null);
    }

    /**
     *
     * @param hasMoved Boolean, si la pieza se ha movido
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     *
     * @return La casilla en la que se encuentra
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Define la casilla en la que se encontrara
     * @param tile La casilla en la que se encontrara
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Devuelve el color de la Pieza
     * @return ColorEnum El color de la Pieza
     */
    public ColorEnum getColor() {
        return color;
    }

    /**
     * Devuelve los movimientos legales e ilegales
     * @return ArrayList con todas las casillas que ve una pieza, movimientos legales y ilegales
     */
    public abstract ArrayList<Tile> getMoves();
    public abstract String getImage();

    /**
     * Devuelve los movimientos legales
     * @return ArrayList con los movimientos legales de una casilla
     */
    public ArrayList<Tile> getLegalMoves()
    {

        ArrayList<Tile> legalMoves = new ArrayList<Tile>();
        ArrayList<Tile> moves = getMoves();
        for(Tile newTile:moves)
        {
            Tile oldTile = tile;
            oldTile.setPieza(null);
            oldTile.setOccupied(false);
            Pieza oldPieza = newTile.getPieza();
            move(newTile);
            if(!CheckDetector.detectCheck(board,color.changeColor()))
            {
                legalMoves.add(newTile);
            }
            tile.setPieza(null);
            tile.setOccupied(false);
            setTile(oldTile);
            oldTile.setPieza(this);
            if(oldPieza != null) {
                oldPieza.setTile(newTile);
                newTile.setPieza(oldPieza);
                board.getPiezas().add(oldPieza);
            }

        }
            return legalMoves;
        }

        public  boolean move(Tile newTile)
        {
            return move(newTile,false);
        }

    /**
     * Mueve una pieza a la nuevacasilla si es un la cond es true
     * @param newTile La casilla a la que se quiere mover
     * @param cond Si es un movimiento real (verdadero) o se estan calculando los movimientos legales (falso)
     * @return Devuelve si se ha conseguido mover la pieza
     */
    public boolean move(Tile newTile,boolean cond){
        if(newTile.isOccupied())
            board.getPiezas().remove(newTile.getPieza());
        tile.setOccupied(false);
        setTile(newTile);
        newTile.setPieza(this);
        newTile.setOccupied(true);
        return true;
    }

    /**
     *  Consigue el numero de la casilla
     * @return El numero de la casilla [0-63]
     */

    public List<Integer> getTileNum()
    {
        List<Integer> moves = new ArrayList<Integer>();
        for(Tile tile:getLegalMoves())
            moves.add(tile.getPosition());

        return moves;
    }

    /**
     * En caso de que se utilice la interfaz 2D con el click y drag, esto devuelve las piezas
     * a la casilla correspondiente
     */
    public void restore()
    {
        x = tile.getX();
        y = tile.getY();
    }

    /**
     * Devuelve el sprite 2D de la pieza
     * @return Sprite 2D de la pieza
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * Hace un set de el tablero
     * @param board el tablero
     */
    public void setBoard(Board board) {
        Pieza.board = board;
        board.addPieza(this);
    }

    /**
     * Las casillas disponiles en la misma linea horizontal
     * @return las casillas disponibles
     */
    protected ArrayList<Tile> getHorizontal()
    {
        return getHorizontal(board.getBoard());
    }

    /**
     *
     * @param board El tablero que se quiere usar
     * @return Las casillas disponibles
     */
    protected ArrayList<Tile> getHorizontal(Tile[] board)
    {
        int position = tile.getPosition();
        ArrayList<Tile> moves = new ArrayList<Tile>();
        IntStream.rangeClosed(-7,7)
                .filter(num -> (num + position < 64 && num+ position >= 0))
                .filter(num -> sameFile(position+num))
                .filter(num -> accesible(position,position,num+position,false,1))
                .forEach(num->moves.add(board[num+position]));
    return moves;
    }

    /**
     * Comprueba si dos casilla estan en la misma horizontal
     * @param newPosition las posicion de la nueva casilla
     * @return verdadero si estan en la misma horizontal
     */
    private boolean sameFile(int newPosition)
    {
        return Math.round(newPosition / 8) == Math.round(tile.getPosition() / 8);
    }

    /**
     * Comprueba si las casillas dadas estan en la misma horizontal
     * @param tile1 Primera casilla
     * @param tile2 Segunda casilla
     * @return Verdadero si estan en la misma horizontal
     */

    private boolean sameFile(Tile tile1,Tile tile2)
    {
        return Math.round(tile1.getPosition() / 8) == Math.round(tile2.getPosition() / 8);
    }

    /**
     * Devuelve si una casilla puede llegar a una posicion
     * @param original La posicion original
     * @param position La posicion que se esta calculando
     * @param newPosition La posicion a la que se quiere llegar
     * @param prev_occupied Si la casilla que se ha mirado anteriormente estaba ocupada
     * @param increment Si se mueve en horizontal 1, en vertical 8, diagonales 7 y 9
     * @return Si la casilla es accesible
     */
    private boolean accesible(int original, int position, int newPosition, boolean prev_occupied, int increment) {
        return accesible(original,position,newPosition,prev_occupied,board.getBoard(),increment);
    }

    /**
     *
     * @param original La posicion original
     * @param position La posicion que se esta calculando
     * @param newPosition La posicion a la que se quiere llegar
     * @param prev_occupied Si la casilla previa estaba ocupada
     * @param board El tablero en el que se calcula
     * @param increment Si se mueve en horizontal 1, en vertical 8, diagonales 7 y 9
     * @return Si la casilla es accesible
     */
    private boolean accesible(int original, int position, int newPosition, boolean prev_occupied, Tile[] board, int increment)
    {
        if (prev_occupied) return false;
        boolean nowOcuppied = original != position && board[position].isOccupied();

        if (newPosition == position)
        {
            // No se puede comer a piezas de su mismo color
            return  !board[position].isOccupied() || !(board[position].getPieza().getColor() ==
                    board[original].getPieza().getColor());

        }

        // Moverse A la izquierda
        else if (newPosition < position)
        {
            return accesible(original,position-increment,newPosition,nowOcuppied,increment);
        }
        // Moverse a la derecha
        else{
            return accesible(original,position+increment,newPosition,nowOcuppied,increment);
        }
    }

    /**
     *
     * @return Las casilla en la misma horizontal que la pieza
     */
    protected ArrayList<Tile> getVertical(){
        return getVertical(board.getBoard());
    }

    /**
     *
     * @param board El tablero a usar
     * @return Las casillas en la misma horizontal
     */
    protected ArrayList<Tile> getVertical(Tile board[])
    {
        int position = tile.getPosition();
        ArrayList<Tile> moves = new ArrayList<Tile>();
                IntStream.rangeClosed(-56,56/9+1)
                        .map(x->x*8)
                        .filter(num -> (num + position < 64 && num+ position >= 0))
                        .filter(num -> accesible(position,position,num+position,false,8))
                        .forEach(num->moves.add(board[num+position]));
        return moves;
    }

    /**
     *
     * @return Las casillas en la misma diagonal que la pieza
     */
    protected ArrayList<Tile> getDiagPrincipal() {
        return getDiagPrincipal(board.getBoard());
    }

    /**
     *
     * @param board El tablero
     * @return Las casillas en la misma diagonal que la pieza
     */
    protected ArrayList<Tile> getDiagPrincipal(Tile board[])
    {
        int position = tile.getPosition();
        ArrayList<Integer> tiles = new ArrayList<Integer>(
                IntStream.rangeClosed(-49,49/7+1)
                        .map(x->x*7)
                        .filter(num -> (num + position < 64 && num+ position >= 0))
                        .filter(num -> accesible(position,position,num+position,false,7))
                        .boxed()
                        .collect(Collectors.toList()));

        tiles.sort(Comparator.comparingInt(Math::abs));
        ArrayList<Tile> moves = new ArrayList<Tile>();
        for (int casilla:tiles)
                if (board[casilla+position].getColor() == tile.getColor() && casilla+position != 0)
                    moves.add(board[casilla + position]);
        return moves;
    }

    /**
     *
     * @return Las casillas en la misma diagonal secundaria que la pieza
     */
    protected ArrayList<Tile> getDiagSec()
    {
       return getDiagSec(board.getBoard());
    }

    /**
     *
     * @param board El tablero que se esta utilizando
     * @return Las casillas que estan en la misma diagonal secundaria que la pieza
     */
    protected ArrayList<Tile> getDiagSec(Tile board[])
    {
        int position = tile.getPosition();
        ArrayList<Integer> tiles = new ArrayList<Integer>(
                IntStream.rangeClosed(-63,63/9+1)
                        .map(x->x*9)
                        .filter(num -> (num + position < 64 && num+ position >= 0))
                        .filter(num -> accesible(position,position,num+position,false,9))
                        .boxed()
                        .collect(Collectors.toList()));

        tiles.sort(Comparator.comparingInt(Math::abs));
        ArrayList<Tile> moves = new ArrayList<Tile>();
        for (int casilla:tiles)
            if (board[casilla+position].getColor() == tile.getColor() && casilla+position != 7)
                moves.add(board[casilla + position]);
        return moves;
    }

    /**
     *
     * @return Las posiciones desde la vista de un caballo
     */
    protected ArrayList<Tile> getKnight()
    {
        return getKnight(board.getBoard());
    }

    /**
     *
     * @param board El tablero
     * @return Las posiciones desde la vista de un caballo
     */
    protected ArrayList<Tile> getKnight(Tile board[])
    {
        ArrayList<Tile> moves = new ArrayList<Tile>();
        ArrayList<Integer> possibilities = new ArrayList<Integer>(Arrays.asList(-17,-10,+6,+15,+17,+10,-6,-15));
        int position = tile.getPosition();
        possibilities.stream()
                .map(x->x+position)
                .filter(x -> (x < 64 && x >= 0))
                .filter(x->board[x].getColor()!=board[position].getColor())
                .filter(x->!(board[x]).isOccupied()||
                        board[x].getPieza().getColor()!=board[position].getPieza().getColor())
                .forEach(x->moves.add(board[x]));
        return moves;
    }

    /**
     *
     * @param o El objeto que se quiere comparar
     * @return Si dos piezas son la misma (misma casilla y mismo toString)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pieza pieza = (Pieza) o;
        return pieza.toString().equals(toString()) && pieza.getTile().getPosition() == tile.getPosition();
    }

}



