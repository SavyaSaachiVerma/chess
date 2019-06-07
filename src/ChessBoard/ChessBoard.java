package ChessBoard;

import Pieces.*;
import Player.*;
import Utils.*;


/**
 * THis class describes the chessboard.
 */
public class ChessBoard {
    public Piece[][] board;
    int numRows;
    int numCols;

    /**
     * constructor for ChessBoard.ChessBoard class
     */
    public ChessBoard(){
        numRows = Util.NUM_ROWS;
        numCols = Util.NUM_COLS;
        board = new Piece[numRows][numCols];
    }

    /**
     * sets up the pieces of the player on the board
     * @param player player
     */
    public void setUpBoardForPlayer (Player player){
        int a;
        if (player.m_color == Util.Color.BLACK)
            a = 0;
        else
            a = 1;

        for(int i = 0; i < Util.NUM_PAWNS; i++){
            int pawnRow = 6-5*a;
            board[pawnRow][i] = new Pawn(pawnRow, i, player, Util.Type.PAWN);
        }

        int pieceRow = 7 - 7*a;
        board[pieceRow][0] = new Rook(pieceRow, 0, player, Util.Type.ROOK);
        board[pieceRow][7] = new Rook(pieceRow, 7, player, Util.Type.ROOK);
        board[pieceRow][1] = new Knight(pieceRow, 1, player, Util.Type.KNIGHT);
        board[pieceRow][6] = new Knight(pieceRow, 6, player, Util.Type.KNIGHT);
        board[pieceRow][2] = new Bishop(pieceRow, 2, player, Util.Type.BISHOP);
        board[pieceRow][5] = new Bishop(pieceRow, 5, player, Util.Type.BISHOP);
        board[pieceRow][3] = new Queen(pieceRow, 3, player, Util.Type.QUEEN);
        board[pieceRow][4] = new King(pieceRow, 4, player, Util.Type.KING);

    }

    public void setUpBoardForPlayerCustom (Player player){
        int a;
        if (player.m_color == Util.Color.BLACK)
            a = 0;
        else
            a = 1;
        int pawnRow = -1;
        for(int i = 0; i < Util.NUM_PAWNS; i++){
            pawnRow = 6-5*a;
            board[pawnRow][i] = new Pawn(pawnRow, i, player, Util.Type.PAWN);
        }
        board[pawnRow][2] = new PawnStar(pawnRow, 2, player, Util.Type.PSTAR);
        board[pawnRow][5] = new PawnStar(pawnRow, 5, player, Util.Type.PSTAR);

        int pieceRow = 7 - 7*a;
        board[pieceRow][0] = new Rook(pieceRow, 0, player, Util.Type.ROOK);
        board[pieceRow][7] = new Rook(pieceRow, 7, player, Util.Type.ROOK);
        board[pieceRow][1] = new Knight(pieceRow, 1, player, Util.Type.KNIGHT);
        board[pieceRow][6] = new Knight(pieceRow, 6, player, Util.Type.KNIGHT);
        board[pieceRow][2] = new Knook(pieceRow, 2, player, Util.Type.KNOOK);
        board[pieceRow][5] = new Knook(pieceRow, 5, player, Util.Type.KNOOK);
        board[pieceRow][3] = new Queen(pieceRow, 3, player, Util.Type.QUEEN);
        board[pieceRow][4] = new King(pieceRow, 4, player, Util.Type.KING);

    }

    /**
     * setting the other places at chessboard to null
     */
    public void setUpRemainingBoard(){
        for (int i = Util.START_EMPTY_ROWS; i < Util.END_EMPTY_ROWS; i++){
            for (int j = 0; j < Util.NUM_COLS ; j++){
                board[i][j] = null;
            }
        }
    }

    /**
     * displays the chess board
     */
    public void  displayBoard(){
        for (int iterRow = 0; iterRow < Util.NUM_ROWS; iterRow++){
            for(int iterCol = 0; iterCol < Util.NUM_COLS; iterCol++){
                Piece piece = board[iterRow][iterCol];
                if (piece != null){
                    System.out.print(piece.m_type);
                    System.out.print(' ');
                }
                else {
                    System.out.print("null ");
                }
            }
            System.out.println(' ');
        }
    }

}
