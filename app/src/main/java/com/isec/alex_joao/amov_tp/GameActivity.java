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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;
import com.isec.alex_joao.amov_tp.Chess.Players.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class GameActivity extends Activity implements BoardFragment.OnFragmentInteractionListener {
    private static final int PORT = 5000;
    private static final int PORT2 = 5001;
    private static final int MSGSIZE = 512;
    private static final String MSGCONT = "JoinGame";
    private static final String groupMCS = "230.30.30.30";

    BoardFragment boardFragment;
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
            if (mode != Chess.ContinueGame && mode != Chess.OneVsOneNetwork)
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

                final AlertDialog.Builder alertdialog = new AlertDialog.Builder(this, R.style.CustomDialog);
                View view1 = getLayoutInflater().inflate(R.layout.create_or_join, null);

                Button create = (Button) view1.findViewById(R.id.create);
                Button join = (Button) view1.findViewById(R.id.join);

                final Activity act = this;

                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startServer();
                        ChessApp.game = new Chess(Chess.OneVsOneNetworkServer);
                    }
                });
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startClient();
                        ChessApp.game = new Chess(Chess.OneVsOneNetworkClient);
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

    private void startServer() {                        //cria
        waiting = new ProgressDialog(this);
        waiting.setMessage(getString(R.string.WaitingClient));
        waiting.setTitle(getString(R.string.waiting));


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final MulticastSocket multicastSocket;
                try {
                    multicastSocket = new MulticastSocket(PORT2);
                    multicastSocket.joinGroup(InetAddress.getByName(groupMCS));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                waiting.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (multicastSocket != null)
                            multicastSocket.close();
                    }
                });

                String str;
                DatagramPacket datagram;
                do {
                    byte[] msg = new byte[MSGSIZE];
                    datagram = new DatagramPacket(msg, MSGSIZE);
                    try {
                        multicastSocket.receive(datagram);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    str = new String(datagram.getData(), 0, datagram.getLength());
                } while (str.compareTo(MSGCONT) != 0 && !multicastSocket.isClosed());


                multicastSocket.close();
                InetAddress add = datagram.getAddress();
                try {
                    Socket socket = new Socket(add, PORT);
                    ChessApp.setGameSocket(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        waiting.dismiss();
                        if (ChessApp.getGameSocket() == null) {
                            finish();
                            Toast.makeText(act, "Server falhado! ", Toast.LENGTH_LONG).show(); // TODO -> debug
                        } else
                            Toast.makeText(act, "Server iniciado com sucesso status: " + ChessApp.getGameSocket().getRemoteSocketAddress(), Toast.LENGTH_LONG).show(); // TODO -> debug
                        startFragment();
                    }
                });
            }
        });
        t.start();
        waiting.show();
    }

    private void startClient() {
        waiting = new ProgressDialog(this);
        waiting.setMessage(getString(R.string.WaitingClient));
        waiting.setTitle(getString(R.string.waiting));

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] msg = MSGCONT.getBytes();
                DatagramPacket datagram = null;
                DatagramSocket socket = null;
                try {
                    datagram = new DatagramPacket(msg, msg.length, InetAddress.getByName(groupMCS), PORT2);
                    socket = new DatagramSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                do {
                    try {
                        socket.send(datagram);
                        Thread.sleep(1000);
                    } catch (IOException e) {
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (ChessApp.getGameSocket() == null);
            }
        });

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (serverSocket != null) {
                    do {
                        try {
                            t1.start();
                            Socket socket1 = serverSocket.accept();
                            ChessApp.setGameSocket(socket1);
                        } catch (IOException e) {
                            break;
                        }
                    } while (ChessApp.getGameSocket() == null);
                }
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        waiting.dismiss();
                        if (ChessApp.getGameSocket() == null) {
                            finish();
                            Toast.makeText(act, "Cliente falhado! ", Toast.LENGTH_LONG).show(); // TODO -> debug
                        } else
                            Toast.makeText(act, "Cliente iniciado com sucesso status: " + ChessApp.getGameSocket().getRemoteSocketAddress(), Toast.LENGTH_LONG).show(); // TODO -> debug
                        startFragment();
                    }
                });
            }
        });

        t.start();


        waiting.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                t.interrupt();
                t1.interrupt();
            }
        });
        waiting.show();
    }

    private void startFragment() {
        boardFragment = BoardFragment.newInstance(ChessApp.game);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.BoardContainer, boardFragment);
        fragmentTransaction.commit();

        ImageView fotoPlayer1 = (ImageView) findViewById(R.id.fotoPlayer1);
        ImageView fotoPlayer2 = (ImageView) findViewById(R.id.fotoPlayer2);
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);

        Player[] players = ChessApp.game.getplayeres();
        player1.setText(players[0].getPerfil().getStrNome());
        player2.setText(players[1].getPerfil().getStrNome());

        utils.setPic(fotoPlayer1, players[0].getPerfil().getImagemFundo(), getApplicationContext());
        utils.setPic(fotoPlayer2, players[1].getPerfil().getImagemFundo(), getApplicationContext());
    }
}
