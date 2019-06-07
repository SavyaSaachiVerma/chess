package main;

import Player.*;
import Utils.*;
import ChessBoard.*;

public class Game {

    public static void main(String[] args){
        Player player1 = new Player(Util.Color.WHITE);
        Player player2 = new Player(Util.Color.BLACK);
        ChessBoard chessBoard = new ChessBoard();
        setUpBoard(player1, player2, chessBoard);
        if(player2.movePiece(6,4,4,4, chessBoard.board, player1))
            System.out.print("sucessful move \n");
        else
            System.out.print("failed move \n");
        chessBoard.displayBoard();
        if(player2.movePiece(7,4,6,4, chessBoard.board, player1))
            System.out.print("sucessful move \n");
        else
            System.out.print("failed move \n");
        chessBoard.displayBoard();
        if(player2.movePiece(6,3,5,3, chessBoard.board, player1))
        System.out.print("sucessful move \n");
        else
        System.out.print("failed move \n");
        chessBoard.displayBoard();
        if(player1.movePiece(1,1,2,1, chessBoard.board, player2))
        System.out.print("sucessful move \n");
        else
        System.out.print("failed move \n");
        chessBoard.displayBoard();
        if(player1.movePiece(0,2,2,0, chessBoard.board, player2))
        System.out.print("sucessful move \n");
        else
        System.out.print("failed move \n");
        chessBoard.displayBoard();
        if(player2.movePiece(5,3,4,3, chessBoard.board, player1))
        System.out.print("sucessful move \n");
        else
        System.out.print("failed move \n");
        chessBoard.displayBoard();


        System.out.print(player1.listAvailablePieces.size());

    }

    /**
     * This function is basically an initialization process.
     * @param player1 player1
     * @param player2 player2
     * @param chessBoard the chessboard class instance
     */

    public static void setUpBoard(Player player1, Player player2, ChessBoard chessBoard) {
        chessBoard.setUpBoardForPlayer(player1);
        chessBoard.setUpBoardForPlayer(player2);
        chessBoard.setUpRemainingBoard();
        //chessBoard.displayBoard();
        player1.initialiseListAvailablePieces(chessBoard.board);
        player2.initialiseListAvailablePieces((chessBoard.board));
    }


}
