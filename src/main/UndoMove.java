package main;

import Pieces.Piece;

import javax.swing.*;

public class UndoMove {
    int[] m_from;
    int[] m_to;
    Piece m_piece;
    Piece m_captured;
    Icon m_iniImage;
    Icon m_finImage;
    boolean firstMove;

    UndoMove(int[] from, int[] to, Piece piece, Piece captured, Icon iniImage, Icon finImage){
        m_from[0] = from[0];
        m_from[1] = from[1];
        m_to[0] = to[0];
        m_to[1] = to[1];
        m_piece = piece;
        m_captured = captured;
        m_iniImage = iniImage;
        m_finImage = finImage;
    }
}
