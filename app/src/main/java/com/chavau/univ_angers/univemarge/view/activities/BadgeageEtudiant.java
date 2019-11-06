package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class BadgeageEtudiant extends AppCompatActivity {

    private RecyclerView _recyclerview;
    AdapterPersonneInscrite _api;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_seance);

        _intent = getIntent();

        _titreActivite = _intent.getStringExtra(AdapterCours.getNomAct());

        setTitle(_titreActivite);

        _recyclerview = findViewById(R.id.recyclerview_creation_seance);

        _etudiants = _intent.getParcelableArrayListExtra(AdapterCours.getListeEtud());

        _api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.NS);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(_api);

        _api.set_listener(new AdapterPersonneInscrite.Listener() {
            @Override
            public void onClick(int position) {
                _api.setPresence(position);
                _api.ajoutPosition(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_pin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_code_pin :

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //_api.notifyDataSetChanged();
        finish();
    }
}