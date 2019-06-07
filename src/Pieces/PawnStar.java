package Pieces;

import Player.*;
import Utils.*;
import java.util.ArrayList;

public class PawnStar extends Piece {

    public PawnStar(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        //int pawnSRow;
        //boolean firstMove;
        int mul;
        int moves = 0;
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        //to locally store the moves (As JAVA manipulates objects by Reference)
        Integer[][] toPos = new Integer[64][2];
        if (m_color == Util.Color.WHITE) {
            //pawnSRow = Util.START_ROW + 1;          //1
            mul = 1;
        }
        else {
            //pawnSRow = Util.NUM_COLS - 2;           //6
            mul = -1;
        }
        //Checking both diagonal moves if opponent piece is located there
        for (int j = -1; j < 2; j = j + 2) {
            if (checkPieceAtLocation(m_x + mul, m_y + j, board)) {
                if (checkOpponentPiece(m_x + mul, m_y + j, board)) {
                    //int[] toPos = {m_x + mul,  m_y + j};
                    toPos[moves][0] = m_x + mul;
                    toPos[moves][1] = m_y + j;
                    listMoves.add(toPos[moves]);
                    moves++;
                }
            }
        }

        //check if the next block has a piece
        if (!checkPieceAtLocation(m_x + mul, m_y, board)) {
            //int[] toPos = {m_x + mul, m_y};
            toPos[moves][0] = m_x + mul;
            toPos[moves][1] = m_y;
            listMoves.add(toPos[moves]);
            moves++;
        }

        //check if the next to next block has a piece (IT jumps if a block on the next block)
        if (!checkPieceAtLocation(m_x + 2 * mul, m_y, board)) {
            //int[] toPos = {m_x + mul, m_y};
            toPos[moves][0] = m_x + 2 * mul;
            toPos[moves][1] = m_y;
            listMoves.add(toPos[moves]);
            moves++;
        }

        return listMoves;
    }
}
