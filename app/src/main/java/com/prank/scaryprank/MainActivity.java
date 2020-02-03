package com.prank.scaryprank;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner mySpinner;
    RelativeLayout mainLayout, mainLayout2;
    private MediaPlayer bgMusic;
    private AudioManager audioManager;

    private final String SHARED_PREFS = "MY_PREFS";

    private long backPressedTime;

    boolean isMusicActive = true;
    boolean isFirstLaunch = true;

    private Button prevBtn, nextBtn, playBtn;
    private ImageView horror_img;
    private TextView startCountView;
    //AdMob ads
    InterstitialAd mInterstitialAd;

    private CountDownTimer countDownTimer;

    int[] horror_images = {
            R.drawable.horror,
            R.drawable.horror2,
            R.drawable.horror3,
            R.drawable.horror_4,
            R.drawable.horror_5,
            R.drawable.horror_6,
            R.drawable.horror7
    };

    int image_counter = 0;

    int countStart = 4;

    int timer = 10000; //10 seconds default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initViews(); //Initialize views



        initListeners();

        //check first time launch
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        isFirstLaunch = preferences.getBoolean("first_launch",true);

        if(isFirstLaunch){
            firstLaunchAlert();
        }

        //showAd(); //show Interstitial Ad
        //showBannerAd();

        getSettings();

        horror_img.setImageResource(horror_images[image_counter]);

        startCountView.setAlpha(0);

        if (isMusicActive) { //If user enabled background music from settings then it will play
            startBgMusic();
        }

        initArrayList(); //Initialize array list for spinner

    }

    private void firstLaunchAlert() {
        mainLayout.setAlpha(0.5f);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.first_launch, null);

        ImageView closeBtn = mView.findViewById(R.id.closeBtn);


        builder.setView(mView);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setAlpha(1);
                dialog.dismiss();
            }
        });
        dialog.show();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("first_launch", false);
        editor.apply();
    }

    private void showBannerAd() {
        //banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void initListeners() {
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
    }

    private void getSettings() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if (preferences.contains("bg_music")) {
            isMusicActive = preferences.getBoolean("bg_music", true);
            Log.d("shared", "topildi");
        } else {
            Log.d("shared", "topilmadi");
        }
    }

    private void startBgMusic() {
        bgMusic.start();
    }

    private void initArrayList() {
        ArrayList<CustomItems> customList = new ArrayList<>();

        customList.add(new CustomItems("10 sekund"));
        customList.add(new CustomItems("20 sekund"));
        customList.add(new CustomItems("30 sekund"));
        customList.add(new CustomItems("45 sekund"));
        customList.add(new CustomItems("1 daqiqa"));
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, customList);

        if (mySpinner != null) {
            mySpinner.setAdapter(customAdapter);
            mySpinner.setOnItemSelectedListener(this);
        }
    }

    private void initViews() {
        mySpinner = findViewById(R.id.spinner1);
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout2 = findViewById(R.id.mainLayout2);
        horror_img = findViewById(R.id.horror_view);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        playBtn = findViewById(R.id.playBtn);
        startCountView = findViewById(R.id.startCount);

        bgMusic = MediaPlayer.create(MainActivity.this, R.raw.horror_bg);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CustomItems items = (CustomItems) parent.getSelectedItem();
        String str = items.getSpinnerText();
        String numberOnly = str.replaceAll("[^0-9]", "");
        timer = Integer.parseInt(numberOnly);
        if (timer == 1) { //choosen 1 minute
            timer = 60000; //60 seconds
        }  else {
            timer *= 1000; //if choosen 10 seconds then 10 * 1000 = 10000
        }
        //Toast.makeText(getApplicationContext(),String.valueOf(timer),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        timer = 10000;
    }

    public void showInfo(View view) {
        mainLayout.setAlpha(0.5f);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.ogohlantirish, null);

        ImageView closeBtn = mView.findViewById(R.id.closeBtn);


        builder.setView(mView);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setAlpha(1);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void showSettings(View view) {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mainLayout.setAlpha(0.7f);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.settings, null);

        Button cancelBtn = mView.findViewById(R.id.cancelBtn);
        Button okBtn = mView.findViewById(R.id.saveBtn);

        final Switch bgSwitch = mView.findViewById(R.id.switchBgMusic);
        final SeekBar volumeSeekbar = mView.findViewById(R.id.seekbarVolume);
        LinearLayout shareApp = mView.findViewById(R.id.shareBtn);
        LinearLayout rateApp = mView.findViewById(R.id.rate);

        //Volume Button
        initControls(volumeSeekbar);

        bgSwitch.setChecked(isMusicActive);

        builder.setView(mView);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setAlpha(1);
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Switch button
                if (bgSwitch.isChecked()) {
                    if (!bgMusic.isPlaying()) {
                        bgMusic.start();
                    }
                    isMusicActive = true;
                } else {
                    isMusicActive = false;
                    if (bgMusic.isPlaying()) {
                        bgMusic.pause();
                    }
                }
                SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("bg_music", isMusicActive);
                editor.apply();

                mainLayout.setAlpha(1);
                dialog.dismiss();
            }
        });

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Share Button
                shareLink();
            }
        });

        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        dialog.show();
    }

    private void shareLink() {
        String URL = "https://play.google.com/store/apps/details?id=" + getPackageName();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Dasturni yuborish");
        shareIntent.putExtra(Intent.EXTRA_TEXT, URL);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    private void initControls(SeekBar seekbar) {
        try {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            assert audioManager != null;
            seekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            seekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevBtn:
                --image_counter;
                if (image_counter == -1) {
                    image_counter = horror_images.length - 1;
                }
                horror_img.setImageResource(horror_images[image_counter]);
                break;

            case R.id.nextBtn:
                image_counter++;
                if (image_counter == horror_images.length) {
                    image_counter = 0;
                }
                horror_img.setImageResource(horror_images[image_counter]);
                break;

            case R.id.playBtn:
                if(bgMusic.isPlaying()){
                    bgMusic.release();
                }

                MediaPlayer doorFx = MediaPlayer.create(MainActivity.this,R.raw.door);
                if(isMusicActive){
                    doorFx.start();
                }

                mainLayout2.setAlpha(0.3f);

                Alerter.create(MainActivity.this)
                        .setTitle("Prank boshlanmoqda")
                        .setText("Telefoningizni yaqinlaringizga bering...")
                        .setBackgroundColorInt(Color.parseColor("#A40000")) // or setBackgroundColorInt(Color.CYAN)
                        .setIcon(R.drawable.phone)//if you don't write this line of code there will be icon of the bell
                        .show();

                CountDownTimer startTimer = new CountDownTimer(3500,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        countStart--;
                        if(countStart == -1){
                            countStart = 0;
                        }
                        startCountView.setText(String.valueOf(countStart));

                        YoYo.with(Techniques.ZoomIn)
                                .duration(1000)
                                .playOn(startCountView);
                    }
                    @Override
                    public void onFinish() {
                        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("image", image_counter);
                        intent.putExtra("time",timer);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                }; startTimer.start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2500 > System.currentTimeMillis()){
            MainActivity.this.finish();
            bgMusic.release();
        }
        else {
//            Toasty.normal(MainActivity.this,"Chiqish uchun yana bir marta bosing!",
//                    Toast.LENGTH_SHORT, ContextCompat.getDrawable
//                            (MainActivity.this,R.drawable.exit)).show();
            Toasty.warning(MainActivity.this,"Chiqish uchun yana bir marta bosing!",
                    Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void showAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2026382780923969/6687203176");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
            }
        });
    }
}



