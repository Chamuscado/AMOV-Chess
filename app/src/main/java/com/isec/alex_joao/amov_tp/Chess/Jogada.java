package com.isec.alex_joao.amov_tp.Chess;

import com.isec.alex_joao.amov_tp.Chess.Players.Player;

import java.io.Serializable;

/**
 * Created by Chamuscado on 10/01/2018.
 */

public class Jogada implements Serializable {

    private Coord coord1;
    private Coord coord2;

    public Jogada(Coord coord1, Coord coord2) {
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public Coord getCoord1() {
        return coord1;
    }

    public void setCoord1(Coord coord1) {
        this.coord1 = coord1;
    }

    public Coord getCoord2() {
        return coord2;
    }

    public void setCoord2(Coord coord2) {
        this.coord2 = coord2;
    }
}
