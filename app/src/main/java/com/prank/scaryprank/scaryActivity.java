package com.prank.scaryprank;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class scaryActivity extends AppCompatActivity {

    private ImageView scaryImg;
    private MediaPlayer scaryFx;
    private CountDownTimer countDownTimer; //to show scary image and close this activity

    int[] horror_images = {
            R.drawable.horror,
            R.drawable.horror2,
            R.drawable.horror3,
            R.drawable.horror_4,
            R.drawable.horror_5,
            R.drawable.horror_6,
            R.drawable.horror7
    };

    int imgNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scary);

        scaryImg = findViewById(R.id.scaryImage);
        scaryFx = MediaPlayer.create(scaryActivity.this,R.raw.scary_fx);

        imgNumber = getIntent().getExtras().getInt("image");

        scaryImg.setImageResource(horror_images[imgNumber]);
        scaryFx.start();

        countDownTimer = new CountDownTimer(4000,1000) { //shows 4 second
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                scaryActivity.this.finish();
            }
        };
        countDownTimer.start();

        scaryFx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                scaryFx.release();
            }
        });


    }
}
