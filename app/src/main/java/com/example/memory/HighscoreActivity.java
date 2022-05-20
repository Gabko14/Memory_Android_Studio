package com.example.memory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Map;

public class HighscoreActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        TextView score1View = findViewById(R.id.score1);
        TextView score2View = findViewById(R.id.score2);
        TextView score3View = findViewById(R.id.score3);
        TextView score4View = findViewById(R.id.score4);
        TextView score5View = findViewById(R.id.score5);

        Button mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(v -> launchMainActivity());

        String score1 = getIntent().getExtras().getString("score1");
        String score2 = getIntent().getExtras().getString("score2");
        String score3 = getIntent().getExtras().getString("score3");
        String score4 = getIntent().getExtras().getString("score4");
        String score5 = getIntent().getExtras().getString("score5");

        score1View.setText("1. "+score1);
        score2View.setText("2. "+score2);
        score3View.setText("3. "+score3);
        score4View.setText("4. "+score4);
        score5View.setText("5. "+score5);
    }
    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivityStart.class);
        startActivity(intent);
    }
}