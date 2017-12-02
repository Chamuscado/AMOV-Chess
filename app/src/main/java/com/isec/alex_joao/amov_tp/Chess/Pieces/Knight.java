package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

public class Knight extends Piece {

    public Knight(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "k";
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2658";
        else if (player.getId() == 1)
            str = "\u265E";
        else
            str = super.getUnicodoString();
        return str;
    }
}
