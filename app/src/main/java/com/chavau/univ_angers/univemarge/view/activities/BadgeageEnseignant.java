package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class BadgeageEnseignant extends AppCompatActivity {
    private RecyclerView _recyclerview;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_seance);

        _intent = getIntent();

        _titreActivite = _intent.getStringExtra(AdapterCours.getNomAct());

        setTitle(_titreActivite);

        _recyclerview = findViewById(R.id.recyclerview_modification_seance);

        _etudiants = _intent.getParcelableArrayListExtra(AdapterCours.getListeEtud());

        ArrayList<String> listePresence = _intent.getStringArrayListExtra(AdapterPersonneInscrite.getNomListePresence());

        AdapterPersonneInscrite api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.MS);

        api.set_listePresence(listePresence);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(api);
    }
}
