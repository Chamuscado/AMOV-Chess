package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements Serializable {

    public Rook(Player player) {
        super(player);
    }

    @Override
    public String toString() {
        return "R(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public List<CoordV2> gerDesloc(Board board) {
        List<CoordV2> list = new ArrayList<>();
        CoordV2 pos = getSquare().getPos();

        for (int i = pos.getX(); addPieceList(board, list, new CoordV2(i, pos.getY())); ++i)
            ; //maxX

        for (int i = pos.getY(); addPieceList(board, list, new CoordV2(pos.getX(), i)); ++i)
            ;//maxY

        for (int i = pos.getY(); addPieceList(board, list, new CoordV2(pos.getX(), i)); --i)
            ;  //min Y

        for (int i = pos.getX(); addPieceList(board, list, new CoordV2(i, pos.getY())); --i)
            ; //minX

        return list;
    }

    @Override
    public String getUnicodoString() {
        String str;
        if (player.getId() == 0)
            str = "\u2656";
        else if (player.getId() == 1)
            str = "\u265C";
        else
            str = super.getUnicodoString();
        return str;
    }
}
