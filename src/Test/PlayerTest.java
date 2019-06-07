package Test;

import Pieces.Piece;
import Player.*;
import ChessBoard.*;
import Utils.*;
import main.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

//    //@BeforeEach
//    void setUp() {
//        Player.Player player1 = new Player.Player(Utils.Util.Color.WHITE);
//        Player.Player player2 = new Player.Player(Utils.Util.Color.BLACK);
//        ChessBoard.ChessBoard chessBoard = new ChessBoard.ChessBoard();
//        main.Game game = new main.Game();
//        game.setUpBoard(player1, player2, chessBoard );
//    }

    @Test
    void getKingPositionTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        Integer[] kingPosWhite = new Integer[2];
        kingPosWhite[0] = 0;
        kingPosWhite[1] = 4;
        Integer[] kingPosBlack = new Integer[2];
        kingPosBlack[0] = 7;
        kingPosBlack[1] = 4;
        //checking Original Positions
        Assertions.assertArrayEquals(player1.getKingPosition(), kingPosWhite, "White Pieces.King's position not correct" );
        Assertions.assertArrayEquals(player2.getKingPosition(), kingPosBlack, "Black Pieces.King's position not correct" );
        //Checking new position
        player1.movePiece(1,5,3,5, chessBoard.board, player2);
        player1.movePiece(0,4,1,5, chessBoard.board, player2);
        kingPosWhite[0] = 1;
        kingPosWhite[1] = 5;
        Assertions.assertArrayEquals(player1.getKingPosition(), kingPosWhite, "White Pieces.King's position not correct" );

    }

    @Test
    void isKingCheckedTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        player1.movePiece(1,5,3,5, chessBoard.board, player2);
        player2.movePiece(6,4,5,4, chessBoard.board, player1);
        player1.movePiece(1,6,3,6, chessBoard.board, player2);
        player2.movePiece(7,3,3,7, chessBoard.board, player1);
        chessBoard.displayBoard();
        Assertions.assertTrue(player1.isKingChecked(player2, chessBoard.board));
    }


    @Test
    void isOpponentPieceCapturedTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        Piece pieceAtFinalPos = chessBoard.board[6][1];
        player1.movePiece(1,6,3,6, chessBoard.board, player2);
        player1.movePiece(0,5,1,6, chessBoard.board, player2);
        player1.movePiece(1,6,6,1, chessBoard.board, player2);
        chessBoard.displayBoard();
        Assertions.assertTrue(player1.isOpponentPieceCaptured(pieceAtFinalPos));

    }

    @Test
    void isCheckMateTest() {
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        Game.setUpBoard(player1, player2, chessBoard );
        player1.movePiece(1,5,3,5, chessBoard.board, player2);
        player2.movePiece(6,4,5,4, chessBoard.board, player1);
        player1.movePiece(1,6,3,6, chessBoard.board, player2);
        player2.movePiece(7,3,3,7, chessBoard.board, player1);
        chessBoard.displayBoard();
        Assertions.assertTrue(player1.isCheckMate(chessBoard.board, player2));
    }


}