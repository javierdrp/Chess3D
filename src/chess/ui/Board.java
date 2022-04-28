package chess.ui;

import chess.piezas.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import data.Data;

public class Board extends JPanel implements MouseMotionListener,MouseListener {
    private Tile[] board;
    private Pieza piezaActual;
    public ArrayList<Pieza> piezas;
    private int nuevaX;
    private int nuevaY;
    private boolean selected;
    private ColorEnum turn;

    public Board() {
        this.setSize(400,400);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.board = new Tile[64];
        this.setLayout(new GridLayout(8,8));
        piezas = new ArrayList<Pieza>();
        turn = ColorEnum.WHITE;
        initBoard();
        loadGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        repaint();


    }

    public void addPieza(Pieza pieza)
    {
        piezas.add(pieza);
    }

    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public void initBoard()
    {
        boolean white = true;
        int color;
        Color shade;
        for (int j =0;j < 8; j++)
        {
            for (int i = 0; i < 8; i++) {
                if (white)
                {
                    color = 0; // white
                    shade = Color.WHITE;//white
                }
                else {
                    color = 1; // black
                    shade = Color.GRAY;
                }
                white = !white;
                board[i+j*8] = new Tile(false,color,i+j*8);
                this.add(board[i+j*8]);
            }
            white = !white;
    }
    }

    private void turnSwap()
    {
        turn = ColorEnum.BLACK.changeColor(turn);
    }

    public Tile[] getBoard() {
        return board;
    }

    public Tile getTile(int index)
    {
        return board[index];
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for(Tile tile:board){
            //System.out.println(tile);
            tile.paintComponent(g);

        }


        if(piezaActual != null){
            piezaActual.setX(nuevaX);
            piezaActual.setY(nuevaY);
            piezaActual.draw(g);
        }
    }

    public void restore()
    {
        for(Tile tile:board)
            tile.restoreColor();
    }

    public void selectTile(int num)
    {
            Tile tile = board[num];
            if(tile.isOccupied() && tile.getPieza().getColor() == turn)
            {
                piezaActual = tile.getPieza();
                Data.selected = num;
                Data.available = piezaActual.getTileNum();
                restore();
                tile.setShade(Color.ORANGE);
                selected = true;
            }
            else if (selected == true && piezaActual.getColor() == turn){
                 if (piezaActual.move(tile))
                {
                    turnSwap();
                    Data.selected = -1;
                    selected = false;
                    Data.available = new ArrayList<Integer>();
                    restore();
                }
            }
            repaint();
            Data.game=toString();


    }


    @Override
    public void mouseDragged(MouseEvent e) {
     /*   nuevaX = e.getX()-30;
        nuevaY = e.getY()-30;
        repaint();*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile tile = (Tile) this.getComponentAt(new Point(e.getX(),e.getY()));
        selectTile(tile.getPosition());
        repaint();

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        /*if (piezaActual !=null) {
            Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));
            if (true)
                piezaActual.move(tile);
            else
                piezaActual.restore();
            piezaActual = null;
            restore();
            repaint();
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
       /* Tile tile = (Tile) this.getComponentAt(new Point(e.getX(),e.getY()));
        if (tile.isOccupied()) {
            piezaActual = tile.getPieza();
            ArrayList<Tile> possible = piezaActual.getMoves();
           // System.out.println(piezaActual.getMoves());
            for(Tile t:possible)
                t.setShade(Color.RED);
            repaint();
        }

        else
            piezaActual = null;*/
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Collection getAllMoves(ColorEnum color){
        ArrayList<Tile> posibles = new ArrayList<Tile>();
        //System.out.println(getPiezas());
        for(Pieza pieza:piezas){
          if (pieza.getColor() == color && !(pieza instanceof King))
                 posibles.addAll(pieza.getMoves());
                //System.out.println(pieza);
        }
        //System.out.println(posibles);

       return posibles;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<8;i++){
            int counter = 0;
            for(int j=0;j<8;j++){
                if (board[8*i+j].isOccupied())
                {
                    if(counter!=0){
                        sb.append(counter);
                        counter = 0;
                    }
                    sb.append(board[8*i+j].getPieza().toString());
                }
                else{
                    counter++;
                }
            }
            if(counter!=0)
                sb.append(counter);
            counter = 0;
            sb.append("/");
        }
        return sb.toString();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(JVentana.WIDTH-200,JVentana.HEIGHT-200);
    }

    public void loadGame(String tablero)
    {

        /* Utilizamos la nomenclatura FEN para cargar y guardar partidas */
        int letra=-1;
        String[] lineas = tablero.split("/");
        Pieza pieza;
        for(int i= 0;i<8;i++)
        {
            String linea = lineas[i];

            for(int j=0;j<8;j++)
            {
                char caracter;
                letra++;

                if(letra >= linea.length())
                    caracter = '/';
                else
                    caracter = linea.charAt(letra);

                pieza = null;
                int num = i*8+j;
                switch (caracter)
                {

                    case 'p': pieza = new Pawn(ColorEnum.BLACK,board[num]); break;
                    case 'r': pieza = new Rook(ColorEnum.BLACK,board[num]); break;
                    case 'n': pieza = new Knight(ColorEnum.BLACK,board[num]); break;
                    case 'b': pieza = new Bishop(ColorEnum.BLACK,board[num]); break;
                    case 'q': pieza = new Queen(ColorEnum.BLACK,board[num]); break;
                    case 'k': pieza = new King(ColorEnum.BLACK,board[num]); break;
                    case 'P': pieza = new Pawn(ColorEnum.WHITE,board[num]); break;
                    case 'R': pieza = new Rook(ColorEnum.WHITE,board[num]); break;
                    case 'N': pieza = new Knight(ColorEnum.WHITE,board[num]); break;
                    case 'B': pieza = new Bishop(ColorEnum.WHITE,board[num]); break;
                    case 'Q': pieza = new Queen(ColorEnum.WHITE,board[num]); break;
                    case 'K': pieza = new King(ColorEnum.WHITE,board[num]); break;
                    case '/': j=8; break;
                    case '1': break;
                    case '2': j++; break;
                    case '3': j+=2; break;
                    case '4': j+=3; break;
                    case '5': j+=4; break;
                    case '6': j+=5; break;
                    case '7': j+=6; break;
                    case '8': j+=7; break;
                    default:
                        System.out.println("ERROR");
                }
                if(pieza!=null)
                {
                    //System.out.println(linea.charAt(letra));
                    board[num].setPieza(pieza);
                    pieza.setBoard(this);
                }
                //System.out.println(j);

            }
            letra =-1;
        }
    }
}
