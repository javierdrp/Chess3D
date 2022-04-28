package chess.ui;

public enum ColorEnum {
    WHITE,
    BLACK;


    public ColorEnum changeColor(ColorEnum color)
    {
        return (WHITE  == color)
                ?BLACK
                :WHITE;
    }
}
