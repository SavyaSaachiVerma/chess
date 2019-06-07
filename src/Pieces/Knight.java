package Pieces;

import Player.*;
import Utils.*;

import java.util.ArrayList;
public class Knight extends Piece{

    public Knight(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        moves = 0;
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        Integer[][] pos = new Integer[8][2];
        for (int iterRow = -2; iterRow <= 2; iterRow = iterRow + 1){
            for (int iterCol = -2; iterCol <= 2; iterCol = iterCol + 1){
                if((Math.abs(iterRow) != Math.abs(iterCol) && iterRow != 0 && iterCol != 0)){
                    pos[moves][0] = curX + iterRow;
                    pos[moves][1] = curY + iterCol;
                    // if the spot is empty
                    if(!checkPieceAtLocation(pos[moves][0], pos[moves][1], board)){
                        if(pos[moves][0] >= 0 && pos[moves][0] <= 7 && pos[moves][1] >= 0 && pos[moves][1] <= 7 ) {
                            listMoves.add(pos[moves]);
                            moves++;
                        }
                    }
                    // if the spot contains a piece
                    else{
                        //checking if it's opponents piece or ours. If opponents then add to list else nothing.
                        if(checkOpponentPiece(pos[moves][0], pos[moves][1], board)) {
                            listMoves.add(pos[moves]);
                            moves++;
                        }
                    }
                }
            }
        }
        return  listMoves;
    }
}
