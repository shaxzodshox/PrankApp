package com.prank.scaryprank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mySpinner;
    RelativeLayout mainLayout;
    private MediaPlayer bgMusic;

    private Button prevBtn,nextBtn,playBtn;
    private ImageView horror_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        startBgMusic();

        initViews(); //Initialize views

        initArrayList(); //Initialize array list for spinner


    }

    private void startBgMusic() {

    }

    private void initArrayList() {
        ArrayList<CustomItems> customList = new ArrayList<>();

        customList.add(new CustomItems("10 sekund"));
        customList.add(new CustomItems("20 sekund"));
        customList.add(new CustomItems("30 sekund"));
        customList.add(new CustomItems("45 sekund"));
        customList.add(new CustomItems("1 daqiqa"));
        customList.add(new CustomItems("2 daqiqa"));
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, customList);

        if(mySpinner != null){
            mySpinner.setAdapter(customAdapter);
            mySpinner.setOnItemSelectedListener(this);
        }
    }

    private void initViews() {
        mySpinner = findViewById(R.id.spinner1);
        mainLayout = findViewById(R.id.mainLayout);
        horror_img = findViewById(R.id.horror_view);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        playBtn = findViewById(R.id.playBtn);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CustomItems items = (CustomItems)parent.getSelectedItem();
        Toast.makeText(MainActivity.this,items.getSpinnerText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showInfo(View view) {
        mainLayout.animate().alpha(0.5f);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.ogohlantirish, null);

        ImageView closeBtn = mView.findViewById(R.id.closeBtn);


        builder.setView(mView);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.animate().alpha(1).setDuration(100);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
