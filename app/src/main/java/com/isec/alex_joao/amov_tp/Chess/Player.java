package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    ArrayList<Piece> pieces;
    int id;
    private Coord dir;

    public Player(int id, Coord dir) {
        this.id = id;
        pieces = new ArrayList<>();
        this.dir = dir;
    }

    public Coord getDir() {
        return dir;
    }

    public void tick()
    {
        for(Piece i : pieces)
            i.tick();
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

    public void removePiece(Piece p) {
        pieces.remove(p);
    }
}
