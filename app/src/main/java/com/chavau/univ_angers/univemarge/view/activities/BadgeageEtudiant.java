package com.chavau.univ_angers.univemarge.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterEvenements;
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

        // Récuperation des données envoyées par l'activité précedente
        _intent = getIntent();

        _titreActivite = _intent.getStringExtra(AdapterEvenements.getNomAct());

        setTitle(_titreActivite);

        _recyclerview = findViewById(R.id.recyclerview_creation_seance);

        _etudiants = _intent.getParcelableArrayListExtra(AdapterEvenements.getListeEtud());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_pin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_code_pin :
                intent = new Intent(this,BadgeageEnseignant.class);
                intent.putExtra(AdapterEvenements.getNomAct(),_titreActivite);
                intent.putParcelableArrayListExtra(AdapterEvenements.getListeEtud(),_etudiants);
                startActivityForResult(intent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                _etudiants = data.getParcelableArrayListExtra(AdapterEvenements.getListeEtud());
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        _api = new AdapterPersonneInscrite(this, _etudiants, AdapterPersonneInscrite.VueChoix.NS);

        _recyclerview.setLayoutManager(new LinearLayoutManager(this));
        _recyclerview.setAdapter(_api);

        _api.set_listener(position -> {
            //TODO : utilité de ces lignes ? met en vert lors d'un click lors du badgeage
            //_api.setPresence(position, Etudiant.STATUE_ETUDIANT.PRESENT);
            //_api.notifyDataSetChanged();
        });
    }
}