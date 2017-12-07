package com.isec.alex_joao.amov_tp.Chess;

import android.os.Parcelable;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Chess implements Serializable {
    private Player[] players;
    private Board board;
    private Player jogadorAtual;

    public Chess() {
        players = new Player[2];
        jogadorAtual = players[0] = new Player(0);
        players[1] = new Player(1);
        board = new Board(new CoordV2(8, 8));
        initBoard();
    }

    public void initBoard() {
        for (int j = 0; j < 2; j++) {
            players[j].addPiece(new Rook(players[j]));
            players[j].addPiece(new Knight(players[j]));
            players[j].addPiece(new Bishop(players[j]));
            players[j].addPiece(new Queen(players[j]));
            players[j].addPiece(new King(players[j]));
            players[j].addPiece(new Bishop(players[j]));
            players[j].addPiece(new Knight(players[j]));
            players[j].addPiece(new Rook(players[j]));
            for (int i = 0; i < 8; i++)
                players[j].addPiece(new Pawn(players[j]));
        }
        board.initBoard(players);
    }

    public void nextPlayer() {
        if (jogadorAtual == players[0])
            jogadorAtual = players[1];
        else
            jogadorAtual = players[0];
    }

    @Deprecated
    public void setSelected(Coord pos) {

        setSelected(new CoordV2(pos));
    }

    public void setSelected(CoordV2 pos) {
        board.setSelected(pos);
    }

    public Piece getSelected() {
        return board.getSelected();
    }

    @Deprecated
    public void moveTo(Coord pos) {
        board.moveTo(pos);
    }

    private void move(CoordV2 pos1, CoordV2 pos2) {

        board.movePiece(pos1, pos2);
    }

    /*
        @Deprecated
        public void move(Coord pos1, Coord pos2) {
            move(new CoordV2(pos1), new CoordV2(pos2));

        }

        @Deprecated
        public void move(String move) {
            Coord start = new Coord();
            Coord end = new Coord();

            start.setLetter(move.charAt(1));
            start.setNumb(move.charAt(2) - '0');
            end.setLetter(move.charAt(4));
            end.setNumb(move.charAt(5) - '0');

            move(start, end);
        }
    */
    public void printBoard() {
        board.printBoard();
    }


    public Board getBoard() {
        return board;
    }

    public boolean hasSelected() {
        return board.getSelected() != null;
    }
}
