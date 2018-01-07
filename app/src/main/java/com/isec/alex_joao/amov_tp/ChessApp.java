package com.isec.alex_joao.amov_tp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.isec.alex_joao.amov_tp.Chess.Chess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chamuscado on 16/12/2017.
 */

public class ChessApp extends Application {
    private static final String PERFIS = "perfis.dat";
    public static Socket gameSocket;
    public static Chess game;
    public static Perfil perfilSelecionado;
    public static List<Perfil> perfis;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ChessApp.context = context;
    }

    public static void setPerfil(Perfil perfil) {
        perfilSelecionado = perfil;
    }

    public static void savePerfis(Context context) {
        if (ChessApp.context == null)
            ChessApp.context = context;
        try {
            FileOutputStream fos = new FileOutputStream(new File(
                    context.getExternalFilesDir(null) + "/" + PERFIS));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(perfis);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lerPerfis(Context context) {
        if (ChessApp.context == null)
            ChessApp.context = context;
        try {
            FileInputStream fis = new FileInputStream(new File(
                    context.getExternalFilesDir(null) + "/" + PERFIS));
            ObjectInputStream ois = new ObjectInputStream(fis);
            perfis = (ArrayList<Perfil>) ois.readObject();
            fis.close();
            if (perfis == null)
                perfis = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            perfis = new ArrayList<>();
        }

    }

    @Deprecated
    public static Bitmap getImage(Perfil perfil) {
        Bitmap bitmap = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    new File(context.getExternalFilesDir(null) + "/"
                            + perfil.getImagemFundo())));
            bitmap = (Bitmap) in.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static void addPerfil(Perfil perfil, Context context) {
        perfis.add(perfil);
        savePerfis(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lerPerfis(getApplicationContext());
    }
}
