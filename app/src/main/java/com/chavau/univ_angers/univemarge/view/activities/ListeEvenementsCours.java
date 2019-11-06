package com.chavau.univ_angers.univemarge.view.activities;


import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.intermediaire.Cours;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;


import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;

public class ListeEvenementsCours extends AppCompatActivity {

    RecyclerView _recyclerview;
    AdapterCours _adapterCours;
    ArrayList<Cours> _cours = new ArrayList<>();

    DatePickerDialog _datepickerdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_evenements_cours);

        _recyclerview = findViewById(R.id.recyclerview_cours);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));

        _cours = Cours.creeCours();

        _adapterCours = new AdapterCours(this, _cours);

        _recyclerview.setAdapter(_adapterCours);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendrier, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option_calendrier:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                _datepickerdialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int annee, int mois, int jour) {
                        mois++; // le mois selectionné correspond à mois+1

                        String date = (jour) + "/" + ((mois < 10) ? "0" + (mois) : String.valueOf(mois)) + "/" + (annee);
                        ArrayList<Cours> cours = new ArrayList<>();

                        for (Cours c : _cours) {
                            if (c.get_date() != null && c.get_date().equals(date)) {
                                cours.add(c);
                            }
                        }
                        // Afficher la liste des évenements correspondants à la date selectionné
                        _adapterCours.setListeCours(cours);
                    }
                }, year, month, day);

                _datepickerdialog.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        _adapterCours.setListeCours(_cours);
    }
}
