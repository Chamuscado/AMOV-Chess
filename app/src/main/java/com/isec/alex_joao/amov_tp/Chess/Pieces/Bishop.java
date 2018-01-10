package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.Board;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Players.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece implements Serializable {

    public Bishop(Player player,Board board) {
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
        return list;
    }


    @Override
    public String toString() {
        return "B(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public String getUnicodeString() {
        String str;
        if (player.getId() == 0)
            str = "\u2657";
        else if (player.getId() == 1)
            str = "\u265D";
        else
            str = super.getUnicodeString();
        return str;
    }
}
