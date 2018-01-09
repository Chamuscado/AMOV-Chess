package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements Serializable {

    public Knight(Player player,Board board) {
        super(player,board);
    }

    @Override
    public String toString() {
        return "k(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public List<Coord> getDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();
        int x = pos.getX();
        int y = pos.getY();
        if (pos.isValid()) addPieceList(board, list, new Coord(x + 1, y + 2));
        if (pos.isValid()) addPieceList(board, list, new Coord(x + 2, y + 1));
        if (pos.isValid()) addPieceList(board, list, new Coord(x + 2, y - 1));
        if (pos.isValid()) addPieceList(board, list, new Coord(x + 1, y - 2));
        if (pos.isValid()) addPieceList(board, list, new Coord(x - 1, y - 2));
        if (pos.isValid()) addPieceList(board, list, new Coord(x - 2, y - 1));
        if (pos.isValid()) addPieceList(board, list, new Coord(x - 2, y + 1));
        if (pos.isValid()) addPieceList(board, list, new Coord(x - 1, y + 2));
        return list;
    }

    @Override
    public String getUnicodeString() {
        String str;
        if (player.getId() == 0)
            str = "\u2658";
        else if (player.getId() == 1)
            str = "\u265E";
        else
            str = super.getUnicodeString();
        return str;
    }
}
