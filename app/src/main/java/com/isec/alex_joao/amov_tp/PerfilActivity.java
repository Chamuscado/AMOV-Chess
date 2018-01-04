package com.isec.alex_joao.amov_tp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex_ on 26/12/2017.
 */

public class PerfilActivity extends Activity{

    String imageFilePath = "/sdcard/temp.png";
    ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
    }

    // adicionar o botao "save" canto superior direito
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_guardar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // quando se premite o botao "save" guarda perfil na lista de leaderboards -> NAO FUNCIONA
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Perfil adicionado!", Toast.LENGTH_SHORT).show();
        HistoricoActivity.gravar();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void onCapturarImagem(View v) {
        String strNome = ((EditText)findViewById(R.id.edNome)).getText().toString();
        if (strNome.isEmpty()) {
            findViewById(R.id.edNome).requestFocus();
            return;
        }
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        imageFilePath = getExternalFilesDir(null)+"/"+strNome+".png";
        Uri fileUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(new File(imageFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 20 && resultCode==RESULT_OK){
            HistoricoActivity.setPic(imagePreview,imageFilePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

class Perfil implements Serializable{

    String strNome;
    int corFundo;
    String imagemFundo;
    Date dataCriacao;

    public Perfil(String strNome, String imagemFundo) {
        this.strNome = strNome;
        this.corFundo = Color.WHITE;
        this.imagemFundo = imagemFundo;
        dataCriacao = new Date();
    }
}

