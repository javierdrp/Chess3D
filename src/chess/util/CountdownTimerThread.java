package chess.util;

import chess.ui.Board;
import data.Data;

public  class CountdownTimerThread extends  Thread {
    private int time; // Tiempo en segundos
    public String label;
    private Board board;

    public CountdownTimerThread(int time, String label, Board board){
        this.time = time;
        this.label = label;
        this.board = board;


    }
    @Override
    public void run()
    {
        while (time > 0){
            try {
                currentThread().sleep(1000);
                time--;
            }catch (Exception e)
            {

            }
            board.updateTime();

        }
    }

    @Override
    public String toString(){
        int segundos = time % 60;
        int minutos = time / 60;
        String s = minutos + ":" + segundos;
        return s;
    }
}
