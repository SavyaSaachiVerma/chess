package Player;

import Pieces.Piece;
import Utils.*;

import java.util.ArrayList;

public class Player {
    public Util.Color m_color;
    public ArrayList<Piece> listAvailablePieces;
    public String name;

    /**
     * Constructor for Player.Player Class
     * @param color the color of the player's pieces
     */
    public Player(Util.Color color) {
        m_color = color;
        listAvailablePieces = new ArrayList<>();
    }

    /**
     * It will initialise the list of pieces of the player
     * @param board the chess board
     */
    public void initialiseListAvailablePieces(Piece[][] board) {
        int startRow;

        if (m_color == Util.Color.WHITE)
            startRow = 0;
        else
            startRow = 6;

        for (int i = startRow; i < startRow + Util.PLAYER_ROWS; i++) {
            for (int j = 0; j < Util.NUM_COLS; j++) {
                listAvailablePieces.add(board[i][j]);
            }
        }
    }

    /**
     * removes a piece from the players list
     * @param piece The piece to be removed
     */
    public void removePiece(Piece piece) {
        listAvailablePieces.remove(piece);
    }

    /**
     * returns the player's king's position
     * @return Integer array of length 2
     */
    public Integer[] getKingPosition() {
        int numAvailablePieces = listAvailablePieces.size();
        Integer[] pos = new Integer[2];
        for (int i = 0; i < numAvailablePieces; i++){
            if (listAvailablePieces.get(i).m_type == Util.Type.KING){
                pos[0] = listAvailablePieces.get(i).m_x;
                pos[1] = listAvailablePieces.get(i).m_y;
                break;
            }
        }
        return pos;
    }

    /**
     * Returns all possible moves of opponents pieces
     * @param opponent opponent player
     * @param board the chess board
     * @return ArrayList
     */
    ArrayList<Integer[]> getOpponentPossibleMoves(Player opponent, Piece[][] board){
        ArrayList<Integer[]> moves = new ArrayList<>();
        for(int i=0; i < opponent.listAvailablePieces.size(); i++){
            Piece piece = opponent.listAvailablePieces.get(i);
            moves.addAll(piece.getPossibleMoves(piece.m_x, piece.m_y, board));
        }
        return moves;
    }

    /**
     * REturns true if the king is checked
     * @param opponent Opponent player
     * @param board chessboard
     * @return bool
     */
    public boolean isKingChecked( Player opponent, Piece[][] board ){
        Integer[] kingPos = getKingPosition();
        ArrayList<Integer[]> opponentMoves = getOpponentPossibleMoves(opponent, board);
        if (Util.containsPos(opponentMoves, kingPos))
            return true;
        return false;
    }

    /**
     * Moves the piece from current position to final position ( IF POSSIBLE)
     * @param curX current row position
     * @param curY current column position
     * @param finalX final row position
     * @param finalY final column position
     * @param board chessboard
     * @param opponent opponent player
     * @return bool value indicating success of move
     */
    public boolean movePiece(int curX, int curY, int finalX, int finalY, Piece[][] board, Player opponent){
        //check if there is a piece at the current location
        if (board[curX][curY] == null || board[curX][curY].m_color != m_color)
            return false;
        //Selected spot has a piece
        Piece piece = board[curX][curY];
        Piece finalPosPiece = board[finalX][finalY];
        if(piece.isValidMove(curX,curY,finalX, finalY, opponent, board)){
            // if the finalPosPiece is an opponent piece remove from opponent's piece list. Already removed from board.
            if(isOpponentPieceCaptured(finalPosPiece)){
                //opponent.listAvailablePieces.remove(finalPosPiece);
                opponent.removePiece(finalPosPiece);
            }
            return true;
        }
        return false;
    }

    /**
     * checks if any opponet's piece was captured during this move
     * @param finalPosPiece the piece at the final position( the position to which the piece in hand has to be moved to)
     * @return bool value
     */
    public boolean isOpponentPieceCaptured(Piece finalPosPiece){
        if((finalPosPiece != null) && (finalPosPiece.m_color != this.m_color))
                return true;
        return false;
    }

    /**
     * Checks if the possible moves list contains the destination specified by player
     * @param listMoves a list of all the possible moves of the piece
     * @param finPos destination
     * @return bool value indicating status
     */
//    private boolean containsPos(ArrayList<Integer[]> listMoves, Integer[] finPos ){
//        for (int i = 0; i< listMoves.size(); i++){
//            Integer[] pos = listMoves.get(i);
//            if( finPos[0] == pos[0] && finPos[1] == pos[1])
//                return true;
//        }
//        return false;
//    }

    /**
     * Checks if the player is under check mate
     * @param board chessboard instance
     * @param opponent opponent player
     * @return status of the king
     */
    public boolean isCheckMate(Piece[][] board, Player opponent){
        if(!isKingChecked(opponent, board)){
            return false;
        }
        return(isStaleMate(board, opponent));
    }

    /**
     *  Checks if the player is in a stalemate situation
     * @param board chessboard
     * @param opponent opponent player
     * @return true or false
     */
     private boolean isStaleMate(Piece[][] board, Player opponent){
        // Loop - 1 running over available pieces
        for (int iterPiece = 0; iterPiece < listAvailablePieces.size(); iterPiece++){
                Piece piece = listAvailablePieces.get(iterPiece);
            Integer[] curPos = new Integer[2];
            curPos[0] = piece.m_x;
            curPos[1] = piece.m_y;
            ArrayList<Integer[]> piecePossibleMoves = piece.getPossibleMoves(curPos[0], curPos[1], board);
            // Loop - 2 running over available piece's moves
            for(int iterMoves = 0; iterMoves < piecePossibleMoves.size(); iterMoves++ ){
                Integer[] finalPos = piecePossibleMoves.get(iterMoves);
                Piece pieceAtFinalPos = board[finalPos[0]][finalPos[1]];
                piece.updatePosition(curPos[0], curPos[1], finalPos[0], finalPos[1], board);
                if (isKingChecked(opponent, board)) {
                    piece.revertPosition(curPos[0], curPos[1], finalPos[0], finalPos[1], board, pieceAtFinalPos);
                }
                else{
                    piece.revertPosition(curPos[0], curPos[1], finalPos[0], finalPos[1], board, pieceAtFinalPos);
                    return false;
                }
            }
        }
        return true;
    }
}
