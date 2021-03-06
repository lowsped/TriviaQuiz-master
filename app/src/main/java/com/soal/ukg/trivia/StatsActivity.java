package com.soal.ukg.trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.soal.ukg.terbaru.R;

import java.util.ArrayList;

/**
 * StatsActivity.java
 * Homework 03
 * Sanket Patil
 * Atul Kumar Banwar
 */
public class StatsActivity extends Activity {
    private ArrayList<Question> questions;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
        AdRequest adRequest2 = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest2);

        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });

        Button button = (Button) findViewById(R.id.button_quit);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayInterstitial();
            }
        });
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();

            int totalQuestionsCount = 0;
            int correctAnswersCount = 0;
            int percentage = 0;

            TextView txtViewCorrectPercentage = (TextView) findViewById(R.id.text_view_correct_percentage);
            TextView txtViewTryAgain = (TextView) findViewById(R.id.text_view_try_again);
            ProgressBar pBarCorrectPercentage = (ProgressBar) findViewById(R.id.progress_bar_correct_percentage);

            if (getIntent().getExtras() != null) {
                questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUESTIONS_LIST_KEY);

                if (questions != null) {
                    totalQuestionsCount = questions.size();
                    correctAnswersCount = getIntent().getExtras().getInt(TriviaActivity.CORRECT_ANS_COUNT_KEY);

                    percentage = (correctAnswersCount * 100) / totalQuestionsCount;
                }

                txtViewCorrectPercentage.setText(getResources().getString(R.string.text_view_correct_percentage, percentage));
                pBarCorrectPercentage.setProgress(percentage);

                if (percentage == 100) {
                    txtViewTryAgain.setText("Congratulations!!! All your answers are correct.");
                }
            }
        }}

    /**
     * handler for quit button
     * @param view
     */
    public void quitAction(View view) {
        finish();
    }

    /**
     * handler for try again button
     * @param view
     */
    public void tryAgainAction(View view) {
        Intent intent = new Intent(StatsActivity.this, TriviaActivity.class);
        intent.putExtra(MainActivity.QUESTIONS_LIST_KEY, questions);
        startActivity(intent);
        finish();
    }
}
