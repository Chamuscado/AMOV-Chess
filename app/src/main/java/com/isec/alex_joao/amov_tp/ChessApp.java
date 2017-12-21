package com.isec.alex_joao.amov_tp;

import android.app.Application;

import com.isec.alex_joao.amov_tp.Chess.Chess;

import java.net.Socket;

/**
 * Created by Chamuscado on 16/12/2017.
 */

public class ChessApp extends Application {
    public static Socket gameSocket;
    public static Chess game;

}
