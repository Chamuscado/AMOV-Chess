package com.isec.alex_joao.amov_tp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

/**
 * Created by alex_ on 23/12/2017.
 */

public class LeaderboardsActivity extends Activity {

    ListView lstView;
    LeaderboardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards);

        lstView = (ListView) findViewById(R.id.lstPerfis);
        adapter = new LeaderboardsAdapter(getApplicationContext(), ChessApp.perfis);
        lstView.setAdapter(adapter);
    }


    // adicionar o botao "+" canto superior direito
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_criar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // quando se premite o botao "+" vai para a activity de criacao de novo perfil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
