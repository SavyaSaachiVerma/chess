package Pieces;

import Player.*;
import Utils.*;

import java.util.ArrayList;

public class King extends Piece {

    public King(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        moves = 0;
        Integer[][] pos = new Integer[10][2];
        for (int iterRow = -1; iterRow <= 1; iterRow++){
            for( int iterCol = -1; iterCol <=1; iterCol++){
                if ((iterRow == 0)  && (iterCol == 0) ){
                    continue;
                }
                pos[moves][0] = curX + iterRow;
                pos[moves][1] = curY + iterCol;
                // if the spot is empty
                if(!checkPieceAtLocation(pos[moves][0], pos[moves][1], board)){
                    if(pos[moves][0] >= 0 && pos[moves][0] <= 7 && pos[moves][1] >= 0 &&pos[moves][0] <= 7 ) {
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
        return listMoves;
    }
}
