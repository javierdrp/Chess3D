package chess.util;

import chess.piezas.Pawn;
import chess.piezas.Pieza;
import chess.ui.PromotionDialog;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

public class PromotionThread extends Thread {
    private Pawn peon;
    public boolean done;
    public boolean isFinished;
    public Pieza nuevaPieza;
    CountDownLatch latch;
    private PromotionDialog promotionDialog;

    public PromotionThread(Pawn peon,CountDownLatch cdl, PromotionDialog pd)
    {
        this.peon = peon;
        isFinished = false;
        latch = cdl;
        this.promotionDialog = pd;
    }

    @Override
    public void run() {
         done = false;
        boolean hasSelected = false;

       /* try {
                System.out.println("hOLA");
                EventQueue.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        promotionDialog = new PromotionDialog(peon);
                        System.out.println("Hola");
                    }
                });

        } catch (InterruptedException e) {
             e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/

        while(!hasSelected)
        {
            hasSelected = promotionDialog.hasSelected;
        }
        nuevaPieza = promotionDialog.getPieza();
        promotionDialog.delete();
        latch.countDown();
    }
}
