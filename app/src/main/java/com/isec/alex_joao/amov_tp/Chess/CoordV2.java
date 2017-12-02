package com.isec.alex_joao.amov_tp.Chess;

public class CoordV2 {
    protected int X,Y;

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
}
