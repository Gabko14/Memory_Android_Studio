package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private int cardCounter = 0;
    private int pointsCounter = 0;

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
            R.id.imageButtonE1,
            R.id.imageButtonE2,
            R.id.imageButtonE3,
            R.id.imageButtonE4,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button nextPageButton = findViewById(R.id.nextPageButton);

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });

        imageList.add(R.drawable.mango);
        imageList.add(R.drawable.mango);
        imageList.add(R.drawable.pineapplerl);
        imageList.add(R.drawable.pineapplerl);
        imageList.add(R.drawable.cherry);
        imageList.add(R.drawable.cherry);
        imageList.add(R.drawable.strawberrys);
        imageList.add(R.drawable.strawberrys);
        imageList.add(R.drawable.raspberry);
        imageList.add(R.drawable.raspberry);
        imageList.add(R.drawable.bitten_apple);
        imageList.add(R.drawable.bitten_apple);

        Collections.shuffle(imageList);
        buttonFunction();
    }

    private void launchActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            TextView pointsCounterView = (TextView) findViewById(R.id.pointsCounter);
            Integer card11 = openedCardsImages.get(0);
            Integer card22 = openedCardsImages.get(1);
            if (card11 == card22) {
                pointsCounter += 1;
                pointsCounterView.setText(Integer.toString(pointsCounter));
            }
        }
    }
}
