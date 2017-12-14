package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;

public class Square implements Serializable {
    private int x, y;
    private Piece piece;
    private int color;
    private int backColor;

    public Square(int x, int y) {
        piece = null;
        color = 0;
        color = 0;
        this.x = x;
        this.y = y;
    }

    public Coord getPos() {
        return new Coord(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece removePiece() {
        Piece p = piece;
        piece = null;
        return p;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        piece.setSquare(this);
        this.piece = piece;
    }


    public Boolean hasPiece() {
        return piece != null;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    @Override
    public String toString() {
        return piece == null ? " " : piece.toString();
    }
}
