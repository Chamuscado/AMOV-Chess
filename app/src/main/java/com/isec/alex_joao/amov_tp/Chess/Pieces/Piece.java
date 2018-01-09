package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.Board;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Player;
import com.isec.alex_joao.amov_tp.Chess.Square;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public abstract class Piece implements Serializable {

    protected Player player;
    protected Board board;
    private Square square;
    private boolean fistMoved;

    public Piece(Player player, Board board) {
        this.board = board;
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

    public abstract List<Coord> getDesloc();

    public List<Coord> getEatsPossible() {
        List<Coord> list = getDesloc();
        Iterator<Coord> it = list.iterator();
        while (it.hasNext()) {
            Coord coord = it.next();
            if (board.getPieceAt(coord) != null || board.getPieceAt(coord).player.equals(player))
                it.remove();
        }
        return list;
    }

    public String getUnicodeString() {
        return "\u2A09" + player.getId();
    }
}
