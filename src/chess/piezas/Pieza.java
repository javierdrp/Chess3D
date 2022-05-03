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

    public static Board getBoard() {
        return board;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g)
    {
        g.drawImage(this.img,x+15,y+15,null);
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
    public ColorEnum getColor() {
        return color;
    }
    public abstract ArrayList<Tile> getMoves();
    public abstract String getImage();


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
            if(!CheckDetector.detectCheck(board,ColorEnum.BLACK.changeColor(color)))
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
    public boolean move(Tile newTile,boolean cond)
    {
        if(newTile.isOccupied())
            board.getPiezas().remove(newTile.getPieza());
        tile.setOccupied(false);
        setTile(newTile);
        newTile.setPieza(this);
        newTile.setOccupied(true);
        return true;
    }

    public List<Integer> getTileNum()
    {
        List<Integer> moves = new ArrayList<Integer>();
        for(Tile tile:getLegalMoves())
            moves.add(tile.getPosition());

        return moves;
    }

    public void restore()
    {
        x = tile.getX();
        y = tile.getY();
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setBoard(Board board) {
        Pieza.board = board;
        board.addPieza(this);
    }


    protected ArrayList<Tile> getHorizontal()
    {
        return getHorizontal(board.getBoard());
    }
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

    private boolean sameFile(int newPosition)
    {
        return Math.round(newPosition / 8) == Math.round(tile.getPosition() / 8);
    }

    private boolean sameFile(Tile tile1,Tile tile2)
    {
        return Math.round(tile1.getPosition() / 8) == Math.round(tile2.getPosition() / 8);
    }

    private boolean accesible(int original, int position, int newPosition, boolean prev_occupied, int increment) {
        return accesible(original,position,newPosition,prev_occupied,board.getBoard(),increment);
    }
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
    protected ArrayList<Tile> getVertical(){
        return getVertical(board.getBoard());
    }
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

    protected ArrayList<Tile> getDiagPrincipal() {
        return getDiagPrincipal(board.getBoard());
    }

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

    protected ArrayList<Tile> getDiagSec()
    {
       return getDiagSec(board.getBoard());
    }
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

    protected ArrayList<Tile> getKnight()
    {
        return getKnight(board.getBoard());
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pieza pieza = (Pieza) o;
        return pieza.toString().equals(toString()) && pieza.getTile().getPosition() == tile.getPosition();
    }

}



