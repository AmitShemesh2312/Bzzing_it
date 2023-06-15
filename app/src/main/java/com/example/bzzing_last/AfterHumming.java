package com.example.bzzing_last;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AfterHumming extends AppCompatActivity implements AfterHummingHandler {

    DB database = new DB();
    String name;

    private FragmentOthersChoose fragmentOthersChoose;
    private FragmentManager fragmentManager;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_humming);

        fragmentManager = getSupportFragmentManager();


        database.setAfterUploadHumming(this);

        database.listenToEndChanges(this);

        name = getIntent().getStringExtra("name");

        fragmentOthersChoose = new FragmentOthersChoose();
        fragmentOthersChoose.setName(name);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragmentOthersChoose, null).commit();
    }


    public void updateDocumentChanges(GameRoom g) {
        AppUtilities.gameRoom = g;
        if (!AppUtilities.gameRoom.getEverybodyDone()) {
            fragmentOthersChoose.writeNames();
            if (name.equals(g.getActivePlayer()))
                checkIfEverybodyDone();
        } else
            fragmentSongReveal();
    }

    public void checkIfEverybodyDone() {
        GameRoom gameRoom = AppUtilities.gameRoom;
        boolean everybodyDone = true;

        for (int i = 0; i < gameRoom.getNotPlayers().size(); i++) {
            if (gameRoom.getNotPlayers().get(i).getSongGuess().equals(""))
                everybodyDone = false;
        }
        if (everybodyDone) {
            gameRoom.setEverybodyDone(true);
            database.updateField("everybodyDone");
            fragmentSongReveal();
        }
    }

    public void fragmentSongReveal() {
        if (!name.equals(AppUtilities.gameRoom.getActivePlayer())) {
            FragmentSongReveal fragmentSongReveal = new FragmentSongReveal();
            fragmentSongReveal.setName(name);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragmentSongReveal).commit();
        } else
        {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FragmentExpectations.class, null).commit();
        }
    }

    public void ratePlayer(View view) {
        GameRoom gameRoom = AppUtilities.gameRoom;
        NotPlayer p = gameRoom.getNotPlayers().get(gameRoom.getNotPlayerIndex(name));
        int rate = p.getRate();
        TextView textView = findViewById(R.id.points);

        int tag = Integer.parseInt(view.getTag().toString());
        if(rate < 10 && rate > 0)
        {
            if (tag == 1)
                rate += 1;
            else
                rate -= 1;
        }
        else if(rate == 0)
        {
            if(tag == 1)
                rate += 1;
        }
        else
        {
            if(tag == 0)
                rate -= 1;
        }
        p.setRate(rate);
        textView.setText("" + rate);
    }

    public void afterRealizing(View view) {
        database.updateField("notPlayers");
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FragmentNextPlayer.class, null).commit();
    }


    //צריך ללחוץ פעמיים על המשך אם לא מזמזם
    //ללחוץ על השמיעה קורס
    public void playRecording(View view)
    {
        if (mediaPlayer == null)
            database.getHumming(AppUtilities.gameRoom.getRounds());
        else {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}