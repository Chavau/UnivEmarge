package com.chavau.univ_angers.univemarge.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.view.activities.Musculation;

public class Configuration_dialog extends DialogFragment {

    private final static int nombrePersonneMin = 1;
    private final static int nombrePersonneMax = 100;

    /**
     * Constructeur par défaut de la classe
     */
    public Configuration_dialog() {
        super();
    }


    /**
     * Méthode servant à créer la fenêtre affichant les informations nécessaire à la configuration
     * d'une séance de sport ( temps et capacité )
     * @param savedInstanceState Bundle qui peut contenir des informations sauvegardées
     * @return Retourne l'affichage de configuration d'une séance
     * @see DialogFragment#onCreateDialog(Bundle)
     */
    @Override
    @NonNull
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Musculation activity = (Musculation) getActivity();
        final View view = LayoutInflater.from(activity).inflate(R.layout.vue_configuration,null);

        //Définition du numberPicker (valeur max et valeur min) pour la capacité de la séance
        final NumberPicker capacity = view.findViewById(R.id.configCapacite);
        capacity.setMinValue(nombrePersonneMin);
        capacity.setMaxValue(nombrePersonneMax);

        // Initialisation du timePicker pour la durée de la séance
        final TimePicker duration = view.findViewById(R.id.configDuree);
        duration.setIs24HourView(true);

        // initialisation des valeurs pour la capacité et la durée aux valeurs définies dans l'activité
        if (activity != null) {
            capacity.setValue(activity.getMData().getCapacite());
            duration.setCurrentHour(activity.getMData().getHeure());
            duration.setCurrentMinute(activity.getMData().getMinute());
        }

        // construction du dialog
        return new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(R.string.etiquetteConfigValider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.ModificatiMusculationonCapaciteHeure(capacity.getValue(), duration.getCurrentHour(), duration.getCurrentMinute());
                    }
                })
                .setNegativeButton(R.string.etiquetteConfigAnnuler, null)
                .create();

    }
}
