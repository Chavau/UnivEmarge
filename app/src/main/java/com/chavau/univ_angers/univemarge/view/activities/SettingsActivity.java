package com.chavau.univ_angers.univemarge.view.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chavau.univ_angers.univemarge.R;

public class SettingsActivity extends AppCompatActivity {

    EditText et_new_pin;
    Button btn_change_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Code generant la fleche qui permet de revenir à l'activité principale
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dialog_msg(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(msg)
                .setPositiveButton("DONE", null)
                .create().show();
    }


    /**
     * Methode permet suivant un boolean d'afficher ou non les composants liés au traitement de changement de mot de passe
     *
     * @param isVisible
     * @param tvComponents
     */
    private void setVisibilityComponents(boolean isVisible, TextView... tvComponents) {
        if (isVisible) {
            tvComponents[0].setVisibility(View.VISIBLE);
            tvComponents[1].setVisibility(View.VISIBLE);
        } else {
            tvComponents[0].setVisibility(View.GONE);
            tvComponents[1].setVisibility(View.GONE);
        }
    }

    /**
     * Methode permet d'initialiser les composantes de traitements du code pin
     */
    private void initChangePasswordsComponents() {
        et_new_pin = findViewById(R.id.edtnewpin);
        btn_change_pin = findViewById(R.id.btnchange);
    }

}

