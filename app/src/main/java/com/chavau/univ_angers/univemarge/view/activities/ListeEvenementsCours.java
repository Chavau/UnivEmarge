package com.chavau.univ_angers.univemarge.view.activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.MainActivity;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.database.DBTables;
import com.chavau.univ_angers.univemarge.database.DatabaseHelper;
import com.chavau.univ_angers.univemarge.database.Identifiant;
import com.chavau.univ_angers.univemarge.database.dao.EvenementDAO;
import com.chavau.univ_angers.univemarge.database.dao.PersonnelDAO;
import com.chavau.univ_angers.univemarge.view.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.intermediaire.Cours;


import java.util.Calendar;
import java.util.ArrayList;

public class ListeEvenementsCours extends AppCompatActivity {

    RecyclerView _recyclerview;
    AdapterEvenements _adapterEvenements;
    // TODO: Refacto pour des evenements.... et utiliser la bonne classe
    ArrayList<Cours> _cours = new ArrayList<>();

    DatePickerDialog _datepickerdialog;

    // Database
    PersonnelDAO _personnelDAO;
    EvenementDAO _evenementDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_evenements_cours);

        _recyclerview = findViewById(R.id.recyclerview_cours);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));

        _cours = Cours.creeCours();

        _adapterEvenements = new AdapterEvenements(this, _cours);

        _recyclerview.setAdapter(_adapterEvenements);

        // Database
        DatabaseHelper helper = new DatabaseHelper(this);
        _personnelDAO = new PersonnelDAO(helper);
        _evenementDAO = new EvenementDAO(helper);
//        this.getListeEvenements();
    }

    private void getListeEvenements() {
        // TODO: get login from shared preference
//        String login = "h.fior";
        String login = "f.mercier";

        // Get ID
        int id = _personnelDAO.getIdFromLogin(login);
        System.out.println("ID:" + id);

//        // Get list
//        Identifiant identifiant = new Identifiant();
//        identifiant.ajoutId(DBTables.Responsable.COLONNE_ID_PERSONNEL_RESPONSABLE, id);
//        _evenements = _evenementDAO.listeEvenementsPourPersonnel(identifiant);
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
                    ArrayList<Cours> cours = new ArrayList<>();

                    for (Cours c : _cours) {
                        if (c.get_date() != null && c.get_date().equals(date)) {
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
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE), 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getResources().getString(R.string.PREF_LOGIN), "");
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