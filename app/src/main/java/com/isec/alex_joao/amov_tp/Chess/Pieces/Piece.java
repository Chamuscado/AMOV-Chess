package com.isec.alex_joao.amov_tp.Chess.Pieces;

import com.isec.alex_joao.amov_tp.Chess.*;

public class Piece implements PieceInterface {

    private Square square;
    private Player player;

    public Piece(Player player) {
        this.player = player;
    }


    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int[][] gerDesloc(Board board) {
        return new int[0][];
    }
}
