package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    protected static final int SAMEPLAYER = 0;
    protected static final int NEXTPLAYER = 1;
    protected static final int WRONGPLAYER = 2;
    private Square board[][];
    private Piece selected;
    private Chess game;

    public Board(Coord size, Chess game) {
        this.game = game;
        board = new Square[size.X][size.Y];
        for (int i = 0; i < size.X; ++i)
            for (int j = 0; j < size.X; ++j)
                board[i][j] = new Square(i, j);
        selected = null;
    }

    public int setSelected(Coord pos, Player player) {            //TODO -> este metodo está todo fodido
        int resp = SAMEPLAYER;
        Piece p;
        List<Coord> list = null;
        try {
            p = getPieceAt(pos.X, pos.Y);
        } catch (IllegalArgumentException ex) {
            p = null;
        }
        if (selected != null)
            list = selected.getDesloc();

        if (p != null)                                            //TODO -> verificar se a peça é do jogador
        {
            Player player2 = p.getPlayer();
            if (player2 == player) {                   //muda a peça selecionada
                selected = p;
            } else if (list != null)
                if (list.contains(pos)) {                           //verifica se é uma jogada valida
                    removeDefinitelyPieceAt(pos);                     //comer a peça
                    selected.moveTo(this, pos);
                    resp = NEXTPLAYER;
                }

        } else { // movimento simples ( sem comer)

            if (list != null)
                if (list.contains(pos)) {
                    selected.moveTo(this, pos);//moveTo(pos);
                    resp = NEXTPLAYER;
                }
        }

        return resp;
    }

    public void removeSelected() {
        selected = null;
    }

    public void initBoard(Player[] players) {

        ArrayList<Piece> pieces = players[0].getPieces();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++)
                board[x][y].setPiece(pieces.get(y * 8 + x));
        }
        pieces = players[1].getPieces();
        int aux = pieces.size() - 1;
        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++, aux--)
                board[x][y].setPiece(pieces.get(aux));
        }
    }

    public Piece getPieceAt(Coord pos) {
        return getPieceAt(pos.X, pos.Y);
    }

    public Piece getPieceAt(int x, int y) {
        if (x < 0 || x > board.length)
            throw new IllegalArgumentException("<geyPieceAT error>: Coluna Inválida : " + x);
        if (y < 0 || y > board[x].length)
            throw new IllegalArgumentException("<geyPieceAT error>: Linha Inválida : " + y);
        return board[x][y].getPiece();
    }

    public Square getSquareAt(int x, int y) {
        if (x < 0 || x > board.length)
            throw new IllegalArgumentException("<getSquareAt error>: Coluna Inválida : " + x);
        if (y < 0 || y > board[x].length)
            throw new IllegalArgumentException("<getSquareAt error>: Linha Inválida : " + y);
        return board[x][y];
    }

    public Square getSquareAt(Coord pos) {
        return getSquareAt(pos.getX(), pos.getY());
    }

    public Piece getSelected() {
        return selected;
    }

    public void removeDefinitelyPieceAt(Coord pos) {
        Piece p = getPieceAt(pos);
        if (p == null)
            return;
        Player player = p.getPlayer();
        player.removePiece(p);
        board[pos.X][pos.Y].removePiece();
    }

    public Chess getGame() {
        return game;
    }
}
