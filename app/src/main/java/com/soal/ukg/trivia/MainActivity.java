package com.soal.ukg.trivia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.soal.ukg.terbaru.R;

import java.util.ArrayList;

/**
 * MainActivity.java
 * Homework 03
 * Sanket Patil
 * Atul Kumar Banwar
 */
public class MainActivity extends Activity implements GetTriviaData.IData {

    private Button btnExit, btnStartTrivia;
    private ProgressBar pgBarLoading;
    private ImageView imgViewTrivia;
    private TextView txtViewLoadingReady;
    private ArrayList<Question> questionList;
    public static final String QUESTIONS_LIST_KEY = "QUESTIONS";
    public static final String JSON_URL = "https://api.myjson.com/bins/fzx2l";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        btnExit = (Button) findViewById(R.id.action_exit);
        btnStartTrivia = (Button) findViewById(R.id.action_start_trivia);
        imgViewTrivia = (ImageView) findViewById(R.id.image_view_trivia);
        pgBarLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
        txtViewLoadingReady = (TextView) findViewById(R.id.text_loading_ready);
        btnStartTrivia.setEnabled(false);

        questionList = new ArrayList<Question>();

        if (isConnectedOnline() && questionList.isEmpty()) {
            pgBarLoading.setVisibility(View.VISIBLE);
            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_loading));
            new GetTriviaData(this).execute(JSON_URL);
        } else if (isConnectedOnline()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Handler for startTrivia
     *
     * @param view
     */
    public void startTrivia(View view) {
        Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
        intent.putExtra(QUESTIONS_LIST_KEY, questionList);
        startActivity(intent);
    }

    /**
     * Handler for exit
     *
     * @param view
     */
    public void exit(View view) {
        finish();
        System.exit(0);
    }

    /**
     * Checking whether the internet connection is available
     *
     * @return
     */
    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setupData(ArrayList<Question> result) {

        pgBarLoading.setVisibility(View.INVISIBLE);
        txtViewLoadingReady.setText("");
        if (!result.isEmpty()) {

            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_ready));
            imgViewTrivia.setImageResource(R.drawable.trivia);
            btnStartTrivia.setEnabled(true);
            questionList.addAll(result);
            //Your Question list is available here
        } else {
            txtViewLoadingReady.setText(getResources().getString(R.string.error_no_questions));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intentku = new Intent(MainActivity.this, about.class);
            startActivity(intentku);
            Toast.makeText(this, "about", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.more) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=pemikir%20versi%20baru"));
            startActivity(intent);
            Toast.makeText(this, "Apliksasi lainya", Toast.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.facebook) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100003203346146"));
                startActivity(intent);
                Toast.makeText(this, "Facebook saya", Toast.LENGTH_LONG).show();
                return true;
        } else   if (id == R.id.kunci) {
                Intent intentku = new Intent(MainActivity.this, kunci.class);
                startActivity(intentku);
                Toast.makeText(this, "Kunci Jawaban", Toast.LENGTH_LONG).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
