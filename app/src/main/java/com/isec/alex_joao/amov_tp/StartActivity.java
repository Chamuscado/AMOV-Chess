package com.isec.alex_joao.amov_tp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onNewGame(View view) {
        //Toast.makeText(getApplicationContext(),getString(R.string.notDoneYet),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    public void onLoadGame(View view) {
        Toast.makeText(getApplicationContext(),getString(R.string.notDoneYet),Toast.LENGTH_SHORT).show();
    }
}
