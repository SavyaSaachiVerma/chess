package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Pieces.*;
import Player.*;
import ChessBoard.*;
import Utils.*;
import main.*;
class PieceTest {

    @Test
    void isValidMoveTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        Piece piece = chessBoard.board[1][2];
        Assertions.assertTrue(piece.isValidMove(1,2,3,2, player2, chessBoard.board));
        Assertions.assertFalse(piece.isValidMove(1,2,0,2, player2, chessBoard.board));
    }

    @Test
    void checkPieceAtLocationTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        Piece piece = chessBoard.board[0][2];
        Assertions.assertFalse(piece.checkPieceAtLocation(3,4, chessBoard.board));
        Assertions.assertTrue(piece.checkPieceAtLocation(7,4, chessBoard.board));
    }

    @Test
    void checkOpponentPieceTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        Piece piece = chessBoard.board[0][2];
        Assertions.assertFalse(piece.checkOpponentPiece(0,5, chessBoard.board));
        Assertions.assertTrue(piece.checkOpponentPiece(7,4, chessBoard.board));
    }
}