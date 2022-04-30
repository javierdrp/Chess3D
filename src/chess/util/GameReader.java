package chess.util;

import chess.piezas.*;
import chess.ui.Board;
import chess.ui.ColorEnum;
import chess.ui.Tile;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.toLowerCase;

public abstract class GameReader {
    /*
    Existen numerosas nomenclaturas para representar una partida de ajedrez, se usará la nomenclatura FEN
    pero en caso de querer leer diferentes nomenclaturas se podrían añadir métodos en esta clase.
    */
    public static void readFEN(Board boards, String tablero)
    {
        Tile[] board = boards.getBoard();
            int letra=-1;
            String[] lineas = tablero.split("/");
            Pieza pieza;
            ColorEnum color;
            for(int i= 0;i<8;i++)
            {
                String linea = lineas[i];

                for(int j=0;j<8;j++)
                {
                    char caracter;
                    letra++;

                    if(letra >= linea.length())
                        caracter = '/';
                    else
                        caracter = linea.charAt(letra);
                    if(isLowerCase(caracter))
                        color = ColorEnum.BLACK;
                    else
                        color = ColorEnum.WHITE;

                    caracter = Character.toLowerCase(caracter);
                    pieza = null;
                    int num = i*8+j;
                    switch (caracter)
                    {

                        case 'p': pieza = new Pawn(color,board[num]); break;
                        case 'r': pieza = new Rook(color,board[num]); break;
                        case 'n': pieza = new Knight(color,board[num]); break;
                        case 'b': pieza = new Bishop(color,board[num]); break;
                        case 'q': pieza = new Queen(color,board[num]); break;
                        case 'k': pieza = new King(color,board[num]); break;
                        case '/': j=8; break;
                        case '1': break;
                        case '2': j++; break;
                        case '3': j+=2; break;
                        case '4': j+=3; break;
                        case '5': j+=4; break;
                        case '6': j+=5; break;
                        case '7': j+=6; break;
                        case '8': j+=7; break;
                        default:
                            System.out.println("ERROR");
                    }
                    if(pieza!=null)
                    {
                        board[num].setPieza(pieza);
                        pieza.setBoard(boards);
                    }
                }
                letra =-1;
            }
        }

        public static String boardToFEN(Board boards)
        {
            Tile[] board = boards.getBoard();
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<8;i++){
                int counter = 0;
                for(int j=0;j<8;j++){
                    if (board[8*i+j].isOccupied())
                    {
                        if(counter!=0){
                            sb.append(counter);
                            counter = 0;
                        }
                        sb.append(board[8*i+j].getPieza().toString());
                    }
                    else{
                        counter++;
                    }
                }
                if(counter!=0)
                    sb.append(counter);
                counter = 0;
                sb.append("/");
            }
            return sb.toString();
        }
    }
