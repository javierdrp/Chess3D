package data;

import chess.ui.Board;
import rendering.model.Board3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * La informacion que se comparte entre el juego y la interfaz grafica.
 * @author Javier Díaz de Rábago, Carlos Marí
 * @version 19/05/2022/A
 */

public class Data {
    public static int selected = -1;
    public static List<Integer> available = new ArrayList<>();
    public static String whiteTime = "00:00";
    public static String blackTime = "00:00";
    public static Board board;
    public static String game = Board3D.newGame;
}
