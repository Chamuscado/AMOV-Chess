package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private Square board[][];
    private Piece selected;
    protected static final int SAMEPLAYER = 0;
    protected static final int NEXTPLAYER = 1;

    public Board(Coord size) {
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
            list = selected.gerDesloc(this);

        if (p != null)                                            //TODO -> verificar se a peça é do jogador
        {
            Player player2 = p.getPlayer();
            if (player2 == player) {                   //muda   a peça selecionada
                selected = p;
            } else if (list != null)
                if (list.contains(pos)) {                           //verifica se é uma jogada valida
                    player2.removePiece(p);                         //comer a peça
                    board[pos.X][pos.Y].removePiece();
                    moveTo(pos);
                    resp = NEXTPLAYER;
                }

        } else { // movimento simples ( sem comer)

            if (list != null)
                if (list.contains(pos)) {
                    moveTo(pos);
                    resp = NEXTPLAYER;
                }
        }

        return resp;
    }

    public void removeSelected() {
        selected = null;
    }


    public void moveTo(Coord pos) {
        if (selected != null)
            movePiece(selected.getSquare().getPos(), pos);
    }

    public void initBoard(Player[] players) {

        ArrayList<Piece> pieces = players[0].getPieces();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++)
                board[j][i].setPiece(pieces.get(j * 8 + i));
        }
        pieces = players[1].getPieces();
        int aux = pieces.size() - 1;
        for (int j = 6; j < 8; j++) {
            for (int i = 0; i < 8; i++, aux--)
                board[j][i].setPiece(pieces.get(aux));
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


    public void movePiece(Coord pos1, Coord pos2) {
        if (!board[pos1.X][pos1.Y].hasPiece())
            return;
        Piece piece = board[pos1.X][pos1.Y].removePiece();
        piece.Moved();

        board[pos2.X][pos2.Y].setPiece(piece);
    }

    public Piece getSelected() {
        return selected;
    }

}
