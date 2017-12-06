package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.Player;

import java.io.Serializable;

public class Bishop extends Piece implements Serializable{

    public Bishop(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2657";
        else if (player.getId() == 1)
            str = "\u265D";
        else
            str = super.getUnicodoString();
        return str;
    }
}
