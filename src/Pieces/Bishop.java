package Pieces;

import Player.*;
import Utils.*;
import java.util.ArrayList;


public class Bishop extends Piece {

    public Bishop(int x, int y, Player player, Util.Type type) {
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        moves = 0;
        // moving to upper left corner
        int iterRow = -1;
        int iterCol = -1;
        //Integer[] pos = {curX + iterRow, curY + iterCol};
        Integer[][] pos = new Integer[64][2];
        pos[moves][0] = curX + iterRow;
        pos[moves][1] = curY + iterCol;
        while ((pos[moves][0] >= 0) && (pos[moves][1] >= 0)) {
            if (noMoreMovesPossible(board, listMoves, iterRow, pos, iterCol)) break;
        }
        // mobing to upper right corner
        iterRow = -1;
        iterCol = 1;
        pos[moves][0] = curX + iterRow;
        pos[moves][1] = curY + iterCol;
        while ((pos[moves][0] >= 0) && (pos[moves][1] <= 7)) {
            if (noMoreMovesPossible(board, listMoves, iterRow, pos, iterCol)) break;
        }
        // moving to lower left corner
        iterRow = 1;
        iterCol = -1;
        pos[moves][0] = curX + iterRow;
        pos[moves][1] = curY + iterCol;
        while ((pos[moves][0] <= 7) && (pos[moves][1] >= 0)) {
            if (noMoreMovesPossible(board, listMoves, iterRow, pos, iterCol)) break;
        }
        // moving to lower right coner
        iterRow = 1;
        iterCol = 1;
        pos[moves][0] = curX + iterRow;
        pos[moves][1] = curY + iterCol;
        while ((pos[moves][0] <= 7) && (pos[moves][1] <= 7)) {
            if (noMoreMovesPossible(board, listMoves, iterRow, pos, iterCol)) break;
        }
        return listMoves;
    }

    private boolean noMoreMovesPossible(Piece[][] board, ArrayList<Integer[]> listMoves, int iterRow, Integer[][] pos, int iterCol) {
        if (!checkPieceAtLocation(pos[moves][0], pos[moves][1], board)) {
            listMoves.add(pos[moves]);
            moves++;
            // increment position to be checked
            pos[moves][0] = pos[moves - 1][0] + iterRow;
            pos[moves][1] = pos[moves - 1][1] + iterCol;
            return false;
        }
        else {
            if (checkOpponentPiece(pos[moves][0], pos[moves][1], board)) {
                listMoves.add(pos[moves]);
                moves++;
            }
            return true;
        }
    }


}


