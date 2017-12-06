package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.List;

public class Piece implements Serializable{

    protected Square square;
    protected Player player;

    public Piece(Player player) {
        this.player = player;
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

    public String getUnicodoString()
    {
        return "\u2A09" + player.getId();
    }
}
