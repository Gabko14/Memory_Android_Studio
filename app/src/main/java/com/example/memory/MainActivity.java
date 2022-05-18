package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity<createButton> extends AppCompatActivity {
    private int cardCounter = 0;
    private int pointsCounter = 0;
    public double timerCounter;

    private List<Integer> imageList = new ArrayList<>();


    private List<Integer> openedCardsImages = new ArrayList<>();
    private List<Integer> openedCards = new ArrayList<>();

    int buttonsArray[] = {
            R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
            R.id.imageButton9,
            R.id.imageButton10,
            R.id.imageButton11,
            R.id.imageButton12,
            R.id.imageButtonE1,
            R.id.imageButtonE2,
            R.id.imageButtonE3,
            R.id.imageButtonE4,
            R.id.imageButtonE5,
            R.id.imageButtonE6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageList.add(R.drawable.mango);
        imageList.add(R.drawable.mango);
        imageList.add(R.drawable.pineapplerl);
        imageList.add(R.drawable.pineapplerl);
        imageList.add(R.drawable.watermelon);
        imageList.add(R.drawable.watermelon);
        imageList.add(R.drawable.cherry);
        imageList.add(R.drawable.cherry);
        imageList.add(R.drawable.strawberrys);
        imageList.add(R.drawable.strawberrys);
        imageList.add(R.drawable.raspberry);
        imageList.add(R.drawable.raspberry);
        imageList.add(R.drawable.bitten_apple);
        imageList.add(R.drawable.bitten_apple);
        imageList.add(R.drawable.grape);
        imageList.add(R.drawable.grape);
        imageList.add(R.drawable.kiwi);
        imageList.add(R.drawable.kiwi);

        Collections.shuffle(imageList);
        buttonFunction();
        setUpTimer();
    }

    private void launchActivity() {

        Intent intent = new Intent(this, EndScreenActivity.class);
        intent.putExtra("time",timerCounter);
        System.out.println("im here1 " + timerCounter);
        startActivity(intent);
    }

    public void setUpTimer() {
        TextView timer = findViewById(R.id.Timer);
        new CountDownTimer(69000000, 100){
            public void onTick(long millisUntilFinished){
                timerCounter += 0.1;
                timer.setText(new DecimalFormat("##.0").format(timerCounter));
//                timer.setText(String.valueOf(timerCounter));
            }
            public  void onFinish(){

            }
        }.start();
    }

    public void buttonFunction() {

        for (int counter = 0; counter < buttonsArray.length; counter++) {
            ImageButton button = findViewById(buttonsArray[counter]);


            int finalCounter = counter;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorStateList buttonscolor = button.getBackgroundTintList();
                    if (buttonscolor == ColorStateList.valueOf(Color.rgb(166, 29, 29))) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255)));
                        button.setImageResource(imageList.get(finalCounter));
                        checkIfCardsMatch();
                        cardCounter += 1;
                        openedCardsImages.add(imageList.get(finalCounter));
                        openedCards.add(buttonsArray[finalCounter]);
                        countPoints();
                    }
                }
            });
        }
    }

    public void checkIfCardsMatch(){
        if (cardCounter > 1) {
            Integer card1 = openedCardsImages.get(0);
            Integer card2 = openedCardsImages.get(1);
            if (card1 != card2) {
                for (int counter = 0; counter < 2; counter++) {
                    ImageButton button = findViewById(openedCards.get(counter));
                    button.setImageResource(0);
                    button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(166, 29, 29)));
                }
            }
            openedCardsImages.clear();
            openedCards.clear();
            cardCounter = 0;
        }
    }
    public void restartFunction(View view) {
        for (int buttonId : buttonsArray) {
            ImageButton button = findViewById(buttonId);
            openedCardsImages.clear();
            openedCards.clear();
            cardCounter = 0;
            button.setImageResource(0);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(166, 29, 29)));
            Collections.shuffle(imageList);
        }
    }

    public void countPoints(){
        if (cardCounter >= 2) {
            Integer card11 = openedCardsImages.get(0);
            Integer card22 = openedCardsImages.get(1);
            if (card11 == card22) {
                pointsCounter += 1;
                if (pointsCounter >= 9){
                    launchActivity();
                }
            }
        }
    }
}

