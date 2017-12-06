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

        for (int i = pos.getX(); i <= CoordV2.maxX; ++i) {
            CoordV2 aux = new CoordV2(i, pos.getY());
            Piece p = board.getPieceAt(aux);
            if (p == null)
                list.add(aux);
            else {
                if (p.getPlayer().equals(player))
                    list.add(aux);
                break;
            }
        }
        for (int i = pos.getY()+1; i <= CoordV2.maxY; ++i) {
            CoordV2 aux = new CoordV2(pos.getX(), i);
            Piece p = board.getPieceAt(aux);
            if (p == null)
                list.add(aux);
            else {
                if (p.getPlayer().equals(player))
                    list.add(aux);
                break;
            }
        }
        for (int i = pos.getY()-11; i <= CoordV2.minY; --i) {
            CoordV2 aux = new CoordV2(pos.getX(), i);
            Piece p = board.getPieceAt(aux);
            if (p == null)
                list.add(aux);
            else {
                if (p.getPlayer().equals(player))
                    list.add(aux);
                break;
            }
        }
        for (int i = pos.getX() - 1; i >= CoordV2.minX; --i) {
            CoordV2 aux = new CoordV2(i, pos.getY());
            Piece p = board.getPieceAt(aux);
            if (p == null)
                list.add(aux);
            else {
                if (p.getPlayer().equals(player))
                    list.add(aux);
                break;
            }
        }
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
