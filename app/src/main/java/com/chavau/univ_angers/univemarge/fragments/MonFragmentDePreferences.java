package com.chavau.univ_angers.univemarge.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.R;


public class MonFragmentDePreferences extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    EditTextPreference etp;
    CheckBoxPreference cbp;
    EditText oldPass;
    EditText newPass;
    Button validerBtn;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);

        etp = (EditTextPreference) findPreference("pref_sync3");
        etp.setVisible(false);

        cbp = (CheckBoxPreference) findPreference("pref_sync2");
        cbp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                initChangePasswordsComponents();
                if (((Boolean) o).booleanValue()) {
                    setVisibilityComponents(true, oldPass, newPass, validerBtn);
                } else {
                    setVisibilityComponents(false, oldPass, newPass, validerBtn);
                }
                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        validerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String oldPassPref = etp.getText();
                String oldPassSaisi = oldPass.getText().toString();
                String newPassSaisi = newPass.getText().toString();
                if (oldPassPref.equals(oldPassSaisi)) {
                    if (newPassSaisi.isEmpty()) {
                        Toast.makeText(getActivity(), "Veuillez saisir un nouveau code pin", Toast.LENGTH_LONG).show();
                    } else {
                        if (newPassSaisi.equals(oldPassSaisi)) {
                            Toast.makeText(getActivity(), "Votre nouveau code pin doit étre different du courant !", Toast.LENGTH_LONG).show();
                        } else {
                            if (newPassSaisi.length() != 4) {
                                Toast.makeText(getActivity(), "Votre nouveau code pin doit avoir 4 numéros !", Toast.LENGTH_LONG).show();
                            } else {
                                etp.setText(newPass.getText().toString());
                                Toast.makeText(getActivity(), "Code pin modifié avec succès !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Ancien code pin incorrect , veuillez réssayer !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initChangePasswordsComponents();
        if (cbp.isChecked()) {
            setVisibilityComponents(true, oldPass, newPass, validerBtn);
        } else {
            setVisibilityComponents(false, oldPass, newPass, validerBtn);
        }


    }

    private void setVisibilityComponents(boolean isVisible, TextView... tvComponents) {
        if (isVisible) {
            tvComponents[0].setVisibility(View.VISIBLE);
            tvComponents[1].setVisibility(View.VISIBLE);
            tvComponents[2].setVisibility(View.VISIBLE);
        } else {
            tvComponents[0].setVisibility(View.GONE);
            tvComponents[1].setVisibility(View.GONE);
            tvComponents[2].setVisibility(View.GONE);
        }
    }

    private void initChangePasswordsComponents() {
        oldPass = getActivity().findViewById(R.id.edtoldpin);
        newPass = getActivity().findViewById(R.id.edtnewpin);
        validerBtn = getActivity().findViewById(R.id.btnchange);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PreferenceScreen pref_screen = getPreferenceScreen();
        int count = pref_screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = pref_screen.getPreference(i);
            if (p instanceof EditTextPreference) {
                String input = sharedPreferences.getString(p.getKey(), "");
                System.out.println("My input : " + input);
                try {
                    double value = Double.parseDouble(input);
                    if (value < 0)
                        System.out.println(value + " is negative");
                    else
                        System.out.println(value + " is possitive");
                    p.setSummary(input);
                } catch (NumberFormatException e) {
                    System.out.println("String " + input + "is not a number");
                }
            }
        }
    }
}