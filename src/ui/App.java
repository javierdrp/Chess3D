package ui;

import chess.ui.JVentana;
import rendering.ui.Display;

public class App {

    public static void main(String[] args) {
        new JVentana();
        // create display and camera
        Display display = new Display("3D Renderer", 1000, 1000);//Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        display.start();
    }
}
