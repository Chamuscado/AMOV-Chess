package com.isec.alex_joao.amov_tp.Chess.Players;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Jogada;
import com.isec.alex_joao.amov_tp.Chess.Pieces.Piece;
import com.isec.alex_joao.amov_tp.Perfil;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Chamuscado on 10/01/2018.
 */

public class BotPlayer extends Player {


    public BotPlayer(int id, Coord dir, Perfil perfil, Chess game) {
        super(id, dir, perfil, game);
    }

    public BotPlayer(int id, Coord dir, Chess game) {
        super(id, dir, game);
    }

    @Override
    public Jogada play() {
        Coord coord1;
        Coord coord2;
        Piece p;
        List<Coord> list;
        do {
            int rand = ThreadLocalRandom.current().nextInt(0, pieces.size());
            p = pieces.get(rand);
            list = p.getDesloc();
        } while (list.size()<1);
        coord1 = p.getSquare().getPos();
        int rand = ThreadLocalRandom.current().nextInt(0, list.size());
        coord2 = list.get(rand);
        return new Jogada(coord1,coord2);

    }
}
