package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class EndScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        TextView timer = findViewById(R.id.endTime);

        double endTime = getIntent().getExtras().getDouble("time");
        System.out.println("im here " + endTime);
        timer.setText(new DecimalFormat("##.0").format(endTime));

        Button restartGameButton = findViewById(R.id.restartGameButton);

        restartGameButton.setOnClickListener(v -> launchActivity());
    }


    private void launchActivity() {

        Intent intent = new Intent(this, MainActivityStart.class);
        startActivity(intent);
    }
}