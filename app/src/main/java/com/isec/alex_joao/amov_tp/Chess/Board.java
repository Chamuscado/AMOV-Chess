package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private Square board[][];
    private Piece selected;


    public Board(CoordV2 size) {
        board = new Square[size.X][size.Y];
        for (int i = 0; i < size.X; ++i)
            for (int j = 0; j < size.X; ++j)
                board[i][j] = new Square(i, j);
        selected = null;
    }

    public int setSelected(CoordV2 pos, Player player) {            //TODO -> este metodo está todo fodido
        int resp = 0;
        Piece p = getPieceAt(pos.X, pos.Y);
        if (p != null)                           //TODO -> verificar se a peça é do jogador
        {
            Player player2 = p.getPlayer();
            if (player2 == player) {                   //muda a peça selecionada
                selected = p;
            } else {                                  //comer a peça
                player2.removePiece(p);
                board[pos.X][pos.Y].removePiece();
                moveTo(pos);
                resp = 1;
            }

        } else {
            moveTo(pos);
            resp = 1;
        }
        return resp;
    }

    public void removeSelected() {
        selected = null;
    }

    public void moveTo(Coord pos) {
        moveTo(new CoordV2(pos));
    }

    public void moveTo(CoordV2 pos) {
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

    public Piece getPieceAt(CoordV2 pos) {
        return getPieceAt(pos.X, pos.Y);
    }

    public Piece getPieceAt(int x, int y) {
        if (x < 0 || x > board.length)
            throw new IllegalArgumentException("<geyPieceAT error>: Coluna Inválida : " + x);
        if (y < 0 || y > board[x].length)
            throw new IllegalArgumentException("<geyPieceAT error>: Linha Inválida : " + y);
        return board[x][y].getPiece();
    }


    public void movePiece(CoordV2 pos1, CoordV2 pos2) {
        if (!board[pos1.X][pos1.Y].hasPiece())
            return;
        Piece piece = board[pos1.X][pos1.Y].removePiece();
        List<CoordV2> mat = piece.gerDesloc(this);


        board[pos2.X][pos2.Y].setPiece(piece);
    }

    public Piece getSelected() {
        return selected;
    }

    public void printBoard() {
        System.out.println("Piece Selected: " + selected);
        int p = 1;
        System.out.print("    ");
        for (int i = 0; i < 8; ++i)
            System.out.print(((char) (i + 'A')) + " ");
        System.out.println();
        System.out.print("    ");

        for (int i = 0; i < 8; ++i)
            System.out.print("- ");

        System.out.println();
        for (Square[] i : board) {
            System.out.print(p + " | ");
            for (Object j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
            p++;
        }
    }

}
