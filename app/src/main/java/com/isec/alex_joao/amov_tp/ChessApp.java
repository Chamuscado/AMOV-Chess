package com.isec.alex_joao.amov_tp;

import android.app.Application;
import android.content.Context;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Player;

import java.net.Socket;

/**
 * Created by Chamuscado on 16/12/2017.
 */

public class ChessApp extends Application {
    public static Socket gameSocket;
    public static Chess game;
    private static Context context;
    public static Player localplayer;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ChessApp.context = context;
    }
}
