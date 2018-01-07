package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.Bishop;
import com.isec.alex_joao.amov_tp.Chess.Pieces.King;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Knight;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Pawn;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Queen;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Rook;

import java.io.Serializable;

public class Chess implements Serializable {
    public static final int ContinueGame = 0;
    public static final int OneVsPhone = 1;
    public static final int OneVsOne = 2;
    public static final int OneVsOneNetwork = 3; // é suposto só ser usado para comunicar entre as atividades
    public static final int OneVsOneNetworkServer = 4;
    public static final int OneVsOneNetworkClient = 5;
    private Player[] players;
    private Board board;
    private Player jogadorAtual;

    public Chess() {
        this(OneVsOne);
    }

    public Chess(int type) {
        players = new Player[2];
        jogadorAtual = players[0] = new Player(0, new Coord(0, 1));
        players[1] = new Player(1, new Coord(0, -1));
        board = new Board(new Coord(8, 8), this);
        initBoard();
    }

    public boolean isKingCheck() {
        return jogadorAtual.isKingInCheck();
    }

    public Player getJogadorAtual() {
        return jogadorAtual;
    }

    public void initBoard() {
        for (int j = 0; j < 2; j++) {
            players[j].addPiece(new Rook(players[j],board));
            players[j].addPiece(new Knight(players[j],board));
            players[j].addPiece(new Bishop(players[j],board));
            if (j == 0) {
                players[j].addPiece(new Queen(players[j],board));
                players[j].addPiece(new King(players[j],board));
            } else if (j == 1) {
                players[j].addPiece(new King(players[j],board));
                players[j].addPiece(new Queen(players[j],board));
            }
            players[j].addPiece(new Bishop(players[j],board));
            players[j].addPiece(new Knight(players[j],board));
            players[j].addPiece(new Rook(players[j],board));
            for (int i = 0; i < 8; i++)
                players[j].addPiece(new Pawn(players[j],board));
        }
        board.initBoard(players);
    }

    public void nextPlayer() {
        if (jogadorAtual == players[0])
            jogadorAtual = players[1];
        else {
            jogadorAtual = players[0];
        }
        jogadorAtual.tick();
        board.removeSelected();
    }

    public Piece getSelected() {
        return board.getSelected();
    }

    public boolean setSelected(Coord pos) {
        int i = board.setSelected(pos, jogadorAtual);
        if (i == Board.NEXTPLAYER) {
            nextPlayer();
        }
        if (i == Board.WRONGPLAYER)
            return false;
        return true;
    }

    public Player getOtherPlayer() {
        if (jogadorAtual == players[0])
            return players[1];
        else {
            return players[0];
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasSelected() {
        return board.getSelected() != null;
    }

}
