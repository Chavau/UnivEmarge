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
    EditTextPreference etp_old_pin;
    SwitchPreference sp_pin;
    SwitchPreference sp_notif;
    SwitchPreference sp_Synchro;


    EditText et_new_pin;
    Button btn_change_pin;




    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
        // Faire l'appel de l'EditText et puis le cacher
        etp_old_pin = (EditTextPreference) findPreference("key_old_pin");
        etp_old_pin.setVisible(false);
        //
        sp_notif = (SwitchPreference) findPreference("key_notif");
        sp_notif.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (((Boolean) o).booleanValue()) {
                    Toast.makeText(getActivity(), "Activé !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Desactivé !", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // Activer swith button notification quand la synchronisation automatique est sur off

        sp_Synchro = (SwitchPreference) findPreference("key_sync_auto");
        sp_Synchro.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (!((Boolean) o).booleanValue()) {
                    sp_notif.setChecked(true);
                }
                return true;
            }
        });

        // Faire l'appel du SwitchPref du code Pin et puis faire apparaitre EditText du nouveau pin et le bouton changer
        sp_pin = (SwitchPreference) findPreference("key_pin");
        sp_pin.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                initChangePasswordsComponents();
                if (((Boolean) o).booleanValue()) {
                    setVisibilityComponents(true, et_new_pin, btn_change_pin);
                } else {
                    setVisibilityComponents(false, et_new_pin, btn_change_pin);
                }
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Traitement conçernant le code Pin
        btn_change_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassSaisi = et_new_pin.getText().toString();
                if (newPassSaisi.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir le nouveau code pin !", Toast.LENGTH_LONG).show();
                } else {
                    if (newPassSaisi.length() != 4) {
                        Toast.makeText(getActivity(), "Votre nouveau code pin doit avoir 4 numéros !", Toast.LENGTH_LONG).show();
                    } else {
                        etp_old_pin.setText(et_new_pin.getText().toString());
                        Toast.makeText(getActivity(), "Code pin modifié avec succès !", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //Affichage des EditText par apport à l'état du  CheckBox
        initChangePasswordsComponents();
        if (sp_pin.isChecked()) {
            setVisibilityComponents(true, et_new_pin, btn_change_pin);
        } else {
            setVisibilityComponents(false, et_new_pin, btn_change_pin);
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
        } else {
            tvComponents[0].setVisibility(View.GONE);
            tvComponents[1].setVisibility(View.GONE);
        }
    }

    /**
     * Methode permet d'initialiser les composantes de traitements du code pin
     */
    private void initChangePasswordsComponents() {
        et_new_pin = getActivity().findViewById(R.id.edtnewpin);
        btn_change_pin = getActivity().findViewById(R.id.btnchange);
    }

}
