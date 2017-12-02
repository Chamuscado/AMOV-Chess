package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

public class Queen extends Piece {

    public Queen(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2655";
        else if (player.getId() == 1)
            str = "\u265B";
        else
            str = super.getUnicodoString();
        return str;
    }
}
