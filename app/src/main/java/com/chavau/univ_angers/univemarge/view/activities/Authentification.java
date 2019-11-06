package com.chavau.univ_angers.univemarge.view.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chavau.univ_angers.univemarge.MainActivity;
import com.chavau.univ_angers.univemarge.R;
import com.chavau.univ_angers.univemarge.view.fragment.Authentification_Fragment;

public class Authentification extends AppCompatActivity implements Authentification_Fragment.CallBacks{

    private static final String TAG_AUTHENTIFICATION_FRAGMENT = "authentification_fragment";
    private Authentification_Fragment authentification_fragment;

    private EditText ed_login;
    private EditText ed_mdp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        ed_login = findViewById(R.id.ed_connexion_login);
        ed_mdp = findViewById(R.id.ed_connexion_mdp);

        FragmentManager frag_manage = getFragmentManager();

        authentification_fragment = (Authentification_Fragment)frag_manage.findFragmentByTag(TAG_AUTHENTIFICATION_FRAGMENT);

        if(authentification_fragment == null){
            authentification_fragment = new Authentification_Fragment();
            frag_manage.beginTransaction().add(authentification_fragment, TAG_AUTHENTIFICATION_FRAGMENT).commit();
        }

    }

    public void onClickValider(View v){
        //TODO : vérifier connexion internet
        String login = ed_login.getText().toString();
        String mdp = ed_mdp.getText().toString();
        if(login.equals("") || mdp.equals("")) {
            Toast.makeText(this, R.string.connexion_erreur_champ, Toast.LENGTH_LONG).show();
            //TODO : mettre en rouge l'erreur sur l'écran ?
        }
        else{
            authentification_fragment.requete_connexion(login, mdp);
        }
    }

    @Override
    public void onItemDone(boolean result, String login) {
        System.out.println("login : " + login);
        if(result) {
            //TODO : mettre login en préférence (voir token) et lancer MainActivity (ou activité de liste de cours)
            SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.PREFERENCE),0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getResources().getString(R.string.PREF_LOGIN),login.toLowerCase());
            editor.commit();

            Toast.makeText(this, "authentification réussi", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Authentification.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            ed_mdp.setText("");
            Toast.makeText(this, R.string.connexion_erreur_mdp, Toast.LENGTH_LONG).show();
        }

    }
}
