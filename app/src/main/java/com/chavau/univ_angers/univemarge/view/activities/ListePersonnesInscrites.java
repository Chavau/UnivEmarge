package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterCours;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class ListePersonnesInscrites extends AppCompatActivity {

    private RecyclerView _recyclerview;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;
    private AdapterPersonneInscrite _api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne_inscrite);

        // Récuperer les données envoyées par la première activité
        _intent = getIntent();

        _titreActivite = _intent.getStringExtra(AdapterCours.getNomAct());

        // Fixer le titre de l'activité en cours
        setTitle(_titreActivite);


        _recyclerview = findViewById(R.id.recyclerview_personneinscrite);

        // Récuperation de la liste des étudiant(e)s inscrit(e)s à l'activité sélectionnée
        _etudiants = _intent.getParcelableArrayListExtra(AdapterCours.getListeEtud());

        // Affecter la liste des étudiant(e)s inscrit(e)s
       _api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.PI);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(_api);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seance,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_creer_seance :
                intent = new Intent(this,BadgeageEtudiant.class);
                intent.putExtra(AdapterCours.getNomAct(),_titreActivite);
                intent.putParcelableArrayListExtra(AdapterCours.getListeEtud(),_etudiants);
                startActivity(intent);
                break;

            case R.id.menu_modifier_seance :
                intent = new Intent(this,BadgeageEnseignant.class);
                //_api.notifyDataSetChanged();
                intent.putStringArrayListExtra(AdapterPersonneInscrite.getNomListePresence(),_api.get_listePresence());
                intent.putExtra(AdapterCours.getNomAct(),_titreActivite);
                intent.putParcelableArrayListExtra(AdapterCours.getListeEtud(),_api.get_etudIns());
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}