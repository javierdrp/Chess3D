package chess.ui;

import chess.piezas.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import chess.util.ColorEnum;
import chess.util.CountdownTimerThread;
import chess.util.GameReader;
import data.Data;

public class Board extends JPanel implements MouseMotionListener,MouseListener {
    private Tile[] board;
    private Pieza piezaActual;
    public ArrayList<Pieza> piezas;
    private int nuevaX;
    private int nuevaY;
    private boolean selected;
    private ColorEnum turn;
    public static CountdownTimerThread whiteTimer;
    public static CountdownTimerThread blackTimer;
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
        whiteTimer = new CountdownTimerThread(300,Data.whiteTime,this);
        blackTimer = new CountdownTimerThread(300,Data.whiteTime,this);
        whiteTimer.start();
        blackTimer.start();
        blackTimer.suspend();
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
    public  void updateTime()
    {
        Data.whiteTime = whiteTimer.toString();
        Data.blackTime = blackTimer.toString();
    }


    private void turnSwap(){
        turn = ColorEnum.BLACK.changeColor(turn);
        try {
            if (turn == ColorEnum.WHITE) {
                whiteTimer.resume();
                blackTimer.suspend();
            }else{
                blackTimer.resume();
                whiteTimer.suspend();
            }
        }catch (Exception e)
        {

        }
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
                for(Tile tiles:piezaActual.getMoves())
                {
                    tiles.setShade(Color.red);
                }
            }
            else if (selected == true && piezaActual.getColor() == turn && piezaActual.getLegalMoves().contains(tile)){
                 if (piezaActual.move(tile,true))
                    turnSwap();
                    piezaActual.setHasMoved(true);
                    Data.selected = -1;
                    selected = false;
                    Data.available = new ArrayList<Integer>();
                    restore();
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

    public void refreshPiezas(){
        piezas = new ArrayList<Pieza>();
        for(Tile tile:board)
        {
            if(tile.isOccupied())
                piezas.add(tile.getPieza());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile tile = (Tile) this.getComponentAt(new Point(e.getX(),e.getY()));
        selectTile(tile.getPosition());
        if(tile.isOccupied())
        {
            for(Tile a:tile.getPieza().getLegalMoves())
                a.setShade(Color.PINK);
        }
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

    public List<Tile> getAllMoves(ColorEnum color){
        ArrayList<Tile> posibles = new ArrayList<Tile>();
        for(Pieza pieza:piezas){
          if (pieza.getColor() == color)
                 posibles.addAll(pieza.getMoves());
        }

       return posibles;
    }


    @Override
    public String toString() {
        return GameReader.boardToFEN(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(JVentana.WIDTH-200,JVentana.HEIGHT-200);
    }

    public void loadGame(String tablero)
    {
        GameReader.readFEN(this,tablero);
    }
}
