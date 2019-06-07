package Pieces;

import Player.*;
import Utils.*;
import java.util.ArrayList;

public class Knook extends Piece {

    public Knook(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_color = player.m_color;
        m_type = type;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        ArrayList<Integer[]> listMoves = new ArrayList<Integer[]>();
        //store the Knook at current location
        Piece knook = board[curX][curY];
        //replace the Knook by a rook
        board[curX][curY] = new Rook(curX, curY, m_player, Util.Type.ROOK);
        listMoves.addAll(board[curX][curY].getPossibleMoves(curX, curY, board));
        //replace Knook by a Knight
        board[curX][curY] = new Knight(curX, curY, m_player, Util.Type.BISHOP);
        listMoves.addAll(board[curX][curY].getPossibleMoves(curX, curY, board));
        board[curX][curY] = knook;
        return listMoves;
    }

}
