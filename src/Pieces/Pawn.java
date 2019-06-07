package Pieces;

import Player.*;
import Utils.*;

import java.util.*;

public class Pawn extends Piece{


    public Pawn(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board){
        int pawnRow;
        boolean firstMove;
        int mul;
        int moves = 0;
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        Integer[][] toPos = new Integer[64][2];

        if (m_color == Util.Color.WHITE) {
            pawnRow = 1;
            mul = 1;
        }
        else {
            pawnRow = 6;
            mul = -1;
        }

        if (m_x == pawnRow)
            firstMove = true;
        else
            firstMove = false;
        //Checking wether the pawn is taking its first move.
        if (firstMove) {
            //int[] toPos = {m_x + mul*2, m_y};
            toPos[moves][0] = m_x + mul * 2;
            toPos[moves][1] = m_y;
            //Check if the next two blocks from current position are empty
            if (!(checkPieceAtLocation(toPos[moves][0], toPos[moves][1], board)) &&
                    !(checkPieceAtLocation(m_x + mul, m_y, board)) ){
                listMoves.add(toPos[moves]);
                moves++;
            }
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

        return listMoves;
    }

}
