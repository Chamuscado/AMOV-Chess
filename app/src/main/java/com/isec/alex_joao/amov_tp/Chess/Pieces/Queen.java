package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece implements Serializable {

    public Queen(Player player,Board board) {
        super(player,board);
    }

    @Override
    public List<Coord> getDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();
        int x = pos.getX();
        int y = pos.getY();

        for (int i = x + 1, j = y + 1; addPieceList(board, list, new Coord(i, j)); ++i, ++j) ;
        for (int i = x - 1, j = y + 1; addPieceList(board, list, new Coord(i, j)); --i, ++j) ;
        for (int i = x + 1, j = y - 1; addPieceList(board, list, new Coord(i, j)); ++i, --j) ;
        for (int i = x - 1, j = y - 1; addPieceList(board, list, new Coord(i, j)); --i, --j) ;
        for (int i = x + 1; addPieceList(board, list, new Coord(i, y)); ++i) ;
        for (int i = y + 1; addPieceList(board, list, new Coord(x, i)); ++i) ;
        for (int i = y - 1; addPieceList(board, list, new Coord(x, i)); --i) ;
        for (int i = x - 1; addPieceList(board, list, new Coord(i, y)); --i) ;
        return list;
    }

    @Override
    public String toString() {
        return "Q(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public String getUnicodeString() {
        String str;
        if (player.getId() == 0)
            str = "\u2655";
        else if (player.getId() == 1)
            str = "\u265B";
        else
            str = super.getUnicodeString();
        return str;
    }
}
