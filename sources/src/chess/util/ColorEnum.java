package chess.util;

public enum ColorEnum {
    WHITE(-1),
    BLACK(1);
    int value;
    ColorEnum(int value)
    {
        this.value = value;
    }
    public ColorEnum changeColor(ColorEnum color)
    {
        return (WHITE  == color)
                ?BLACK
                :WHITE;
    }

    public ColorEnum changeColor()
    {
        return changeColor(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return ( WHITE == this)
                ?"White"
                :"Black";
    }
}
