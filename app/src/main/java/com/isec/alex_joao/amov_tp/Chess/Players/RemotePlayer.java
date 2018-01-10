package com.isec.alex_joao.amov_tp.Chess.Players;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Coord;
import com.isec.alex_joao.amov_tp.Chess.Jogada;
import com.isec.alex_joao.amov_tp.ChessApp;
import com.isec.alex_joao.amov_tp.Perfil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Chamuscado on 10/01/2018.
 */

public class RemotePlayer extends Player {
     private ObjectInputStream in =null;


    public RemotePlayer(int id, Coord dir, Perfil perfil, Chess game) {
        super(id, dir, perfil, game);
    }

    public RemotePlayer(int id, Coord dir, Chess game) {
        super(id, dir, game);
    }

    public void startThread(){
        Thread run = new Thread() {
            @Override
            public void run() {
                Socket gameSocket = ChessApp.getGameSocket();

                while (gameSocket != null && gameSocket.isConnected()) {
                    if(in == null && gameSocket!= null)
                        try {
                            in = new ObjectInputStream(gameSocket.getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    try {
                        Jogada jog = (Jogada) in.readObject();
                        getGame().joga(jog);
                    } catch (IOException e) {
                        ChessApp.endSocket();
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        run.start();
    }

    public RemotePlayer(Player player) {
        super(player);
    }

    @Override
    public Jogada play() {



        return null;
    }
}
