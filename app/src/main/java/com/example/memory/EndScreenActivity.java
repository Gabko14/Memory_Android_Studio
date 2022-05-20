package com.example.memory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EndScreenActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Highscores highscores;
    private boolean bool = true;
    private String userID;

    private List<Double> topScoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        TextView timer = findViewById(R.id.endTime);
        double endTime = getIntent().getExtras().getDouble("time");
        new DecimalFormat("0.0").format(endTime);
        String formattedTime = new DecimalFormat("##.0").format(endTime);
        timer.setText(formattedTime);
        Button restartGameButton = findViewById(R.id.restartGameButton);
        Button highscoreButton = findViewById(R.id.highscoresButton);
        highscoreButton.setOnClickListener(v -> launchMainActivity());
        restartGameButton.setOnClickListener(v -> launchMainActivity());

        FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userID = task.getResult();
                getdata(userID);
            }
        });

    }

    private void getdata(String userId) {
        highscores = new Highscores();
        String allTogetherRefference = "Players/" + userID;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(allTogetherRefference);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (bool == true) {
                    bool = false;
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };

                    Map<String, String> values = snapshot.getValue(genericTypeIndicator);

                    TextView bestTime = findViewById(R.id.bestTime);

                    if (snapshot.exists()) {
                        String points1 = values.get("score1") + "";
                        String points2 = values.get("score2") + "";
                        String points3 = values.get("score3") + "";
                        String points4 = values.get("score4") + "";
                        String points5 = values.get("score5") + "";

                        double endTime = getIntent().getExtras().getDouble("time");
                        String formattedTime = new DecimalFormat("##.0").format(endTime);
                        double bestScoreTillNow1 = Double.parseDouble(points1);
                        double bestScoreTillNow2 = Double.parseDouble(points2);
                        double bestScoreTillNow3 = Double.parseDouble(points3);
                        double bestScoreTillNow4 = Double.parseDouble(points4);
                        double bestScoreTillNow5 = Double.parseDouble(points5);
                        topScoreList.clear();
                        topScoreList.add(bestScoreTillNow1);
                        topScoreList.add(bestScoreTillNow2);
                        topScoreList.add(bestScoreTillNow3);
                        topScoreList.add(bestScoreTillNow4);
                        topScoreList.add(bestScoreTillNow5);
                        myloop:
                        for (int i = 0; i < 5; i++) {
                            double score = topScoreList.get(i);
                            if (score > endTime) {
                                topScoreList.remove(4);
                                topScoreList.add(i, endTime);
                                break myloop;
                            }
                        }
                        String doubleFormat1 = new DecimalFormat("##.0").format(topScoreList.get(0));
                        String doubleFormat2 = new DecimalFormat("##.0").format(topScoreList.get(1));
                        String doubleFormat3 = new DecimalFormat("##.0").format(topScoreList.get(2));
                        String doubleFormat4 = new DecimalFormat("##.0").format(topScoreList.get(3));
                        String doubleFormat5 = new DecimalFormat("##.0").format(topScoreList.get(4));
                        addDatatoFirebase(doubleFormat1, doubleFormat2, doubleFormat3, doubleFormat4, doubleFormat5);
                    } else {
                        databaseReference = firebaseDatabase.getReference(allTogetherRefference);

                        double endTime = getIntent().getExtras().getDouble("time");
                        new DecimalFormat("0.0").format(endTime);
                        String formattedTime = new DecimalFormat("##.0").format(endTime);

                        addDatatoFirebase(formattedTime, "100.0", "100.0", "100.0", "100.0");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(EndScreenActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDatatoFirebase(String score1, String score2, String score3, String score4, String score5) {
        highscores.setScore1(score1);
        highscores.setScore2(score2);
        highscores.setScore3(score3);
        highscores.setScore4(score4);
        highscores.setScore5(score5);
        databaseReference.setValue(highscores);
    }


    private void launchMainActivity() {

        Intent intent = new Intent(this, MainActivityStart.class);

        startActivity(intent);
    }
    private void launchHighscores() {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

}
//private void checkIfHighscore() {
//        if(){
//            addDatatoFirebase(formattedTime);
//            TextView bestTime = findViewById(R.id.bestTime);
//            double endTime = getIntent().getExtras().getDouble("time");
//            String formattedTime = new DecimalFormat("0.0").format(endTime);
//
//            bestTime.setText(highscoreList.get(0));
//        }else{
//            System.out.println("... got into else");
//            TextView bestTime = findViewById(R.id.bestTime);
//            double endTime = getIntent().getExtras().getDouble("time");
//            String formattedTime = new DecimalFormat("0.0").format(endTime);
//
//            int timeInInt = Integer.parseInt(formattedTime);
//            int highscoreInInt = Integer.parseInt(highscoreList.get(0));
//            if (timeInInt < highscoreInInt){
//                bestTime.setText(timeInInt);
//                highscoreList.clear();
//                highscoreList.add(formattedTime);
//            }else{
//                System.out.println("...bot times are the same");
//            }
//        }
//    }