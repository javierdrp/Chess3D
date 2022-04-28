package rendering.model;

import rendering.renderdata.Global;
import rendering.mesh.Mesh3D;
import rendering.model.piece.Bishop3D;
import rendering.model.piece.King3D;
import rendering.model.piece.Knight3D;
import rendering.model.piece.Pawn3D;
import rendering.model.piece.Queen3D;
import rendering.model.piece.Rook3D;
import rendering.util.Util;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board3D {

    private static Tile3D[] tiles = new Tile3D[64];

    private static ChessPiece3D[] allPieces;

    public static final String newGame = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    public static Set<ChessPiece3D> game;
    public static Set<ChessPiece3D> dead;

    public static Map<String, Integer> used;

    public static void prepare() {
        resetUsed();
        allPieces = new ChessPiece3D[]{
                new Rook3D(ChessPiece3D.BLACK), new Knight3D(ChessPiece3D.BLACK), new Bishop3D(ChessPiece3D.BLACK), new Queen3D(ChessPiece3D.BLACK), new King3D(ChessPiece3D.BLACK), new Bishop3D(ChessPiece3D.BLACK), new Knight3D(ChessPiece3D.BLACK), new Rook3D(ChessPiece3D.BLACK),
                new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK), new Pawn3D(ChessPiece3D.BLACK),

                new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE), new Pawn3D(ChessPiece3D.WHITE),
                new Rook3D(ChessPiece3D.WHITE), new Knight3D(ChessPiece3D.WHITE), new Bishop3D(ChessPiece3D.WHITE), new Queen3D(ChessPiece3D.WHITE), new King3D(ChessPiece3D.WHITE), new Bishop3D(ChessPiece3D.WHITE), new Knight3D(ChessPiece3D.WHITE), new Rook3D(ChessPiece3D.WHITE)
        };

        Mesh3D[] meshes = new Mesh3D[64];
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {
                meshes[i*8+j] = Util.readMeshFromObjFile("models/board/tile.obj", ((i+j)%2==0)? Tile3D.WHITE: Tile3D.BLACK);
                meshes[i*8+j].move(0.5 + j-4, -.48, 0.5 + i-4);
            }
        for(int i = 0; i < 64; i++) {
            tiles[i] = new Tile3D(meshes[i], i);
        }
    }

    public static void loadGame(String s) {
        dead = new HashSet<>(32);
        game = new HashSet<>(32);
        dead.addAll(Arrays.asList(allPieces));

        int pos = 0;
        resetUsed();

        for(int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '/':
                case '|':
                    if(!(pos%8 == 0 && s.charAt(i-1) != '|')) pos = pos - pos%8 + 8;
                    break;
                case '-':
                    pos++;
                    break;
                default:
                    try {
                        pos += Integer.parseInt(((Character)s.charAt(i)).toString());
                    }
                    catch(Exception e) {
                        ChessPiece3D piece = getPiece(s.charAt(i));
                        if (piece != null) {
                            piece.moveToTile(pos);
                            game.add(piece);
                            dead.remove(piece);
                        }
                        pos++;
                    }
                    break;
            }
        }

        Global.meshBuffer.clear();
        for(Tile3D tile : tiles) Global.meshBuffer.addMesh(tile.mesh);
        game.stream().map(ChessPiece3D::getMesh).forEach(Global.meshBuffer::addMesh);
        //Global.meshBuffer.addMesh(Util.readMeshFromObjFile("models/board/frame.obj", new Color(0x5B493B)));

        System.out.println(game.size());
        System.out.println(dead.size());
    }

    private static ChessPiece3D getPiece(char c) {
        for(ChessPiece3D piece : allPieces)
        {
            if(piece.name.equals(Character.toString(c) + used.get(Character.toString(c)))) {
                use(Character.toString(c));
                return piece;
            }
        }
        return null;
    }

    public static void resetUsed() {
        used = new HashMap<>();
        for(String k : new String[]{"r","R","k","K","b","B","q","Q","n","N","p","P"})
            used.put(k, 0);
    }

    public static Integer use(String piece) {
        used.replace(piece, used.get(piece) + 1);
        return used.get(piece) - 1;
    }

}
