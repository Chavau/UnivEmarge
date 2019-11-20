package com.chavau.univ_angers.univemarge.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.adapters.AdapterEvenements;
import com.chavau.univ_angers.univemarge.adapters.AdapterPersonneInscrite;
import com.chavau.univ_angers.univemarge.intermediaire.Etudiant;

import java.util.ArrayList;

public class BadgeageEtudiant extends AppCompatActivity {

    AdapterPersonneInscrite _api;
    private RecyclerView _recyclerview;
    private Intent _intent;
    private String _titreActivite;
    private ArrayList<Etudiant> _etudiants;

    public void alertDialogCodePin(final View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String codePin = preferences.getString("key_old_pin", "");
       // String codePinExiste = preferences.getString("key_pin", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Code Pin");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.create();
        AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                final EditText _text_code_pin = view.findViewById(R.id.code_pin);
                String code_pin_saisi = _text_code_pin.getText().toString();
                String msg = "Erreur";
                if (code_pin_saisi.matches(codePin)) {
                    Intent intent = new Intent(getApplicationContext(), BadgeageEnseignant.class);
                    intent.putExtra(AdapterEvenements.getNomAct(), _titreActivite);
                    intent.putParcelableArrayListExtra(AdapterEvenements.getListeEtud(), _etudiants);
                    startActivityForResult(intent, 1);
                    dialog.dismiss();
                } else {
                    TextView tv = view.findViewById(R.id.errorCodePin);
                    msg = " Le code pin est incorrect, veuillez réessayer !";
                    tv.setText(msg);
                    tv.setVisibility(View.VISIBLE);

                }
            }
        });
    }

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
        getMenuInflater().inflate(R.menu.menu_code_pin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_code_pin:

                View v = LayoutInflater.from(this).inflate(R.layout.dialog_pin, null);
                alertDialogCodePin(v);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
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