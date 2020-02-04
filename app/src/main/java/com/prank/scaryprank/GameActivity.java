package com.prank.scaryprank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_question;
    private Button tv_opt1, tv_opt2, tv_opt3, tv_opt4;
    private static CountDownTimer countDownTimer;

    String[] savollar = {
            "Braziliya terma jamoasi futbol bo'yicha jahon chempionatlarida nechcha marotaba " +
                    "muvaffaqiyat qozongan?",
            "Amerika qo'shma shtatlari ilk prezidenti kim?",
            "Yer yuzida nechta sayyora bor?",
            "Italiya poytaxtini toping...",
            "To'gri javobni belgilang:\n2 + 2 x 6 = ?",
            "Dunyodagi eng mitti qush qaysi?",
            "Hayvonlarni o'rganadigan fan nomi...",
            "O'zbekiston qachon mustaqillikga erishgan?",
            "Valentin kuni nishonlanadigan kunni belgilang...",
            "To'gri javobni belgilang:\n2 + 2 * 2 = ?",
            "Eng yosh aktrisani belgilang...",
            "Koronavirus chiqqan davlat - ?",
            "Dubaydagi eng baland bino nomi..."
    };

    String[] variant1 = {"4 marta", "Barak Obama", "8 ta", "Berlin", "12", "Chumchuq","Zoologiya",
            "1989-yil 30-avgust", "9-fevral", "4", "Zarina Nizomiddinova", "Tailand", "Burj khalifa"};

    String[] variant2 = {"5 marta", "Tomas Jefferson", "9 ta", "Madrid", "14", "Kolibri","Biologiya",
            "1990-yil 31-avgust", "8-mart","6", "Gulchehra Eshonqulova", "Indoneyziya", "Eifel Tower"};

    String[] variant3 = {"6 marta", "Donald Tramp", "10 ta", "Rim", "24","Shpatelteil","Fizika",
            "1991-yil 30-avgust", "14-fevral","8", "Durdona Qurbonova", "Malayziya", "Ozodlik haykali"};

    String[] variant4 = {"7 marta", "Jorj Vashington", "11 ta", "Amsterdam", "30","Bengal","Ximiya",
            "1991-yil 31-avgust","21-mart","Shodiya To'xtayeva", "Xitoy", "Twin towers"};


    String[] javoblar = {"5 marta", "Jorj Vashington", "9 ta", "Rim", "14","Kolibri","Zoologiya",
            "1991-yil 31-avgust","14-fevral","6", "Durdona Qurbonova","Xitoy","Burj khalifa"};


    private String choosen_opt = "";
    private int choosen_answer = 0;
    private int questionCounter = 0;

    private static int imgNumber = 0;
    private static int timer = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        initViews();

        initListeners();

        updateQuestions();

        imgNumber = getIntent().getExtras().getInt("image");
        timer = getIntent().getExtras().getInt("time");

        startTime();
    }

    private void updateQuestions() {
        tv_question.setText(savollar[questionCounter]);
        tv_opt1.setText(variant1[questionCounter]);
        tv_opt2.setText(variant2[questionCounter]);
        tv_opt3.setText(variant3[questionCounter]);
        tv_opt4.setText(variant4[questionCounter]);
    }

    private void initViews() {
        tv_question = findViewById(R.id.questionView);
        tv_opt1 = findViewById(R.id.tv_option1);
        tv_opt2 = findViewById(R.id.tv_option2);
        tv_opt3 = findViewById(R.id.tv_option3);
        tv_opt4 = findViewById(R.id.tv_option4);
    }

    private void initListeners(){
    tv_opt1.setOnClickListener(this);
    tv_opt2.setOnClickListener(this);
    tv_opt3.setOnClickListener(this);
    tv_opt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_option1:
                choosen_opt = tv_opt1.getText().toString();
                if(javoblar[questionCounter].equals(choosen_opt)){
                    showCorrectToast();
                }
                else{
                    showWrongToast();
                }
                questionCounter++;
                if(questionCounter == savollar.length - 1) questionCounter = 0;
                updateQuestions();
                break;

            case R.id.tv_option2:
                choosen_opt = tv_opt2.getText().toString();
                if(javoblar[questionCounter].equals(choosen_opt)){
                    showCorrectToast();
                }
                else{
                    showWrongToast();
                }
                questionCounter++;
                if(questionCounter == savollar.length - 1) questionCounter = 0;
                updateQuestions();
                break;

            case R.id.tv_option3:
                choosen_opt = tv_opt3.getText().toString();
                if(javoblar[questionCounter].equals(choosen_opt)){
                    showCorrectToast();
                }
                else{
                    showWrongToast();
                }
                questionCounter++;
                if(questionCounter == savollar.length - 1) questionCounter = 0;
                updateQuestions();
                break;

            case R.id.tv_option4:
                choosen_opt = tv_opt4.getText().toString();
                if(javoblar[questionCounter].equals(choosen_opt)){
                    showCorrectToast();
                }
                else{
                    showWrongToast();
                }
                questionCounter++;
                if(questionCounter == savollar.length - 1) questionCounter = 0;
                updateQuestions();
                break;
        }

    }

    private void showCorrectToast() {
        Toasty.success(GameActivity.this,"Javob to'gri", Toast.LENGTH_SHORT).show();
    }
    private void showWrongToast(){
        Toasty.error(GameActivity.this,"Javob noto'gri", Toast.LENGTH_SHORT).show();
    }

    private void startTime(){
        final Intent intent = new Intent(GameActivity.this, scaryActivity.class);
        intent.putExtra("image",imgNumber);

        countDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(intent);
                GameActivity.this.finish();
                //Toasty.success(GameActivity.this, String.valueOf(timer), Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }
}
