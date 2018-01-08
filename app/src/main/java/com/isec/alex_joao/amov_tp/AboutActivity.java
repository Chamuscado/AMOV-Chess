package com.isec.alex_joao.amov_tp;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by alex_ on 08/01/2018.
 */

public class AboutActivity extends Activity {

    private TextView textView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.creditos);
        textView = (TextView) findViewById(R.id.creditos);
        textView.setText(getString(R.string.DevelopedBy) + "\n\n" + getString(R.string.developer1) + "\n" + getString(R.string.developer2));

        textView.startAnimation(animation);
    }
}
