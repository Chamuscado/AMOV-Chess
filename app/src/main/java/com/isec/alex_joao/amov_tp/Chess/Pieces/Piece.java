package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.List;

public class Piece implements Serializable {

    protected Square square;
    protected Player player;
    protected boolean fistMoved;

    public Piece(Player player) {
        this.player = player;
        fistMoved = true;
    }

    protected boolean addPieceList(Board board, List<Coord> list, Coord pos) {
        boolean resp = false;
        if (pos.isValid()) {
            Piece p = board.getPieceAt(pos);
            if (p == null) {
                list.add(pos);
                resp = true;
            } else {
                if (p == this) {
                    resp = true;
                } else {
                    Player player = p.getPlayer();
                    if (player != this.player)
                        list.add(pos);
                }
            }
        }
        return resp;
    }

    public void moveTo(Board board, Coord pos2) {
        Coord pos = getSquare().getPos();

        Piece piece = board.getSquareAt(pos).removePiece();
        piece.Moved();
        board.getSquareAt(pos2).setPiece(piece);

    }

    public void tick() {
    }

    public boolean isFistMoved() {
        return fistMoved;
    }

    public void Moved() {
        this.fistMoved = false;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Coord> gerDesloc(Board board) {
        return null;
    }

    public String getUnicodoString() {
        return "\u2A09" + player.getId();
    }
}
