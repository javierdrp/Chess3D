package chess.ui;

import chess.piezas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import data.Data;

public class JVentana extends JFrame {
    public static int HEIGHT = 800;
    public static int WIDTH = 800;
    private JButton btnNewGame;
    private JPanel pnlBoard;
    private JPanel pnlNorte;
    private final int X = 8;
    private final int Y = 8;
    boolean waiting = true;
    private Board board;
    Pieza pieza;
//    public static void main(String[] args) {
//        new JVentana();
//    }

    public JVentana()
    {
        this.setLayout(new BorderLayout());
        //this.setLayout(new GridLayout(3,1));
        //this.add(pnlSur, BorderLayout.SOUTH);

        //this.add(pnlBoard, BorderLayout.CENTER);
        this.setSize(WIDTH,HEIGHT);
        this.setTitle("Ajedrez");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
        //this.add(pnlNorte);
        pnlNorte.setPreferredSize(new Dimension(600,100));
        pnlBoard = new JPanel();
        board = new Board();
        Data.board = board;

        pnlBoard.setLayout(new FlowLayout());
        JPanel pnlEste = new JPanel();
        JPanel pnlOeste = new JPanel();
        //board.setSize(400,400);

        //board.setMinimumSize(new Dimension(400,400));
        //pnlBoard.add(pnlEste);
        pnlBoard.add(board);
        //pnlBoard.add(pnlOeste);
        pnlBoard.setPreferredSize(new Dimension(500,500));
        this.add(pnlBoard);

        this.add(pnlBoard,BorderLayout.CENTER);
        this.add(pnlNorte,BorderLayout.NORTH);
        board.repaint();
        System.out.println(board);
        //board.setSize(400,400);
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board=new Board();
                board.repaint();

            }
        });
        this.setVisible(true);
    }

    void init()
    {
        btnNewGame = new JButton("Nueva Partida");
        pnlNorte = new JPanel(new FlowLayout());
        pnlNorte.add(btnNewGame);
    }


}
