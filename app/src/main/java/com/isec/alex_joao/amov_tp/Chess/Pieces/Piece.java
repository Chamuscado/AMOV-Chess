package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.List;

public class Piece implements Serializable {

    protected Square square;
    protected Player player;

    public Piece(Player player) {
        this.player = player;
    }

    protected boolean addPieceList(Board board, List<CoordV2> list, CoordV2 pos) {
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
                    if (!player.equals(this.player))
                        list.add(pos);
                }
            }
        }
        return resp;
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

    public List<CoordV2> gerDesloc(Board board) {
        return null;
    }

    public String getUnicodoString() {
        return "\u2A09" + player.getId();
    }
}
