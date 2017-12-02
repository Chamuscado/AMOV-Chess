package com.isec.alex_joao.amov_tp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Chamuscado on 02/12/2017.
 */

public class BoardView extends View {
    int cor0, cor1;
    Paint boardPaint;
    Paint textPaint;

    public BoardView(Context context, int cor0, int cor1) {
        super(context);
        this.cor0 = cor0;
        this.cor1 = cor1;
        boardPaint = new Paint();
        boardPaint = new Paint();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        //-----------------------

        int max = 8;
        float cellWidth = canvas.getWidth() / (float) max;
        Coord c;
        Piece p;
        textPaint.setTextSize(cellWidth);
        float textOffset = 0.15f * cellWidth;
        boardPaint.reset();
        for (int x = 0; x < max; x++) {
            for (int y = 0; y < max; y++) {
                c = new Coordinate(x, y);
                if (c.isValid()) {
                    if ((x + y) % 2 == 0) boardPaint.setColor(Color.GRAY);
                    else boardPaint.setColor(Color.DKGRAY);
                    drawCoordinate(c, canvas, cellWidth, boardPaint, max);
                    if (isInEditMode()) continue;
                    p = Board.getPiece(c);
                    if (p != null) {
                        textPaint.setColor(Game.getPlayerColor(p.getPlayerId()));
                        canvas.drawText(p.getString(), x * cellWidth,
                                (max - y) * cellWidth - textOffset, textPaint);
                    }
                }
            }
        }
        if (selection != null && (p = Board.getPiece(selection)) != null) {
            boardPaint.setAlpha(128);
            boardPaint.setColor(Color.CYAN);
            canvas.drawCircle(selection.x * cellWidth + cellWidth / 2,
                    (max - selection.y - 1) * cellWidth + cellWidth / 2, cellWidth / 2, boardPaint);
            textPaint.setColor(Game.getPlayerColor(p.getPlayerId()));
            canvas.drawText(p.getString(), selection.x * cellWidth,
                    (max - selection.y) * cellWidth - 10, textPaint);
            for (Coordinate possible : p.getPossiblePositions()) {
                drawCoordinate(possible, canvas, cellWidth, boardPaint, max);
            }
        }
        // print last moves
        boardPaint.setStyle(Paint.Style.STROKE);
        boardPaint.setStrokeWidth(5f);
        for (Player player : Game.players) {
            if (player.lastMove != null) {
                boardPaint.setColor(player.color);
                if (BuildConfig.DEBUG)
                    Logger.log("draw lastMove: " + player.lastMove.first.toString() + " to " +
                            player.lastMove.second.toString());
                drawCoordinate(player.lastMove.first, canvas, cellWidth, boardPaint, max);
                drawCoordinate(player.lastMove.second, canvas, cellWidth, boardPaint, max);
            }
        }
        //-----------------------
    }

    private void drawCoordinate(final Coord c, final Canvas canvas, final float cellWidth, final Paint paint, int max) {
        canvas.drawRect(c.x * cellWidth, (max - c.y - 1) * cellWidth, (c.x + 1) * cellWidth,
                (max - c.y) * cellWidth, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.min(getMeasuredWidth(), getMeasuredHeight()),
                Math.min(getMeasuredWidth(), getMeasuredHeight()));
    }
}
