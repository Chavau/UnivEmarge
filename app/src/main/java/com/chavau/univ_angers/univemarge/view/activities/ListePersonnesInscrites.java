package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class ListePersonnesInscrites extends AppCompatActivity {

    private RecyclerView _recyclerview;
    private Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_inscrite);

        // Récuperer les données envoyées par la première activité
        _intent = getIntent();

        // Fixer le titre de l'activité en cours
        setTitle(_intent.getStringExtra(AdapterCours.getNomAct()));


        _recyclerview = findViewById(R.id.recyclerview_personneinscrite);

        ArrayList<Etudiant> et = _intent.getParcelableArrayListExtra(AdapterCours.getListeEtud());

        // Affacter la liste des étudiant(e)s inscrit(e)s
        AdapterPersonneInscrite api = new AdapterPersonneInscrite(this, et);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(api);
    }
}