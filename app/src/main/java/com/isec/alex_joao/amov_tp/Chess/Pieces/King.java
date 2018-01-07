package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements Serializable {

    private boolean check;
    public King(Player player,Board board) {
        super(player,board);
        check = false;
    }

    @Override
    public List<Coord> gerDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();

        for (int i = -1; i <= 1; ++i)
            for (int j = -1; j <= 1; ++j)
                addPieceList(board, list, new Coord(i + pos.getX(), j + pos.getY()));
        list.remove(getSquare().getPos());
        return list;
    }

    public boolean isCheck() {
        return check;
    }

    @Override
    public void tick() {
        Coord pos = getSquare().getPos();
        List<Piece> list= board.getGame().getOtherPlayer().getPieces();
        for (Piece i: list             ) {
            for (Coord j : i.gerDesloc()){
                if(pos.equals(j)){
                    check = true;
                    return;
                }
            }
        }
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
