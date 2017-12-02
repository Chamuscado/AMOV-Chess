package com.isec.alex_joao.amov_tp.Chess;

public class CoordV2 {
    protected int X, Y;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public CoordV2(int x, int y) {
        X = x;
        Y = y;
    }

    public CoordV2(Coord cord) {
        Y = cord.getLetAsInd();
        X = cord.getNumbAsInd();
    }

    static final int maxX = 7;
    static final int maxY = 7;
    static final int minX = 0;
    static final int minY = 0;

    public boolean isValid() {
        return X < minX || X > maxX || Y < minY || Y > maxY ? false : true;
    }
}
