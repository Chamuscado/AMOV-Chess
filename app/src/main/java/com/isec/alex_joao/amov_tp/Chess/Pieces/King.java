package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.Board;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class King extends Piece implements Serializable {

    private boolean check;

    public King(Player player, Board board) {
        super(player, board);
        check = false;
    }

    @Override
    public List<Coord> getDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord pos = getSquare().getPos();

        for (int i = -1; i <= 1; ++i)
            for (int j = -1; j <= 1; ++j)
                addPieceList(board, list, new Coord(i + pos.getX(), j + pos.getY()));
        list.remove(getSquare().getPos());

        if (check && !board.getGame().getOtherPlayer().equals(player)) {
            List<Piece> list2 = board.getGame().getOtherPlayer().getPieces();
            Set<Coord> allPos = new HashSet<>();
            for (Piece i : list2) {
                allPos.addAll(i.getDesloc());
            }
            for (Coord i : allPos) {
                Iterator<Coord> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().equals(i))
                        it.remove();
                }
            }
        }
        if (list.size() == 0) {
            board.getGame().endGame(board.getGame().getOtherPlayer());
        }


        return list;
    }


    public boolean isCheck() {
        return check;
    }

    @Override
    public void tick() {
        Coord pos = getSquare().getPos();
        List<Piece> list = board.getGame().getOtherPlayer().getPieces();
        for (Piece i : list) {
            for (Coord j : i.getDesloc()) {
                if (pos.equals(j)) {
                    check = true;
                    return;
                }
            }
        }
        check = false;
    }

    @Override
    public String toString() {
        return "K(" + getSquare().getX() + "/" + getSquare().getY() + ")";
    }

    @Override
    public String getUnicodeString() {
        String str;
        if (player.getId() == 0)
            str = "\u2654";
        else if (player.getId() == 1)
            str = "\u265A";
        else
            str = super.getUnicodeString();
        return str;
    }
}
