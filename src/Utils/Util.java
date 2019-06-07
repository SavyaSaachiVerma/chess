package Utils;

import java.util.ArrayList;

public class Util {

    public enum Type{
        BISHOP, KING, ROOK, QUEEN, PAWN, KNIGHT, PSTAR, KNOOK;
    }

    public enum Color{
        WHITE, BLACK;
    }

    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;
    public static final int NUM_PAWNS = 8;
    public static final int START_ROW = 0;
    public static final int START_COL = 0;
    public static final int PLAYER_ROWS = 2;
    public static final int END_EMPTY_ROWS = 6;
    public static final int START_EMPTY_ROWS = 2;

    public static boolean containsPos(ArrayList<Integer[]> listMoves, Integer[] finPos ){
        for (int i = 0; i< listMoves.size(); i++){
            Integer[] pos = listMoves.get(i);
            if( finPos[0] == pos[0] && finPos[1] == pos[1])
                return true;
        }
        return false;
    }

}
