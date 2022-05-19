package com.example.memory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class EndScreenActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Highscores highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        TextView timer = findViewById(R.id.endTime);
        double endTime = getIntent().getExtras().getDouble("time");
        new DecimalFormat("##.0").format(endTime);
        String formattedTime = new DecimalFormat("##.0").format(endTime);
        timer.setText(formattedTime);
        Button restartGameButton = findViewById(R.id.restartGameButton);
        restartGameButton.setOnClickListener(v -> launchActivity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Highscores");
        highscores = new Highscores();
        addDatatoFirebase(formattedTime);
    }

    private void addDatatoFirebase(String points) {
        System.out.println("...got into addData");
        highscores.setPoints(points);
        databaseReference.setValue(highscores);
    }

    private void launchActivity() {

        Intent intent = new Intent(this, MainActivityStart.class);
        startActivity(intent);
    }
}