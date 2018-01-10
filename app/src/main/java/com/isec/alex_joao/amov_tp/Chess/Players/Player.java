package com.isec.alex_joao.amov_tp.Chess.Players;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Jogada;
import com.isec.alex_joao.amov_tp.Chess.Pieces.King;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Perfil;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    ArrayList<Piece> pieces;
    int id;
    private Coord dir;
    private String name;
    private King king;
    private Perfil perfil;
    private Chess game;

    public Player(int id, Coord dir, Perfil perfil, Chess game) {
        this.game = game;
        this.id = id;
        pieces = new ArrayList<>();
        this.dir = dir;
        king = null;
        if (perfil != null)
            this.perfil = perfil;
        else
            this.perfil = new Perfil("Android", "");
    }

    public Player(int id, Coord dir, Chess game) {
        this(id, dir, null, game);
    }

    public Player(Player player) {
        this.game = player.game;
        this.id = player.id;
        pieces = player.pieces;
        this.dir = player.dir;
        king = player.king;
        perfil = new Perfil("Android", "");
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        if (perfil != null)
            this.perfil = perfil;

    }

    public Chess getGame() {
        return game;
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

    public void win() {
        if (perfil != null)
            perfil.addWin();
    }

    public void lose() {
        if (perfil != null)
            perfil.addDefeats();
    }

    public abstract Jogada play();
}
