package Pieces;

import Player.*;
import Utils.*;

import java.util.*;
import java.lang.*;
public abstract class Piece {

    public int m_x, m_y;
    public Player m_player;
    public Util.Type m_type;
    public Util.Color m_color;
    public int moves;
    /**
     * Constructor for Pieces.Piece
     * @param x     x location of the piece
     * @param y     y location of the piece
     * @param player    the player whom this piece belongs to
     */
    public Piece(int x, int y, Player player){
        m_x = x;
        m_y = y;
        m_player = player;
    }

    /**
     * Function to get the player
     * @return Player.Player
     */
    public Player getPlayer(){
        return m_player;
    }

    /**
     * Function to get all the possible positions where this piece can end up in 1 move
     * @param curX      current X position of the piece
     * @param curY      current Y position of the piece
     * @return          2-D intger array specifying new x and y positions. Along the rows possible positions.
     */
    public abstract ArrayList<Integer[]> getPossibleMoves(int curX, int curY, Piece[][] board);

    /**
     * Checks if the move specfied by the player is valid or not
     * @param curX the current row of the piece
     * @param curY the current column of the piece
     * @param finalX the destination row of the piece
     * @param finalY the destination column of the piece
     * @param opponent player
     * @param board chessboard
     * @return status of the move
     */
     public boolean isValidMove(int curX, int curY, int finalX, int finalY, Player opponent, Piece[][] board){
        if((curX == finalX) && (curY == finalY))
            return false;
        if ((finalX >= Util.NUM_ROWS) || (finalX < Util.START_ROW) || (finalY >= Util.NUM_COLS) || (finalY < Util.START_COL))
            return false;
        ArrayList<Integer[]> listMoves = getPossibleMoves(curX, curY, board);
        //printMovesList(listMoves);
        Integer[] arr = new Integer[2];
        arr[0] = finalX;
        arr[1] = finalY;
        if (Util.containsPos(listMoves, arr)) {
            Piece piece = board[finalX][finalY];
            updatePosition(curX, curY, finalX, finalY, board);
            //check if after move king gets checked
            if (m_player.isKingChecked(opponent, board)){
                //revert back to original position
                revertPosition(curX, curY, finalX, finalY, board, piece);
                return false;
            }
            //if final move is in possible moves and king will not get checked
            return true;
        }
        //if final position is not in possible moves
        return false;
    }

    /**
     * checks if a particular location at the board contains a piece
     * @param x the row to be checked
     * @param y the column to be checked
     * @param board board
     * @return true or false
     */
    public boolean checkPieceAtLocation(int x, int y, Piece[][] board){
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7){
            if (board[x][y] != null)
                return true;
        }
        return false;
    }

    /**
     * check is a particular position on the board has an opponents piece
     * @param x the location
     * @param y the location
     * @param board board
     * @return true or false
     */
    public boolean checkOpponentPiece (int x, int y, Piece[][] board){
        if(board[x][y].m_color == m_color)
            return false;
        return true;
    }

    /**
     * Updates the position of the piece
     * @param curX current row
     * @param curY current column
     * @param finalX destination row
     * @param finalY destination column
     * @param board board
     */
    public void updatePosition(int curX, int curY, int finalX, int finalY, Piece[][] board){
        m_x = finalX;
        m_y = finalY;
        board[finalX][finalY] = this;
        board[curX][curY] = null;
    }

    /**
     * revert back to previous position
     * @param curX current row
     * @param curY current column
     * @param finalX destination row
     * @param finalY destination column
     * @param board board
     * @param piece piece that was at the destination position
     */
    public void revertPosition(int curX, int curY, int finalX, int finalY, Piece[][] board, Piece piece){
        m_x = curX;
        m_y = curY;
        board[curX][curY] = this;
        board[finalX][finalY] = piece;
    }

    /**
     * checks if a move is present in the list of moves
     * @param listMoves list of moves
     * @param finPos destination
     * @return true or false
     */
//    public boolean containsPos(ArrayList<Integer[]> listMoves, Integer[] finPos ){
//        for (int i = 0; i< listMoves.size(); i++){
//            Integer[] pos = listMoves.get(i);
//            if( finPos[0] == pos[0] && finPos[1] == pos[1])
//                return true;
//        }
//        return false;
//    }
}
