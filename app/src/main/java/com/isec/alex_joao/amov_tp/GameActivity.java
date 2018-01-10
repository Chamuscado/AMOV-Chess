package com.isec.alex_joao.amov_tp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameActivity extends Activity implements BoardFragment.OnFragmentInteractionListener {
    private static final int PORT = 5000;
    private static final int PORT2 = 5001;
    private static final int MSGSIZE = 512;
    private static final String MSGCONT = "JoinGame";
    private static final String groupMCS = "230.30.30.30";

    BoardFragment boardFragment;rrr
    ProgressDialog waiting;
    Handler handle;
    int mode;
    Activity act = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ChessApp.setContext(this);

        Intent intent = getIntent();
        if (intent != null) {
            mode = intent.getIntExtra("mode", Chess.OneVsOne);
            if (mode != Chess.ContinueGame)
                ChessApp.game = new Chess(mode);
        }
        handle = new Handler();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mode == Chess.OneVsOneNetwork || mode == Chess.OneVsOneNetworkClient || mode == Chess.OneVsOneNetworkServer) {
            ConnectivityManager connectivityM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityM.getActiveNetworkInfo();
            if (info == null || !info.isConnected()) {
                Toast.makeText(this, R.string.errorNetWorkConnect, Toast.LENGTH_LONG).show();
                mode = Chess.OneVsOne;
            } else {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this, R.style.CustomDialog);
                View view1 = getLayoutInflater().inflate(R.layout.create_or_join, null);

                Button create = (Button) view1.findViewById(R.id.create);
                Button join = (Button) view1.findViewById(R.id.join);

                final Activity act = this;

                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChessApp.game = new Chess(Chess.OneVsOneNetworkServer);
                        startServer();
                    }
                });
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChessApp.game = new Chess(Chess.OneVsOneNetworkClient);
                        startClient();
                    }
                });
                alertdialog.setView(view1);
                alertdialog.show();
            }
        } else {
            if (ChessApp.game == null)
                ChessApp.game = new Chess();
            startFragment();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(), getString(R.string.notDoneYet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Toast.makeText(getApplicationContext(), "onPointerCaptureChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("game", ChessApp.game);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //ChessApp.game = (Chess) savedInstanceState.getSerializable("game");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ChessApp.game.isEnd()) {
            Toast.makeText(act, "Fim do jogo!! " + System.lineSeparator()
                    + ChessApp.game.getWinner().getPerfil().getStrNome()
                    + " ganhou", Toast.LENGTH_LONG).show();
            finish();
        }
        return super.onTouchEvent(event);
    }

    private void startServer() {
        MulticastSocket multicastSocket = null;
        try {
            multicastSocket = new MulticastSocket(PORT2);
            multicastSocket.joinGroup(InetAddress.getByName(groupMCS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        waiting = new ProgressDialog(this);
        waiting.setMessage(getString(R.string.WaitingClient));
        waiting.setTitle(getString(R.string.waiting));

        final MulticastSocket finalMulticastSocket = multicastSocket;
        waiting.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (finalMulticastSocket != null)
                    finalMulticastSocket.close();
            }
        });
        waiting.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String str;
                DatagramPacket datagram;
                do {
                    byte[] msg = new byte[MSGSIZE];
                    datagram = new DatagramPacket(msg, MSGSIZE);
                    try {
                        finalMulticastSocket.receive(datagram);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    str = new String(datagram.getData(), 0, datagram.getLength());

                } while (str.compareTo(MSGCONT) != 0 && !finalMulticastSocket.isClosed());
                finalMulticastSocket.close();
                InetAddress add = datagram.getAddress();
                try {
                    ChessApp.gameSocket = new Socket(add, PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        waiting.dismiss();
                        if (ChessApp.gameSocket == null)
                            finish();
                        //Toast.makeText(act, "Server iniciado com sucesso status: " + ChessApp.gameSocket.getRemoteSocketAddress(), Toast.LENGTH_LONG).show(); // TODO -> debug
                        startFragment();
                    }
                });
            }
        });
        t.start();
    }

    private void startClient() {
        byte[] msg = MSGCONT.getBytes();
        DatagramPacket datagram = null;
        DatagramSocket socket = null;
        try {
            datagram = new DatagramPacket(msg, msg.length, InetAddress.getByName(groupMCS), PORT2);
            socket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        waiting = new ProgressDialog(this);
        waiting.setMessage(getString(R.string.WaitingClient));
        waiting.setTitle(getString(R.string.waiting));

        final DatagramSocket finalSocket = socket;
        waiting.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (finalSocket != null)
                    finalSocket.close();
            }
        });
        waiting.show();

        final DatagramPacket finalDatagram = datagram;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (serverSocket != null) {
                    try {
                        serverSocket.setSoTimeout(1000);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    do {
                        try {
                            finalSocket.send(finalDatagram);
                            ChessApp.gameSocket = serverSocket.accept();
                        } catch (IOException e) {

                        }
                    } while (ChessApp.gameSocket == null && !finalSocket.isClosed());
                }
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        waiting.dismiss();
                        if (ChessApp.gameSocket == null)
                            finish();
                        // Toast.makeText(act, "Server iniciado com sucesso status: " + ChessApp.gameSocket.getRemoteSocketAddress(), Toast.LENGTH_LONG).show(); // TODO -> debug
                        startFragment();
                    }
                });
            }
        });
        t.start();
    }

    private void startFragment() {
        boardFragment = BoardFragment.newInstance(ChessApp.game);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.BoardContainer, boardFragment);
        fragmentTransaction.commit();
    }
}
