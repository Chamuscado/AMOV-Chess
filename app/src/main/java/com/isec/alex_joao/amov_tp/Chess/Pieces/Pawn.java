package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements Serializable {

    private boolean doubleSquare;

    public Pawn(Player player,Board board) {
        super(player,board);
        doubleSquare = false;
    }

    public boolean isDoubleSquare() {
        return doubleSquare;
    }

    @Deprecated
    public void setDoubleSquare(boolean doubleSquare) {
        this.doubleSquare = doubleSquare;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public List<Coord> gerDesloc() {
        List<Coord> list = new ArrayList<>();
        Coord dir = player.getDir();
        Coord pos = getSquare().getPos();

        Coord pos2 = new Coord(pos.getX() + dir.getX(), pos.getY() + dir.getY());       // celula à frente

        if (pos2.isValid())
            if (board.getPieceAt(pos2.getX(), pos2.getY()) == null)
                addPieceList(board, list, pos2);

        if (isFistMoved() && list.contains(pos2)) {
            pos2 = new Coord(pos.getX() + (dir.getX() * 2), pos.getY() + (dir.getY() * 2)); // 2 celulas à frente
            if (pos2.isValid())
                if (board.getPieceAt(pos2.getX(), pos2.getY()) == null)
                    addPieceList(board, list, pos2);
        }

        pos2 = new Coord(pos.getX() + (dir.getX() == 0 ? 1 : dir.getX()), pos.getY() + (dir.getY() == 0 ? 1 : dir.getY()));                      // celula frente direita
        if (pos2.isValid()) {
            Piece p = board.getPieceAt(pos2);
            if (p != null)
                if (p.getPlayer() != player)
                    list.add(pos2);
        }
        pos2 = new Coord(pos.getX() + (dir.getX() == 0 ? -1 : dir.getX()), pos.getY() + (dir.getY() == 0 ? -1 : dir.getY()));                       // celula frente esquerda
        if (pos2.isValid()) {
            Piece p = board.getPieceAt(pos2);
            if (p != null)
                if (p.getPlayer() != player)
                    list.add(pos2);
        }

        pos2 = new Coord(pos.getX() + (dir.getX() == 0 ? -1 : 0), pos.getY() + (dir.getY() == 0 ? -1 : 0));     // celula de um lado
        if (pos2.isValid()) {
            Piece p = board.getPieceAt(pos2);
            if (p != null)
                if (p.getPlayer() != player && p instanceof Pawn)
                    if (((Pawn) p).isDoubleSquare())
                        list.add(new Coord(pos2.getX() + dir.getX(), pos2.getY() + dir.getY()));
        }


        pos2 = new Coord(pos.getX() + (dir.getX() == 0 ? 1 : 0), pos.getY() + (dir.getY() == 0 ? 1 : 0));     // celula de um lado
        if (pos2.isValid()) {
            Piece p = board.getPieceAt(pos2);
            if (p != null)
                if (p.getPlayer() != player && p instanceof Pawn)
                    if (((Pawn) p).isDoubleSquare())
                        list.add(new Coord(pos2.getX() + dir.getX(), pos2.getY() + dir.getY()));
        }
        return list;
    }

    @Override
    public void moveTo(Board board, Coord pos2) {
        Coord pos = getSquare().getPos();
        Coord dir = getPlayer().getDir();

        Piece piece = board.getSquareAt(pos).removePiece();
        piece.Moved();

        Coord pos3 = new Coord(pos.getX() + 1, pos.getY() + dir.getY());
        if (pos3.isValid() && pos2.equals(pos3))
        {
            board.removeDefinitelyPieceAt(new Coord(pos.getX() + 1, pos.getY()));
            pos2 = pos3;
        }
        pos3 = new Coord(pos.getX() - 1, pos.getY() + dir.getY());
        if (pos3.isValid() && pos2.equals(pos3))
        {
            board.removeDefinitelyPieceAt(new Coord(pos.getX() - 1, pos.getY()));
            pos2 = pos3;
        }

        board.getSquareAt(pos2).setPiece(piece);

        if (Math.sqrt(Math.pow(pos.getX() - (double) pos2.getX(), 2) + Math.pow(pos.getY() - (double) pos2.getY(), 2)) == 2)
            doubleSquare = true;
    }

    @Override
    public void tick() {
        doubleSquare = false;
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
