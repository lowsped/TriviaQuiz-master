package com.androidbook.triviaquiz;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//import android.media.MediaPlayer.OnCompletionListener;
public class QuizSplashActivity extends QuizActivity {


     @Override
    public void onCreate(Bundle savedInstanceState) {

         animale[0]="leone";
         animale[1]="struzzo";
         animale[2]="maiale";
         animale[3]="asino";
         animale[4]="acquila";
         animale[5]="cagnolino";
         animale[6]="cavallo";
         animale[7]="delfino";
         animale[8]="mucca";
         animale[9]="rana";
       
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        indovinati=0;
        tentativi=5;
    }
    /*
      Helper method to start the animation on the splash screen
     */

    public void foto1click(View v)
    {
        immagine_selezionata=0;
        String backgroundImageName = (String) v.getTag();
        Log.e(DEBUG_TAG, "--------------  - "+ backgroundImageName  );
        if (backgroundImageName ==animale[numArr[numero_casuale]]){startActivity(new Intent(QuizSplashActivity.this, giusto.class));}
        else {      startActivity(new Intent(QuizSplashActivity.this, sbagliato.class));}
    }
    public void foto2click(View v)
    {
        immagine_selezionata=1;
        String backgroundImageName = (String) v.getTag();
        Log.e(DEBUG_TAG, "--------------  - "+ backgroundImageName  );
        if (backgroundImageName ==animale[numArr[numero_casuale]]){startActivity(new Intent(QuizSplashActivity.this, giusto.class));}
        else {      startActivity(new Intent(QuizSplashActivity.this, sbagliato.class));}
    }
    public void foto3click(View v)
    {
        immagine_selezionata=2;
        String backgroundImageName = (String) v.getTag();
        Log.e(DEBUG_TAG, "--------------  - "+ backgroundImageName  );
        if (backgroundImageName ==animale[numArr[numero_casuale]]){startActivity(new Intent(QuizSplashActivity.this, giusto.class));}
        else {      startActivity(new Intent(QuizSplashActivity.this, sbagliato.class));}
    }
    public void foto4click(View v)
    {
        immagine_selezionata=3;
        String backgroundImageName = (String) v.getTag();
        Log.e(DEBUG_TAG, "--------------  - "+ backgroundImageName  );
        if (backgroundImageName ==animale[numArr[numero_casuale]]){startActivity(new Intent(QuizSplashActivity.this, giusto.class));}
        else {      startActivity(new Intent(QuizSplashActivity.this, sbagliato.class));}

    }

    
    @Override
    protected void onPause() {
        super.onPause();
        resetta=1;
        //indovinati=0;
        //tentativi=5;
    }
    protected void onStop() {
        super.onStop();
        /*
          indovinati=0;
          tentativi=5;
         */
       // Log.e(DEBUG_TAG, "-------------- ON STOP -----------------");
        
    }

    
    @Override
    protected void onResume() {
        super.onResume();

        // Start animating at the beginning so we get the full splash screen experience
        //startAnimating();

        //Log.e(DEBUG_TAG, "-------------- generato numero casuale da 1 a 4 - "+ numero_casuale );

        if (resetta ==1){
            Random r1= new Random();
            numero_casuale=r1.nextInt(4);
            generaarrayunico();
            suonaverso() ;
        }
        TextView text;
        text= (TextView)findViewById(R.id.indovinati);
        text.setText("Hai indovinato "+indovinati+" animali");
        text= (TextView)findViewById(R.id.record);
        SharedPreferences prefs =  getSharedPreferences("recordzoodroid",0);
        int RecordZooDroid = prefs.getInt("recordzoodroid", 0);
        if (indovinati>RecordZooDroid){
        	//aggiorno il record nelle sharedpreferences
        	SharedPreferences.Editor editor=prefs.edit();
        	editor.putInt("recordzoodroid", indovinati);
        	editor.commit();
        	RecordZooDroid=indovinati;
        	
        }

        //Log.e(DEBUG_TAG," shared preferences recordzoodroid " + RecordZooDroid);
        text.setText("il tuo record è "+RecordZooDroid);
        text= (TextView)findViewById(R.id.tentativi);
        text.setText("Ti restano "+tentativi+" tentativi");


        //randomizzaimmagini();
        // startAnimating();
        /*SharedPreferences prefs =  getSharedPreferences("recordzoodroid",0);
        int RecordZooDroid = prefs.getInt("RecordZooDroid", 0);
        Log.e(DEBUG_TAG," shared preferences recordzoodroid " + RecordZooDroid);
        */

    }
    private void generaarrayunico(){
      
        int inizio=0;
        
        int esci=0;
        Random r1= new Random();
        int i1=r1.nextInt(fine);
        numArr[0]=i1;
        for (int i = 1; i < 4 ; i++) {
            esci =0;
            while(esci!=1){
                r1= new Random();
                i1=r1.nextInt(fine);
                for ( int und = 0; und < fine ; und++) {
                    if(i1!=numArr[und])
                    {
                        if(und ==(fine-1))   {
                            numArr[i]=i1;
                            esci=1;
                        }
                    } else
                    {
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < 4 ; i++) {
            //Log.e(DEBUG_TAG," " );
            Log.e(DEBUG_TAG," array " + i+" "+numArr[i]  );
        }
        int imageArr[] = new int[fine];
        imageArr[0] = R.drawable.leone;
        imageArr[1] = R.drawable.struzzo;
        imageArr[2] = R.drawable.maiale;
        imageArr[3] = R.drawable.asino;
        imageArr[4] = R.drawable.acquila;
        imageArr[5] = R.drawable.cagnolino;
        imageArr[6] = R.drawable.cavallo;
        imageArr[7] = R.drawable.delfino;
        imageArr[8] = R.drawable.mucca;
        imageArr[9] = R.drawable.rana;
        
        animale_casuale=animale[numArr[numero_casuale]];
        Log.e(DEBUG_TAG," animale casuale fra quelli visualizzati " + animale_casuale );
                
        ImageView image;
        image = (ImageView)findViewById(R.id.ImageView2_Left );
        image.setImageResource(imageArr[numArr[0]]);
        image.setTag(animale[numArr[0]]);
        image = (ImageView)findViewById(R.id.ImageView2_Right );
        image.setImageResource(imageArr[numArr[1]]);
        image.setTag(animale[numArr[1]]);
        image = (ImageView)findViewById(R.id.ImageView3_Left );
        image.setImageResource(imageArr[numArr[2]]);
        image.setTag(animale[numArr[2]]);
        image = (ImageView)findViewById(R.id.ImageView3_Right );
        image.setImageResource(imageArr[numArr[3]]);
        image.setTag(animale[numArr[3]]);

    }
    public void riascoltaverso(View v )
    {
        Log.e(DEBUG_TAG," verso del  " + animale[numArr[numero_casuale]]);
        suonaverso() ;

    }
    public void suonaverso(){
        //Log.e(DEBUG_TAG," numero casuale generato dalla classe  " + numero_casuale );
        //Log.e(DEBUG_TAG," array presente  " + suonoArr [1] );
        int suonoArr[] =new int[fine];
        //Log.e(DEBUG_TAG," suono ---   " + numArr[numero_casuale ] );

        suonoArr[0] = R.raw.leone;
        suonoArr[1] = R.raw.struzzo;
        suonoArr[2] = R.raw.maiale;
        suonoArr[3] = R.raw.asino ;
        suonoArr[4] = R.raw.acquila;
        suonoArr[5] = R.raw.cagnolino;
        suonoArr[6] = R.raw.cavallo;
        suonoArr[7] = R.raw.delfino;
        suonoArr[8] = R.raw.mucca;
        suonoArr[9] = R.raw.rana;

        
        MediaPlayer mp;
        try {
            mp = MediaPlayer.create(this,suonoArr [numArr[numero_casuale] ] );
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    mp = null;
                }
            });
        } catch (Exception exception) {
        }
        
        //int suonoArr[] =new int[5];
    }
}
        