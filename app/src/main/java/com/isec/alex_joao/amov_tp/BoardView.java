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
    int[] corSquare;
    Paint boardPaint;
    Paint textPaint;
    float cellSize;
    float cellSizeX;
    float cellSizeY;
    Chess game;
    final static int MAX = 10;

    public BoardView(Context context, Chess game) {
        super(context);
        corSquare = new int[2];
        corSquare[0] = getResources().getColor(R.color.Square0);
        corSquare[1] = getResources().getColor(R.color.Square1);
        boardPaint = new Paint();
        textPaint = new Paint();
        cellSize = 0;
        this.game = game;
    }

    @Override
    public void draw(Canvas canvas) {               // TODO -> é preciso refazer todo o método
        super.draw(canvas);
        cellSizeX = canvas.getWidth() / MAX;
        cellSizeY = canvas.getHeight() / MAX;
        float piecesOffsetX = 0.15f * cellSizeX;
        float piecesOffsetY = 0.15f * cellSizeY;
        float textOffsetX = 0.30f * cellSizeX;
        float textOffsetY = 0.30f * cellSizeY;
        float coordSize = 0.80f * cellSizeY;
        canvas.drawLine(0, 0, cellSizeX, cellSizeY, textPaint);

        Square s = null;
        List<Coord> posPos = null;
        if (game.hasSelected()) {
            s = game.getSelected().getSquare();
            if (s != null)
                posPos = s.getPiece().gerDesloc(game.getBoard());
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
                        canvas.drawText(p.getUnicodoString(), x * cellSizeX + piecesOffsetX, (y + 1) * cellSizeY - piecesOffsetY, textPaint);
                    }
                }
            }
        }

/*
        cellSize = canvas.getWidth() / (float) MAX;
        float textSize = cellSize;
        float textSizeCoords = cellSize * (float) 0.8;
        Coord c;
        Piece p;
        textPaint.setTextSize(textSize);
        float PiecesOffset = 0.15f * cellSize;
        float TextOffset = 0.30f * cellSize;
        boardPaint.reset();
        Square s = null;
        List<Coord> posPos = null;
        if (game.hasSelected()) {
            s = game.getSelected().getSquare();
            if (s != null)
                posPos = s.getPiece().gerDesloc(game.getBoard());
        }

        for (int x = 0; x < MAX; ++x) {
            for (int y = 0; y < MAX; ++y) {
                c = new Coord(x, y);
                if ((x == 0 || x == (MAX - 1)) && y != 0 && y != (MAX - 1)) {
                    textPaint.setTextSize(textSizeCoords);
                    textPaint.setColor(Color.BLACK);

                    canvas.drawText(Integer.toString(MAX - y - 1), x * cellSize + TextOffset, (y + 1) * cellSize - PiecesOffset, textPaint);

                } else if ((y == 0 || y == (MAX - 1)) && x != 0 && x != (MAX - 1)) {
                    textPaint.setTextSize(textSizeCoords);
                    textPaint.setColor(Color.BLACK);
                    canvas.drawText(Character.toString((char) ('a' + x - 1)), x * cellSize + TextOffset, (y + 1) * cellSize - PiecesOffset, textPaint);
                }
                if (c.isValid()) {
                    boardPaint.setColor(corSquare[(x + y) % 2]);
                    drawCoordinate(c, canvas, cellSize, boardPaint, MAX);
                    if (s != null)
                        if (s.getPos().equals(c)) {
                            boardPaint.setColor(getResources().getColor(R.color.SquareOfSelecedPiece));
                        }
                    if (posPos != null) {
                        if (posPos.contains(c)) {
                            boardPaint.setColor(getResources().getColor(R.color.PossibleMovement));
                        }
                    }

                    drawCoordinate(c, canvas, cellSize, boardPaint, MAX);
                    // if (isInEditMode()) continue;
                    p = game.getBoard().getPieceAt(x, MAX - y - 3);          //verificar cordenadas
                    if (p != null) {
                        textPaint.setTextSize(textSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(p.getUnicodoString(), (x + 1) * cellSize + PiecesOffset, (MAX - y - 1) * cellSize - PiecesOffset, textPaint);

                    } else {
                        textPaint.setTextSize(textSize / 2);
                        canvas.drawText(c.toString(), (x + 1) * cellSize, (MAX - y - 1) * cellSize - PiecesOffset, textPaint);
                    }
                }
            }
        }
        */
    }

    @Deprecated
    private void drawCoordinate(Coord c, Canvas canvas, float cellSize, Paint paint, int max) {
        canvas.drawRect((c.getX() + 1) * cellSize, (c.getY() + 1) * cellSize, (c.getX() + 2) * cellSize, (c.getY() + 2) * cellSize, paint);
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
                game.setSelected(pos);
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

}
