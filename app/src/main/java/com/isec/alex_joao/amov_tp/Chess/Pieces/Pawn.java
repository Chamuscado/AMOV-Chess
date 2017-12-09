package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements Serializable {


    public Pawn(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public List<Coord> gerDesloc(Board board) {
        List<Coord> list = new ArrayList<>();
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                list.add(new Coord(i, j));
        list.remove(getSquare().getPos());
        return list;
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
