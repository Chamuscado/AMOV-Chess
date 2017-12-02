package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Pieces.*;

import java.util.ArrayList;

public class Board {
    private Square board[][];
    Piece selected;


    public Board(CoordV2 size) {
        board = new Square[size.X][size.Y];
        for (int i = 0; i < size.X; ++i)
            for (int j = 0; j < size.X; ++j)
                board[i][j] = new Square(i, j);
        selected = null;
    }

    public void setSelected(CoordV2 pos) {

        selected = getPieceAt(pos.X, pos.Y);
    }

    public void moveTo(Coord pos) {
        if (selected != null)
            movePiece(selected.getSquare().getPos(), new CoordV2(pos));
        else
            System.out.println("Piece not selected!");
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
        int[][] mat = piece.gerDesloc(this);

        for (int[] i : mat) {
            System.out.println();
            for (int j : i) {
                System.out.print("  " + j);
            }
        }


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
