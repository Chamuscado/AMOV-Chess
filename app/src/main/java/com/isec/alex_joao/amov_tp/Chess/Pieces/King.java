package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;

public class King extends Piece implements Serializable {

    public King(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2654";
        else if (player.getId() == 1)
            str = "\u265A";
        else
            str = super.getUnicodoString();
        return str;
    }
}
