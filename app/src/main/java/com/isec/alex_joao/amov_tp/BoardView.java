package com.isec.alex_joao.amov_tp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Chess.Square;

import java.util.List;

/**
 * Created by Chamuscado on 02/12/2017.
 */

public class BoardView extends View {
    final static int MAX = 10;
    int[] corSquare;
    Paint boardPaint;
    Paint textPaint;
    float cellSize;
    float cellSizeX;
    float cellSizeY;


    public BoardView(Context context) {
        super(context);
        corSquare = new int[2];
        corSquare[0] = getResources().getColor(R.color.Square0);
        corSquare[1] = getResources().getColor(R.color.Square1);
        boardPaint = new Paint();
        textPaint = new Paint();
        cellSize = 0;

    }

    @Override
    public void draw(Canvas canvas) {               // TODO -> é preciso refazer todo o método
        Chess game = ChessApp.game;
        super.draw(canvas);
        cellSizeX = canvas.getWidth() / MAX;
        cellSizeY = canvas.getHeight() / MAX;
        float piecesOffsetX = 0.15f * cellSizeX;
        float piecesOffsetY = 0.15f * cellSizeY;
        float textOffsetX = 0.30f * cellSizeX;
        float textOffsetY = 0.30f * cellSizeY;
        float coordSize = 0.80f * cellSizeY;
        canvas.drawLine(0, 0, cellSizeX, cellSizeY, textPaint);
        canvas.drawLine(cellSizeX * MAX, 0, cellSizeX * (MAX - 1), cellSizeY, textPaint);
        canvas.drawLine(0, cellSizeY * MAX, cellSizeX, cellSizeY * (MAX - 1), textPaint);
        canvas.drawLine(cellSizeX * MAX, cellSizeY * MAX, cellSizeX * (MAX - 1), cellSizeY * (MAX - 1), textPaint);
        Coord jogadorAtualKing = null;
        if (game.isKingCheck()) {
            jogadorAtualKing = game.getJogadorAtual().getKing().getSquare().getPos();
        }
        Square s = null;
        List<Coord> posPos = null;
        if (game.hasSelected()) {
            s = game.getSelected().getSquare();
            if (s != null)
                posPos = s.getPiece().getDesloc();
        }

        for (int x = 0; x < MAX; ++x) {
            for (int y = 0; y < MAX; ++y) {
                Coord pos = new Coord(x - 1, MAX - y - 2);
                if (x == 0 || y == 0 || x == MAX - 1 || y == MAX - 1) {
                    // insere no canvas as letras e numeros das conrdenadas
                    if ((x == 0 || x == MAX - 1) && y != 0 && y != (MAX - 1)) {
                        textPaint.setTextSize(coordSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(Integer.toString(MAX - y - 1), x * cellSizeX + textOffsetX,
                                (y + 1) * cellSizeY - textOffsetY, textPaint);

                    } else if (x != 0 && x != (MAX - 1)) {
                        textPaint.setTextSize(coordSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(Character.toString((char) ('a' + x - 1)), x * cellSizeX +
                                textOffsetX, (y + 1) * cellSizeY - textOffsetY, textPaint);
                    }
                } else if (pos.isValid()) {
                    boardPaint.setColor(corSquare[(x + y) % 2]);
                    canvas.drawRect(x * cellSizeX, y * cellSizeY, (x + 1) * cellSizeX, (y + 1) * cellSizeY, boardPaint);
                    //textPaint.setTextSize(cellSizeX / 4);
                    //canvas.drawText(new Coord(x, y).toString() + pos.toString(), x * cellSizeX, (y + 1) * cellSizeY - piecesOffsetY, textPaint);

                    if(jogadorAtualKing != null && jogadorAtualKing.equals(pos)){
                        boardPaint.setColor(getResources().getColor(R.color.KingCheck));
                        canvas.drawRect(x * cellSizeX, y * cellSizeY, (x + 1) * cellSizeX, (y + 1) * cellSizeY, boardPaint);
                    }

                    if (s != null)
                        if (s.getPos().equals(pos)) {
                            boardPaint.setColor(getResources().getColor(R.color.SquareOfSelecedPiece));
                            canvas.drawRect(x * cellSizeX, y * cellSizeY, (x + 1) * cellSizeX, (y + 1) * cellSizeY, boardPaint);
                        }
                    if (posPos != null) {
                        if (posPos.contains(pos)) {
                            boardPaint.setColor(getResources().getColor(R.color.PossibleMovement));
                            canvas.drawRect(x * cellSizeX, y * cellSizeY, (x + 1) * cellSizeX, (y + 1) * cellSizeY, boardPaint);
                        }
                    }

                    Piece p = game.getBoard().getPieceAt(pos);          //verificar cordenadas
                    if (p != null) {
                        textPaint.setTextSize(cellSizeX);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(p.getUnicodeString(), x * cellSizeX + piecesOffsetX, (y + 1) * cellSizeY - piecesOffsetY, textPaint);
                    }
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.min(getMeasuredWidth(), getMeasuredHeight()),
                Math.min(getMeasuredWidth(), getMeasuredHeight()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (cellSizeX != 0 || cellSizeY != 0) {
            int x = (int) (event.getX() / cellSizeX);
            int y = (int) (event.getY() / cellSizeY);
            Coord pos = new Coord(x - 1, MAX - y - 2);
            if (pos.isValid()) {
                if (!ChessApp.game.setSelected(pos))
                    Toast.makeText(getContext(), getResources().getString(R.string.wrongplayer), Toast.LENGTH_SHORT).show();
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

}
