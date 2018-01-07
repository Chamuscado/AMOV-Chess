package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.King;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Perfil;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Player implements Serializable {
    ArrayList<Piece> pieces;
    int id;
    Socket des;
    private Coord dir;
    private String name;
    private King king;
    private Perfil perfil;

    public Player(int id, Coord dir) {
        this.id = id;
        pieces = new ArrayList<>();
        this.dir = dir;
        king = null;
        perfil = null;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Piece getKing() {
        return king;
    }

    public boolean isKingInCheck() {
        return king.isCheck();
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
        if (piece instanceof King && king == null) {
            king = (King) piece;
        }
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void removePiece(Piece p) {
        pieces.remove(p);
    }

}
