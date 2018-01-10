package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.Board;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Players.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements Serializable {

    public Rook(Player player, Board board) {
        super(player, board);
    }

    @Override
    public String toString() {
        return "R(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public List<Coord> getDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();
        int x = pos.getX();
        int y = pos.getY();

        for (int i = x + 1; addPieceList(board, list, new Coord(i, y)); ++i)
            ; //maxX
        for (int i = y + 1; addPieceList(board, list, new Coord(x, i)); ++i)
            ;//maxY
        for (int i = y - 1; addPieceList(board, list, new Coord(x, i)); --i)
            ;  //min Y
        for (int i = x - 1; addPieceList(board, list, new Coord(i, y)); --i)
            ; //minX

        return list;
    }

    @Override
    public String getUnicodeString() {
        String str;
        if (player.getId() == 0)
            str = "\u2656";
        else if (player.getId() == 1)
            str = "\u265C";
        else
            str = super.getUnicodeString();
        return str;
    }
}
