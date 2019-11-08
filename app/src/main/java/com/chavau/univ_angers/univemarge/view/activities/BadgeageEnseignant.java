package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class BadgeageEnseignant extends AppCompatActivity {
    private RecyclerView _recyclerview;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;
    AdapterPersonneInscrite _api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_seance);

        _intent = getIntent();

        _titreActivite = _intent.getStringExtra(AdapterEvenements.getNomAct());

        setTitle(_titreActivite);

        _recyclerview = findViewById(R.id.recyclerview_modification_seance);

        _etudiants = _intent.getParcelableArrayListExtra(AdapterEvenements.getListeEtud());

        _api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.MS);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(_api);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra(AdapterEvenements.getListeEtud(),_api.get_etudIns());
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
