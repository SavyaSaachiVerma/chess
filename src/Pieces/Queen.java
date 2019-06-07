package Pieces;

import Player.*;
import Utils.*;
import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(int x, int y, Player player, Util.Type type){
        super(x, y, player);
        m_type = type;
        m_color = player.m_color;
    }

    @Override
    public ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board) {
        ArrayList<Integer[]> listMoves = new ArrayList<>();
        //store the queen
        Piece queen = board[curX][curY];
        //replace the queen by a rook
        board[curX][curY] = new Rook(curX, curY, m_player, Util.Type.ROOK);
        listMoves.addAll(board[curX][curY].getPossibleMoves(curX, curY, board));
        //replace queen by a bishop
        board[curX][curY] = new Bishop(curX, curY, m_player, Util.Type.BISHOP);
        listMoves.addAll(board[curX][curY].getPossibleMoves(curX, curY, board));
        board[curX][curY] = queen;
        return listMoves;
    }
}
