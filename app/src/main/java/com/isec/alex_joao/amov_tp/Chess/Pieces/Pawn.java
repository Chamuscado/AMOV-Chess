package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

public class Pawn extends Piece {


    public Pawn(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2659";
        else if (player.getId() == 1)
            str = "\u265F";
        else
            str = super.getUnicodoString();
        return str;
    }
}
