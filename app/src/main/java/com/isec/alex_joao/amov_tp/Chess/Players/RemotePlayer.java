package com.isec.alex_joao.amov_tp.Chess.Players;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Jogada;
import com.isec.alex_joao.amov_tp.ChessApp;
import com.isec.alex_joao.amov_tp.Perfil;

/**
 * Created by Chamuscado on 10/01/2018.
 */

public class RemotePlayer extends Player {

    public RemotePlayer(int id, Coord dir, Perfil perfil, Chess game) {
        super(id, dir, perfil, game);

    }

    public RemotePlayer(int id, Coord dir, Chess game) {
        super(id, dir, game);
    }


    @Override
    public Jogada play() {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                if (ChessApp.gameSocket != null && ChessApp.gameSocket.isConnected()) {

                }
            }
        };


        return null;
    }
}
