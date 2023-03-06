package rendering.util;

import data.Data;

import java.awt.Color;

public class Clickable {

    public int pos = 0;
    public Color color = Color.WHITE;

    public void click() {
        //System.out.println(pos);
        Data.board.selectTile(pos);
    }

    public void chooseColor() {}

}
