package com.chavau.univ_angers.univemarge.fragments;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;


public class MonFragmentDePreferences extends PreferenceFragmentCompat {
    EditTextPreference etp;
    SwitchPreference cbp;
    EditText oldPass;
    EditText newPass;
    Button validerBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
        // Faire l'appel de l'EditText et puis le cacher
        etp = (EditTextPreference) findPreference("pref_sync3");
        etp.setVisible(false);
        // Faire l'appel du CheckBox et puis faire le Test
        cbp = (SwitchPreference) findPreference("pref_sync2");
        cbp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                initChangePasswordsComponents();
                if (((Boolean) o).booleanValue()) {
                    setVisibilityComponents(true, newPass, validerBtn);
                } else {
                    setVisibilityComponents(false, newPass, validerBtn);
                }
                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        // Traitement conçernant le code Pin
        validerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassSaisi = newPass.getText().toString();
                if (newPassSaisi.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir le nouveau code pin !", Toast.LENGTH_LONG).show();
                } else {
                    if (newPassSaisi.length() != 4) {
                        Toast.makeText(getActivity(), "Votre nouveau code pin doit avoir 4 numéros !", Toast.LENGTH_LONG).show();
                    } else {
                        etp.setText(newPass.getText().toString());
                        Toast.makeText(getActivity(), "Code pin modifié avec succès !", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //Affichage des EditText par apport au CheckBox
        initChangePasswordsComponents();
        if (cbp.isChecked()) {
            setVisibilityComponents(true, newPass, validerBtn);
        } else {
            setVisibilityComponents(false, newPass, validerBtn);
        }


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
            //tvComponents[2].setVisibility(View.VISIBLE);
        } else {
            tvComponents[0].setVisibility(View.GONE);
            tvComponents[1].setVisibility(View.GONE);
            //tvComponents[2].setVisibility(View.GONE);
        }
    }

    /**
     * Methode permet d'initialiser les composantes de traitements du code pin
     */
    private void initChangePasswordsComponents() {
        //oldPass = getActivity().findViewById(R.id.edtoldpin);
        newPass = getActivity().findViewById(R.id.edtnewpin);
        validerBtn = getActivity().findViewById(R.id.btnchange);
    }


}