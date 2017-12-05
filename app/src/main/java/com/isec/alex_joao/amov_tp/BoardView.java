package com.isec.alex_joao.amov_tp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.CoordV2;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Chess.Square;

/**
 * Created by Chamuscado on 02/12/2017.
 */

public class BoardView extends View {
    int[] corSquare;
    Paint boardPaint;
    Paint textPaint;
    float cellSize;
    Chess game;
    final static int max = 10;

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
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //-----------------------


        cellSize = canvas.getWidth() / (float) max;
        float textSize = cellSize;
        float textSizeCoords = cellSize * (float) 0.8;
        CoordV2 c;
        Piece p;
        textPaint.setTextSize(textSize);
        float textOffset = 0.15f * cellSize;
        boardPaint.reset();
        for (int x = 0; x < max; x++) {
            for (int y = 0; y < max; y++) {
                c = new CoordV2(x, y);
                if ((x == 0 || x == (max - 1)) && y != 0 && y != (max - 1)) {
                    textPaint.setTextSize(textSizeCoords);
                    textPaint.setColor(Color.BLACK);
                    canvas.drawText(Integer.toString(max - y - 1), x * cellSize + textOffset, (max - y) * cellSize - textOffset, textPaint);

                } else if ((y == 0 || y == (max - 1)) && x != 0 && x != (max - 1)) {
                    textPaint.setTextSize(textSizeCoords);
                    textPaint.setColor(Color.BLACK);
                    canvas.drawText(Character.toString((char) ('a' + x - 1)), x * cellSize + textOffset, (max - y) * cellSize - textOffset, textPaint);

                }

                if (c.isValid()) {
                    boardPaint.setColor(corSquare[(x + y) % 2]);
                    if (game.hasSelected()) {
                        Square s = game.getSelected().getSquare();
                        if (s != null)
                            if (s.getPos().equals(c))
                                boardPaint.setColor(getResources().getColor(R.color.SquareOfSelecedPiece));
                    }
                    drawCoordinate(c, canvas, cellSize, boardPaint, max);
                    // if (isInEditMode()) continue;
                    p = game.getBoard().getPieceAt(x, y);          //verificar cordenadas
                    if (p != null) {
                        textPaint.setTextSize(textSize);
                        textPaint.setColor(Color.BLACK);
                        canvas.drawText(p.getUnicodoString(), (x + 1) * cellSize + textOffset, (max - y - 1) * cellSize - textOffset, textPaint);

                    }
                }
            }
        }
        /*if (selection != null && (p = Board.getPiece(selection)) != null) {
            boardPaint.setAlpha(128);
            boardPaint.setColor(Color.CYAN);
            canvas.drawCircle(selection.x * cellSize + cellSize / 2,
                    (max - selection.y - 1) * cellSize + cellSize / 2, cellSize / 2, boardPaint);
            textPaint.setColor(Game.getPlayerColor(p.getPlayerId()));
            canvas.drawText(p.getString(), selection.x * cellSize,
                    (max - selection.y) * cellSize - 10, textPaint);
            for (Coordinate possible : p.getPossiblePositions()) {
                drawCoordinate(possible, canvas, cellSize, boardPaint, max);
            }
        }*/
        // print last moves
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setStrokeWidth(5f);

        //-----------------------
    }

    private void drawCoordinate(CoordV2 c, Canvas canvas, float cellSize, Paint paint, int max) {
        canvas.drawRect((c.getX() + 1) * cellSize, (max - c.getY() - 2) * cellSize, (c.getX() + 2) * cellSize, (max - c.getY() - 1) * cellSize, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.min(getMeasuredWidth(), getMeasuredHeight()),
                Math.min(getMeasuredWidth(), getMeasuredHeight()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cellSize != 0) {
            int x = (int) (event.getX() / cellSize);
            int y = (int) (event.getY() / cellSize);
            CoordV2 pos = new CoordV2(x - 1, max - y - 2);
            if (pos.isValid()) {
                game.setSelected(pos);
                invalidate();
            }
        } else
            Toast.makeText(getContext(), "nao exite", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
