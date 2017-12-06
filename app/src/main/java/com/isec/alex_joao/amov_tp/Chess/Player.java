package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    ArrayList<Piece> pieces;
    int id;

    public Player(int id) {
        this.id = id;
        pieces = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
