package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.ChessApp;
import com.isec.alex_joao.amov_tp.R;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Player implements Serializable {
    ArrayList<Piece> pieces;
    int id;
    Socket des;
    private Coord dir;
    private String name;

    public Player(String name, int id, Coord dir) {
        this.id = id;
        pieces = new ArrayList<>();
        this.dir = dir;
    }

    public Player(int id, Coord dir) {
        this(ChessApp.getContext().getString(R.string.player) + id, id, dir);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getDir() {
        return dir;
    }

    public void tick() {
        for (Piece i : pieces)
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

    public void play(Chess chess)
    {

    }
}
