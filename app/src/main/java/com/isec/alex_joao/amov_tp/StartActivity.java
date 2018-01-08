package com.isec.alex_joao.amov_tp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.isec.alex_joao.amov_tp.Chess.Chess;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onNewGame(View view) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this, R.style.CustomDialog);
        View view1 = getLayoutInflater().inflate(R.layout.typegameselect, null);

        Button OneVsPhone = (Button) view1.findViewById(R.id.OneVsPhone);
        Button OneVsOne = (Button) view1.findViewById(R.id.OneVsOne);
        Button OneVsOneNetwork = (Button) view1.findViewById(R.id.OneVsOneNetwork);

        final Activity act = this;

        OneVsPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, GameActivity.class);
                intent.putExtra("mode", Chess.OneVsPhone);
                startActivity(intent);
            }
        });

        OneVsOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, GameActivity.class);
                intent.putExtra("mode", Chess.OneVsOne);
                startActivity(intent);
            }
        });

        OneVsOneNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, GameActivity.class);
                intent.putExtra("mode", Chess.OneVsOneNetwork);
                startActivity(intent);
            }
        });

        alertdialog.setView(view1);
        alertdialog.show();
    }

    public void onLoadGame(View view) {
        Toast.makeText(getApplicationContext(), getString(R.string.notDoneYet), Toast.LENGTH_SHORT).show();
    }

    public void onSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onLeaderboards(View view){
        Intent intent = new Intent(this, LeaderboardsActivity.class);
        startActivity(intent);
    }

    public void onAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


}
