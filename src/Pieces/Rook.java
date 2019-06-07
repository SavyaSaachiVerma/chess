package Pieces;

import Pieces.Piece;

import java.util.ArrayList;
import Player.*;
import Utils.*;

public class Rook extends Piece {

    public Rook(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board){
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        moves = 0;
        Integer[][] pos = new Integer[64][2];
        // traversing row-wise. first up then down.
        for (int iterRow = -1; iterRow <= 1; iterRow = iterRow + 2){
            pos[moves][0] = curX + iterRow;
            pos[moves][1] = curY;
            while((pos[moves][0] >= 0) && (pos[moves][0] <=7) && (pos[moves][1] >= 0) && (pos[moves][1] <=7)){
                if (noMoreMovesPossible(board, listMoves, pos))
                    break;
                pos[moves][0] = pos[moves - 1][0] + iterRow;
                pos[moves][1] = pos[moves - 1][1];
            }
        }
        //traversing column wise. First left then right.
        for (int iterCol = -1; iterCol <= 1; iterCol = iterCol + 2){
            pos[moves][0] = curX;
            pos[moves][1] = curY + iterCol;
            while((pos[moves][0] >= 0) && (pos[moves][0] <=7) && (pos[moves][1] >= 0) && (pos[moves][1] <=7)){
                if (noMoreMovesPossible(board, listMoves, pos))
                    break;
                pos[moves][1] = pos[moves - 1][1] + iterCol;
                pos[moves][0] = pos[moves - 1][0];

            }
        }
        return listMoves;
    }

    private boolean noMoreMovesPossible(Piece[][] board, ArrayList<Integer[]> listMoves, Integer[][] pos) {
        if (!checkPieceAtLocation(pos[moves][0], pos[moves][1], board)) {
            listMoves.add(pos[moves]);
            moves++;
            return false;
        }
        else {
            if (checkOpponentPiece(pos[moves][0], pos[moves][1], board)){
                listMoves.add(pos[moves]);
                moves++;
            }
            return true;
        }
    }
}

