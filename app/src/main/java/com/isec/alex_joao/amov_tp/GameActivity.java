package com.isec.alex_joao.amov_tp;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

public class GameActivity extends Activity {
    BoardFragment boardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardFragment = BoardFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.BoardContainer, boardFragment);
        fragmentTransaction.commit();
    }
}
