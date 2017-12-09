package com.isec.alex_joao.amov_tp;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;

public class GameActivity extends Activity implements BoardFragment.OnFragmentInteractionListener {
    BoardFragment boardFragment;
    Chess game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState != null)
            game = (Chess) savedInstanceState.getSerializable("game");
        if (game == null)
            game = new Chess();
        boardFragment = BoardFragment.newInstance(game);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.BoardContainer, boardFragment);
        fragmentTransaction.commit();
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
        outState.putSerializable("game", game);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game = (Chess) savedInstanceState.getSerializable("game");
    }
}
