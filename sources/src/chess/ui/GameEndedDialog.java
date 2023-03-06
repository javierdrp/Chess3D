package chess.ui;

import chess.util.ColorEnum;
import data.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEndedDialog extends JDialog {
    public static final int DRAW = 0;
    public static final int WIN = 1;
    String message;
    public GameEndedDialog(int state, ColorEnum winner)
    {
        this.message = state == DRAW
                ?"Game ended in a draw"
                : winner.toString() + " Won the game";

        this.setLayout(new BorderLayout());

        this.add(new JLabel(message),BorderLayout.NORTH);
        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Data.board = new Board();
                Data.game = Data.board.toString();
                JVentana.setBoard(Data.board);
                Data.board.repaint();
                GameEndedDialog.this.dispose();
            }
        });

        this.add(btnNewGame,BorderLayout.SOUTH);
        this.setSize(100,100);
        this.setVisible(true);
    }
}
