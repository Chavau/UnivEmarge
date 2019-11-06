package com.chavau.univ_angers.univemarge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.view.activities.Authentification;
import com.chavau.univ_angers.univemarge.view.activities.ListeEvenementsCours;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_liste_evenements_cours);


        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE),0);
        if(preferences.getString(getResources().getString(R.string.PREF_LOGIN),"").equals("")) {
            Intent intent = new Intent(MainActivity.this, Authentification.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, ListeEvenementsCours.class);
            startActivity(intent);
        }
    }



}
