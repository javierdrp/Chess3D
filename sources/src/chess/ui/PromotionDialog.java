package chess.ui;

import chess.piezas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * Esta clase define el DialogBox que aparece cuando se corona
 * @author Carlos Marí Noguera
 * @version 19/05/2022/A
 */
public class PromotionDialog extends JDialog {
    JPanel pnlBotones;
    JButton btnQueen;
    JButton btnBishop;
    JButton btnKnight;
    JButton btnRook;
    public boolean hasSelected;
    Pieza pieza;
    Pawn pawn;
    Tile tile;

    /**
     *
     * @param pawn El peon a coronar
     * @param tile La casilla a la que se quiere mover
     */
    public PromotionDialog(Pawn pawn, Tile tile)
    {
        /**
         *
         */
        this.tile = tile;
        this.pawn = pawn;
        hasSelected = false;
        btnQueen = new JButton("Queen");
        btnBishop = new JButton("Bishop");
        btnKnight = new JButton("Knight");
        btnRook = new JButton("Rook");
        pnlBotones = new JPanel();
        pnlBotones.setLayout(new FlowLayout());
        pnlBotones.add(btnQueen);
        pnlBotones.add(btnRook);
        pnlBotones.add(btnKnight);
        pnlBotones.add(btnBishop);


        btnQueen.addActionListener(new ActionListener() {
            /**
             * Cuando se pulsa el boton se elige una reina
             * @param e Accion de pulsar el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                pieza = new Queen(pawn);
                hasSelected = true;
                finish();

            }
        });

        btnRook.addActionListener(new ActionListener() {
            /**
             * Cuando se pulsa el boton se elige una torre
             * @param e Accion de pulsar el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                pieza = new Rook(pawn);
                hasSelected = true;
                finish();
            }
        });

        btnBishop.addActionListener(new ActionListener() {
            /**
             * Cuando se pulsa el boton se elige un alfil
             * @param e Accion de pulsar el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                pieza = new Bishop(pawn);
                hasSelected = true;
                finish();
            }
        });

        btnKnight.addActionListener(new ActionListener() {
            /**
             * Cuando se pulsa el boton se elige un caballo
             * @param e Accion de pulsar el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                pieza = new Knight(pawn);
                hasSelected = true;
                finish();

            }
        });

        this.add(pnlBotones);
        this.setSize(400,100);
        this.setTitle("Promotion");
        this.setVisible(true);
    }

    /**
     * Cierra la ventana
     */
    public void delete()
    {
        dispose();
    }

    /**
     *
     * @return Devuelve la pieza a la que se ha coronado
     */
    public Pieza getPieza()
    {
        return pieza;
    }

    /**
     * Acaba la coronacion a la pieza deseada
     */
    void finish()
    {
        pawn.finishPromotion(pieza,tile);
        delete();
    }


}
