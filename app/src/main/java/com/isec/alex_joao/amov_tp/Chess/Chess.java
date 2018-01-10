package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.Bishop;
import com.isec.alex_joao.amov_tp.Chess.Pieces.King;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Knight;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Pawn;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Queen;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Rook;
import com.isec.alex_joao.amov_tp.Chess.Players.BotPlayer;
import com.isec.alex_joao.amov_tp.Chess.Players.LocalPlayer;
import com.isec.alex_joao.amov_tp.Chess.Players.Player;
import com.isec.alex_joao.amov_tp.Chess.Players.RemotePlayer;
import com.isec.alex_joao.amov_tp.ChessApp;

import java.io.Serializable;

public class Chess implements Serializable {
    public static final int ContinueGame = 0;
    public static final int OneVsPhone = 1;
    public static final int OneVsOne = 2;
    public static final int OneVsOneNetwork = 3; // é suposto só ser usado para comunicar entre as atividades
    public static final int OneVsOneNetworkServer = 4;
    public static final int OneVsOneNetworkClient = 5;
    private Player[] players;
    private Player winner;
    private Board board;
    private Player jogadorAtual;
    private boolean end;

    public Chess() {
        this(OneVsOne);
    }

    public Chess(int type) {

        winner = null;
        end = false;
        players = new Player[2];

        if (type == OneVsOneNetworkClient) {
            players[0] = new RemotePlayer(0, new Coord(0, 1), this);
            players[1] = new LocalPlayer(1, new Coord(0, -1), ChessApp.perfilSelecionado, this);
        } else if (type == OneVsOneNetworkServer) {
            players[0] = new LocalPlayer(0, new Coord(0, 1), ChessApp.perfilSelecionado, this);
            players[1] = new RemotePlayer(1, new Coord(0, -1), this);
        } else {
            players[0] = new LocalPlayer(0, new Coord(0, 1), ChessApp.perfilSelecionado, this);
            if (type == OneVsPhone)
                players[1] = new BotPlayer(1, new Coord(0, -1), this);
            else if (type == OneVsOne)
                players[1] = new LocalPlayer(1, new Coord(0, -1), ChessApp.perfilSelecionado, this);
        }
        jogadorAtual = players[0];
        board = new Board(new Coord(8, 8), this);
        initBoard();
        players[0].play();
    }

    public boolean isKingCheck() {
        return jogadorAtual.isKingInCheck();
    }

    public Player getJogadorAtual() {
        return jogadorAtual;
    }

    public void initBoard() {
        for (int j = 0; j < 2; j++) {
            players[j].addPiece(new Rook(players[j], board));
            players[j].addPiece(new Knight(players[j], board));
            players[j].addPiece(new Bishop(players[j], board));
            if (j == 0) {
                players[j].addPiece(new Queen(players[j], board));
                players[j].addPiece(new King(players[j], board));
            } else if (j == 1) {
                players[j].addPiece(new King(players[j], board));
                players[j].addPiece(new Queen(players[j], board));
            }
            players[j].addPiece(new Bishop(players[j], board));
            players[j].addPiece(new Knight(players[j], board));
            players[j].addPiece(new Rook(players[j], board));
            for (int i = 0; i < 8; i++)
                players[j].addPiece(new Pawn(players[j], board));
        }
        board.initBoard(players);
        if (players[0] instanceof RemotePlayer)
            ((RemotePlayer) players[0]).startThread();
        if (players[1] instanceof RemotePlayer)
            ((RemotePlayer) players[1]).startThread();

    }

    public void nextPlayer() {
        if (jogadorAtual == players[0])
            jogadorAtual = players[1];
        else {
            jogadorAtual = players[0];
        }
        jogadorAtual.tick();
        board.removeSelected();
        if (!(jogadorAtual instanceof LocalPlayer)) {
            Jogada jog = jogadorAtual.play();
            joga(jog);
        }
    }

    public Piece getSelected() {
        return board.getSelected();
    }

    public boolean joga(Jogada jog) {
        if (end || jog == null)
            return false;
        board.setSelected(jog.getCoord1(), jogadorAtual);
        board.setSelected(jog.getCoord2(), jogadorAtual);
        nextPlayer();
        return true;
    }

    public boolean setSelected(Coord pos) {
        if (jogadorAtual instanceof LocalPlayer) {
            if (end)
                return false;
            int i = board.setSelected(pos, jogadorAtual);
            if (i == Board.NEXTPLAYER) {
                nextPlayer();
            }
            if (i == Board.WRONGPLAYER)
                return false;
            return true;
        } else
            return false;


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

    public void endGame(Player player) {
        winner = player;
        winner.win();
        if (winner == players[0])
            players[1].lose();
        else {
            players[0].lose();
        }
        end = true;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isEnd() {
        return end;
    }

    public void removeRemote() {
        for (int i = 0; i < players.length; i++)
            if (players[i] instanceof RemotePlayer)
                players[i] = new BotPlayer(players[i]);
    }

    public Player[] getplayeres() {
        return players;
    }
}
