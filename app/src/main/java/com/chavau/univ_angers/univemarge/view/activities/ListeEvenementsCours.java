package com.chavau.univ_angers.univemarge.view.activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.MainActivity;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.dao.EvenementDAO;
import com.chavau.univ_angers.univemarge.database.entities.Evenement;


import java.util.Calendar;
import java.util.ArrayList;

public class ListeEvenementsCours extends AppCompatActivity {

    RecyclerView _recyclerview;
    AdapterEvenements _adapterEvenements;
    ArrayList<Evenement> _cours = new ArrayList<>();

    DatePickerDialog _datepickerdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_evenements_cours);

        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE),0);

        _recyclerview = findViewById(R.id.recyclerview_cours);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));

        EvenementDAO dao = new EvenementDAO(new DatabaseHelper(this));

        //Création de l'identifiant avec l'id de l'enseignant qui est enregistré
        Identifiant c_nul_les_identifiant = new Identifiant();
        c_nul_les_identifiant.ajoutId("id", preferences.getInt(getResources().getString(R.string.PREF_IDENTIFIANT),0));

        _cours = dao.listeEvenementsPourPersonnel(c_nul_les_identifiant);

        _adapterEvenements = new AdapterEvenements(this, _cours);

        _recyclerview.setAdapter(_adapterEvenements);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_liste_evenement, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                _datepickerdialog = new DatePickerDialog(this, (datePicker, annee, mois, jour) -> {
                    mois++; // le mois selectionné correspond à mois+1

                    String date = (jour) + "/" + ((mois < 10) ? "0" + (mois) : String.valueOf(mois)) + "/" + (annee);
                    ArrayList<Evenement> cours = new ArrayList<>();

                    for (Evenement c : _cours) {
                        if (c.getDateDebut() != null && c.getDateDebut().equals(date)) {
                            cours.add(c);
                        }
                    }
                    // Afficher la liste des évenements correspondants à la date selectionné
                    _adapterEvenements.setListeCours(cours);
                }, year, month, day);

                _datepickerdialog.show();

                return true;
            case R.id.synchron:
                return true;
            case R.id.setting:
                Intent start_settings_activity = new Intent(this, SettingsActivity.class);
                startActivity(start_settings_activity);
                return true;
            case R.id.deconnect:
                //Remet le login à vide ( la clef aussi quand celle-ci sera opérationnelle)
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE),0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getResources().getString(R.string.PREF_LOGIN),""); // remise à zéro du login
                editor.putInt(getResources().getString(R.string.PREF_IDENTIFIANT), 0); // remise à zéro de l'identifiant
                editor.commit();
                // relance la MainActivity qui redirigera vers l'activité d'authentification
                Intent intent = new Intent(ListeEvenementsCours.this, MainActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        _adapterEvenements.setListeCours(_cours);
    }
}