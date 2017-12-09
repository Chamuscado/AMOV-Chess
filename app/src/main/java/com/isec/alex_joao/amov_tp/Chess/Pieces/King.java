package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements Serializable {

    public King(Player player) {
        super(player);
    }

    @Override
    public List<Coord> gerDesloc(Board board) {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();

        for (int i = -1; i <= 1; ++i)
            for (int j = -1; j <= 1; ++j)
                addPieceList(board, list, new Coord(i + pos.getX(), j + pos.getY()));
        list.remove(getSquare().getPos());
        return list;
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
