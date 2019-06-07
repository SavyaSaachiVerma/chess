package main;
import ChessGUI.*;
import Player.*;
import Pieces.*;
import Utils.*;
import ChessBoard.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;


public class GameController {
    private boolean start;
    private boolean restart;
    private boolean forfeit;
    private boolean waitForPlayer;
    private boolean waitForName;
    private boolean nextRound;
    private boolean custom;
    private boolean undo1;
    private boolean undo2;
    private String name1;
    private String name2;
    private int score1;
    private int score2;
    private Point location;
    private ChessGUI view;
    private Player curPlayer;
    private Stack<UndoMove> undoStack;
    private int[] ini;
    private int[] fin;
    private Icon iniImg;
    private Icon finImg;
    private Piece curPiece;
    private Piece capturedPiece;

    GameController(){
        gameLoop();
    }

    public void setUp(){
        //Do the initial set up of the board
        score1 = 0;
        score2 = 0;
        custom = false;
        undoStack = new Stack<>();
        ini = new int[2];
        fin = new int[2];
        undo1 = false;
        undo2 = false;
        view = new ChessGUI();
        waitForPlayer = true;
        waitForName = true;
        start = false;
        nextRound = true;
        view.addStartButtonListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //System.out.print(" HI 0 !");
                start = true;
                name1 = view.playerName1.getText();
                name2 = view.playerName2.getText();
                waitForName = false;
                custom = false;
            }
        });
        view.addCustomStartButton(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                custom = true;
                start = true;
                name1 = view.playerName1.getText();
                name2 = view.playerName2.getText();
                waitForName = false;
            }
        });
        view.addUndoButtonListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Player has Pressed UNDO MOVE");
                if(curPlayer.m_color == Util.Color.WHITE)
                    undo1 = true;
                else
                    undo2 = true;
            }
        });
        view.addRestartButtonListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                restart = true;
                String[] options = {"YES", "NO"};
                int choice = JOptionPane.showOptionDialog(null,curPlayer.name + "wants to " +
                        "restart the game. Select " + "whether you want to restart or not", "choose an option",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "NO");
                if(choice == 0){
                    start = false;
                    forfeit = false;
                    updateScoreRestart();
                }
                else
                    start = true;
            }
        });
        view.addForfeitButtonListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                forfeit = true;
                JOptionPane.showMessageDialog(null, "Player forfeit the game." );
                updateScore();
                start = false;
                restart = false;
            }
        });
        view.addBoardSquareListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JButton button = (JButton)(e.getSource());
                location = button.getLocation();
                waitForPlayer = false;
            }
        });
        view.score1.setText(String.valueOf(score1));
        view.score2.setText(String.valueOf(score2));
        JOptionPane.showMessageDialog(null, "Please Enter Both Player's name and hit START \n" +
                "Left side: White Player Name & Right Side: Black Player Name. ");
    }

    private void gameLoop(){
        while(true) {
            setUp();
            boolean firstGame = true;
            while(waitForName){
                System.out.print(1);
            }
            waitForName = true;
            while (start || forfeit || restart) {
                Player player1 = new Player(Util.Color.WHITE);
                Player player2 = new Player(Util.Color.BLACK);
                ChessBoard chessBoard = new ChessBoard();
                Piece[][] board = chessBoard.board;
                resetSquareColors();
                if(custom){
                    setUpBoardCustom(player1, player2, chessBoard);
                    view.setupNewGameCustom();
                }
                else {
                    setUpBoard(player1, player2, chessBoard);
                    view.setupNewGame();
                }
                boolean firstMove;
                ArrayList<Integer[]> possibleMoves = new ArrayList<>();
                int[] initialPos = {-1, -1};
                if(firstGame) {
                    player1.name = name1;
                    player2.name = name2;
                    firstGame = false;
                }
                JOptionPane.showMessageDialog(null, "Starting New Game");
                start = true;
                while( nextRound && !checkForfeitOrRestart()) {
                    firstMove = true;
                    while (firstMove) {
                        if (checkForfeitOrRestart()) break;
                        JOptionPane.showMessageDialog(null, "Select the piece which you would like " +
                                "to move" + player1.name);
                        waitForPlayer = true;
                        while (waitForPlayer) {
                            if (checkForfeitOrRestart()) break;
                            if(undo2){
                                board[fin[0]][fin[1]] = capturedPiece;
                                board[ini[0]][ini[1]] = curPiece;
                                if(capturedPiece != null) {
                                    capturedPiece.m_x = fin[0];
                                    capturedPiece.m_y = fin[1];
                                    player1.listAvailablePieces.add(capturedPiece);
                                }
                                if(curPiece != null) {
                                    curPiece.m_x = ini[0];
                                    curPiece.m_y = ini[1];
                                }
                                view.boardSquares[ini[0]][ini[1]].setIcon(iniImg);
                                view.boardSquares[fin[0]][fin[1]].setIcon(finImg);
                                break;
                            }
                            System.out.print(" 1 ");
                        }
                        if(undo2)
                            break;
                        if (checkForfeitOrRestart()) break;
                        curPlayer = player1;
                        initialPos = getRowColFromLocation(location);
                        if (checkNullOrWrongPiece(player1, board[initialPos[0]][initialPos[1]], Util.Color.WHITE))
                            continue;
                        Piece piece = board[initialPos[0]][initialPos[1]];
                        possibleMoves = piece.getPossibleMoves(initialPos[0], initialPos[1], board);
                        if (checkValidPiece( player1, possibleMoves)) continue;
                        showPossibleMovesSquare(possibleMoves);
                        firstMove = false;
                    }
                    boolean secondMove = true;
                    while (secondMove && !undo2) {
                        if (checkForfeitOrRestart()) break;
                        waitForPlayer = true;
                        while (waitForPlayer) {
                            if (checkForfeitOrRestart()) break;
                            System.out.print(" 1 ");
                        }
                        if (checkForfeitOrRestart()) break;
                        int[] finPos = getRowColFromLocation(location);
                        fin[0] = finPos[0];
                        fin[1] = finPos[1];
                        ini[0] = initialPos[0];
                        ini[1] = initialPos[1];
                        iniImg = view.boardSquares[initialPos[0]][initialPos[1]].getIcon();
                        finImg = view.boardSquares[finPos[0]][finPos[1]].getIcon();
                        curPiece = board[initialPos[0]][initialPos[1]];
                        capturedPiece = board[finPos[0]][finPos[1]];
                        if (player1.movePiece(initialPos[0], initialPos[1], finPos[0], finPos[1], board, player2)) {
                            makeChanges(player1, player2, player2.isCheckMate(board, player1), possibleMoves,
                                    view.boardSquares[initialPos[0]][initialPos[1]], view.boardSquares[finPos[0]][finPos[1]]);
                            secondMove = false;
                            undo1 = false;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Move  " + player1.name);
                        }
                    }
                    firstMove = true;
                    while (firstMove) {
                        if (checkForfeitOrRestart()) break;
                        JOptionPane.showMessageDialog(null, "Select the piece which you would like " +
                                "to move" + player2.name);
                        waitForPlayer = true;
                        while (waitForPlayer) {
                            if (checkForfeitOrRestart()) break;
                            if(undo1){
                                Piece temp = board[fin[0]][fin[1]];
                                board[fin[0]][fin[1]] = capturedPiece;
                                board[ini[0]][ini[1]] = curPiece;
                                if(capturedPiece != null) {
                                    capturedPiece.m_x = fin[0];
                                    capturedPiece.m_y = fin[1];
                                    player2.listAvailablePieces.add(capturedPiece);
                                }
                                if(curPiece != null) {
                                    curPiece.m_x = ini[0];
                                    curPiece.m_y = ini[1];
                                }
                                view.boardSquares[ini[0]][ini[1]].setIcon(iniImg);
                                view.boardSquares[fin[0]][fin[1]].setIcon(finImg);
                                break;
                            }
                            System.out.print(" 1 ");
                        }
                        if (checkForfeitOrRestart()) break;
                        if(undo1)
                            break;
                        curPlayer = player2;
                        initialPos = getRowColFromLocation(location);
                        if (checkNullOrWrongPiece(player2, board[initialPos[0]][initialPos[1]], Util.Color.BLACK))
                            continue;
                        //Selected spot has a piece
                        Piece piece = board[initialPos[0]][initialPos[1]];
                        possibleMoves = piece.getPossibleMoves(initialPos[0], initialPos[1], board);
                        if (checkValidPiece(player2, possibleMoves)) continue;
                        showPossibleMovesSquare(possibleMoves);
                        firstMove = false;
                    }
                    secondMove = true;
                    while (secondMove && !undo1) {
                        if (checkForfeitOrRestart()) break;
                        waitForPlayer = true;
                        while (waitForPlayer) {
                            if (checkForfeitOrRestart()) break;
                            System.out.print(" 1 ");
                        }
                        if (checkForfeitOrRestart()) break;
                        int[] finPos = getRowColFromLocation(location);
                        fin[0] = finPos[0];
                        fin[1] = finPos[1];
                        ini[0] = initialPos[0];
                        ini[1] = initialPos[1];
                        iniImg = view.boardSquares[initialPos[0]][initialPos[1]].getIcon();
                        finImg = view.boardSquares[finPos[0]][finPos[1]].getIcon();
                        curPiece = board[initialPos[0]][initialPos[1]];
                        capturedPiece = board[finPos[0]][finPos[1]];
                        if (player2.movePiece(initialPos[0], initialPos[1], finPos[0], finPos[1], board, player1)) {
                            makeChanges( player1,  player2, player1.isCheckMate(board, player2),
                                    possibleMoves,  view.boardSquares[initialPos[0]][initialPos[1]],
                                    view.boardSquares[finPos[0]][finPos[1]]);
                            secondMove = false;
                            undo2 = false;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Move  " + player2.name);
                        }
                    }
                }

            }
        }
    }

    private void makeChanges(Player player1, Player player2, boolean checkMate, ArrayList<Integer[]> possibleMoves,
                             JButton jButton, JButton jButton1) {
        jButton1.setIcon(jButton.getIcon());
        jButton.setIcon(null);
        for (int i = 0; i < possibleMoves.size(); i++) {
            Integer[] pos = possibleMoves.get(i);
            if ((pos[0] % 2 == 1 && pos[1] % 2 == 1) || (pos[0] % 2 == 0 && pos[1] % 2 == 0)) {
                view.boardSquares[pos[0]][pos[1]].setBackground(Color.WHITE);
            } else {
                view.boardSquares[pos[0]][pos[1]].setBackground(Color.BLACK);
            }
        }
        if (checkMate) {
            JOptionPane.showMessageDialog(null, player2.name + "you have got " +
                    "check mate");
            updateScoreCheckMate(player1);
            start = false;
        }
    }

    private void showPossibleMovesSquare(ArrayList<Integer[]> possibleMoves) {
        for (int i = 0; i < possibleMoves.size(); i++) {
            Integer[] pos = possibleMoves.get(i);
            view.boardSquares[pos[0]][pos[1]].setBackground(Color.cyan);
        }
    }

    private boolean checkValidPiece(Player player1, ArrayList<Integer[]> possibleMoves) {
        if (possibleMoves.size() < 1) {
            JOptionPane.showMessageDialog(null, "Please Select A Piece Which Can " +
                    "Be Moved " + player1.name);
            return true;
        }
        return false;
    }

    private boolean checkNullOrWrongPiece(Player player1, Piece piece2, Util.Color white) {
        if (piece2 == null) {
            JOptionPane.showMessageDialog(null, "Null Piece Selected " + player1.name);
            return true;
        }
        if (piece2.m_color != white) {
            JOptionPane.showMessageDialog(null, "Please Select your own Piece " + player1.name);
            return true;
        }
        return false;
    }

    private boolean checkForfeitOrRestart() {
        if (!start)
            return true;
        return false;
    }

    private void resetSquareColors() {
        for (int i = 0; i < 8; i++) {               //row
            for (int j = 0; j < 8; j++) {          //column
                if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0)) {
                    view.boardSquares[i][j].setBackground(Color.WHITE);
                    view.boardSquares[i][j].setIcon(null);
                } else {
                    view.boardSquares[i][j].setBackground(Color.BLACK);
                    view.boardSquares[i][j].setIcon(null);
                }
            }
        }
    }

    public void setUpBoard(Player player1, Player player2, ChessBoard chessBoard) {
        chessBoard.setUpBoardForPlayer(player1);
        chessBoard.setUpBoardForPlayer(player2);
        chessBoard.setUpRemainingBoard();
        //chessBoard.displayBoard();
        player1.initialiseListAvailablePieces(chessBoard.board);
        player2.initialiseListAvailablePieces((chessBoard.board));
    }

    public void setUpBoardCustom(Player player1, Player player2, ChessBoard chessBoard) {
        chessBoard.setUpBoardForPlayerCustom(player1);
        chessBoard.setUpBoardForPlayerCustom(player2);
        chessBoard.setUpRemainingBoard();
        //chessBoard.displayBoard();
        player1.initialiseListAvailablePieces(chessBoard.board);
        player2.initialiseListAvailablePieces((chessBoard.board));
    }

    public int[] getRowColFromLocation(Point location){
        int[] arr = new int[2];
        arr[1] = (location.x - 24) / 74;
        arr[0] = (location.y - 24) / 74;
        return arr;
    }

    public void updateScoreRestart(){
        score1 += 1;
        score2 += 1;
        view.score1.setText(String.valueOf(score1));
        view.score2.setText(String.valueOf(score2));
    }

    public void updateScore(){
        if (curPlayer.m_color == Util.Color.WHITE){
            score2 += 1;
            view.score2.setText(String.valueOf(score2));
        }
        else {
            score1 += 1;
            view.score1.setText((String.valueOf(score1)));
        }
    }

    public void updateScoreCheckMate( Player player){
        if (player.m_color == Util.Color.WHITE){
            score1 += 1;
            view.score1.setText(String.valueOf(score1));
        }
        else {
            score2 += 1;
            view.score2.setText((String.valueOf(score2)));
        }
    }

    public static void main(String[] args){
       GameController game = new GameController();

    }

}
