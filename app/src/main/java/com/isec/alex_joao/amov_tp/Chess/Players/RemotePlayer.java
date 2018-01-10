package com.isec.alex_joao.amov_tp.Chess.Players;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Jogada;
import com.isec.alex_joao.amov_tp.ChessApp;
import com.isec.alex_joao.amov_tp.Perfil;

import java.io.IOException;
import java.net.Socket;

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

    public RemotePlayer(Player player) {
        super(player);
    }

    @Override
    public Jogada play() {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                Socket gameSocket = ChessApp.getGameSocket();
                if (gameSocket != null && gameSocket.isConnected()) {
                    try {
                        Jogada jog = (Jogada) ChessApp.in.readObject();
                        getGame().joga(jog);
                    } catch (IOException e) {
                        ChessApp.endSocket();
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        };
run.run();

        return null;
    }
}
