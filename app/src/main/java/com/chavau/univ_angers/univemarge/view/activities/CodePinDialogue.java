package com.chavau.univ_angers.univemarge.view.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.chavau.univ_angers.univemarge.MainActivity;
import com.chavau.univ_angers.univemarge.R;


public class CodePinDialogue extends DialogFragment {


    /**
     * Constructeur par défaut de la classe
     */
    public CodePinDialogue() {
        super();
    }

    public static void alertDialogCodePin(BadgeageEtudiant activity) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_pin, null);

        new AlertDialog.Builder(activity)
                .setTitle("Code Pin")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final EditText _text_code_pin = view.findViewById(R.id.code_pin);
                        String code_pin = _text_code_pin.getText().toString();
                        String msg = "Erreur";
                        if (code_pin.matches("100")) {
                            msg = "Le mot de passe a été modifié";
                        } else {
                            msg = "L'ancien code de pin est incorrect, veuillez réessayer";
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity activity = (MainActivity) getActivity();
        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_pin, null);

        SharedPreferences preferences = getContext().getSharedPreferences(getResources().getString(R.string.PREFERENCE), 0);
        String codePin = preferences.getString("key_old_pin", "");

        return new AlertDialog.Builder(activity)
                .setTitle("Code Pin")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final EditText _text_code_pin = view.findViewById(R.id.code_pin);
                        String code_pin = _text_code_pin.getText().toString();
                        String msg = "Erreur";
                        if (code_pin.matches("100")) {
                            msg = "Le mot de passe a été modifié";
                        } else {
                            msg = " L'ancien code de pin est incorrect, veuillez réessayer !";
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }

    public Dialog dialog_msg(String msg) {

        final MainActivity activity = (MainActivity) getActivity();
        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_pin, null);
        //String msg = "Erreur";

        //if(code == "100"){ msg ="Correct" ; }
        return new AlertDialog.Builder(activity)
                .setTitle(msg)
                //.setView(view)
                .setPositiveButton("DONE", null)
                .create();
    }


}
